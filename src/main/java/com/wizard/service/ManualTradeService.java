package com.wizard.service;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.huobi.client.model.Candlestick;
import com.huobi.client.model.enums.OrderType;
import com.wizard.model.BuySellHistoryRecordModel;
import com.wizard.model.CommonResult;
import com.wizard.model.ManualTradeBean;
import com.wizard.model.from.ManualTradeAdd;
import com.wizard.model.from.ManualTradeUpdate;
import com.wizard.persistence.trade.BuyRecordMapper;
import com.wizard.persistence.trade.ManualTradeMapper;
import com.wizard.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ManualTradeService {

    @Resource
    private HuoBiService huoBiService;

    @Resource
    private ManualTradeMapper manualTradeMapper;

    @Resource
    private BuyRecordMapper buyRecordMapper;

    private final String MANUAL_KEY = "KEY";

    public LoadingCache<String, List<ManualTradeBean>> manualTradeBeanCache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES).build(new CacheLoader<String, List<ManualTradeBean>>() {
                @Override
                public List<ManualTradeBean> load(String key) {
                    log.info("get from manualTradeBeanCache");
                    return manualTradeMapper.getAll();
                }
            });

    /**
     * 人工买
     *
     * @param manualTradeAdd
     * @return
     */
    public CommonResult buy(ManualTradeAdd manualTradeAdd) {

        if (StringUtils.isEmpty(manualTradeAdd.getSymbol())
                || manualTradeAdd.getBuyPrice() == null
                || manualTradeAdd.getExpense() == null
                || manualTradeAdd.getAmountPrecision() == null
                || manualTradeAdd.getPricePrecision() == null) {
            return CommonResult.getFailResult("参数错误");
        }

        Float amountF = manualTradeAdd.getExpense() / manualTradeAdd.getBuyPrice();
        BigDecimal amount = new BigDecimal(amountF).setScale(manualTradeAdd.getAmountPrecision(), BigDecimal.ROUND_DOWN);
        BigDecimal price = new BigDecimal(manualTradeAdd.getBuyPrice()).setScale(manualTradeAdd.getPricePrecision(), BigDecimal.ROUND_DOWN);

        Long orderId = huoBiService.createOrder(manualTradeAdd.getSymbol(), amount, price, OrderType.BUY_LIMIT);
        manualTradeAdd.setBuyOrderId(orderId + "");
        manualTradeAdd.setAmount(amount.floatValue());

        if (orderId != null) {
            manualTradeMapper.insertRecord(manualTradeAdd);
        } else {
            return CommonResult.getFailResult("操作失败");
        }

        return CommonResult.getSuccResult();
    }

    /**
     * 定时检查人工操作记录的状态以及是否需要进行交易
     */
    public void checkManualTrade() {

        List<ManualTradeBean> manualTradeBeanList = manualTradeBeanCache.getUnchecked(MANUAL_KEY);

        //按交易对分类
        Map<String, List<ManualTradeBean>> manualTradeBeanMap = manualTradeBeanList.stream().collect(Collectors.groupingBy(ManualTradeBean::getSymbol));

        for (String symbol : manualTradeBeanMap.keySet()) {

            List<ManualTradeBean> manualTradeBeans = manualTradeBeanMap.get(symbol);
            Candlestick candlestick = huoBiService.getCurrentCandlestick(symbol);

            if (candlestick != null) {

                log.info("symbol={} candlestick={}",symbol, JSON.toJSONString(candlestick));
                float price = candlestick.getClose().floatValue();

                for (ManualTradeBean manualTradeBean : manualTradeBeans) {

                    if (manualTradeBean.getStatus() == Constant.ManualTradeStatusEnum.BUY.getCode()
                            || manualTradeBean.getStatus() == Constant.ManualTradeStatusEnum.SELL.getCode()) {
                        //查看挂单是否成功
                        checkOrder(manualTradeBean);
                    } else if (manualTradeBean.getStatus() == Constant.ManualTradeStatusEnum.NORMAL.getCode()) {

                        //这两个值不为空 说明达到可以交易的条件了
                        if(manualTradeBean.getLowOperPrice() != null && manualTradeBean.getHighOperPrice() != null){

                            //达到交易价格
                            if(price >= manualTradeBean.getLowOperPrice() && price <= manualTradeBean.getHighOperPrice()){
                                log.info("达到交易价格 manualTradeBean={}",manualTradeBean);
                                Long sellOrderId = huoBiService.createOrder(symbol,new BigDecimal(manualTradeBean.getAmount()+""),candlestick.getClose(),OrderType.SELL_LIMIT);
                                if(sellOrderId != null){
                                    ManualTradeUpdate manualTradeUpdate = new ManualTradeUpdate();
                                    manualTradeUpdate.setId(manualTradeBean.getId());
                                    manualTradeUpdate.setStatus(Constant.ManualTradeStatusEnum.SELL.getCode());
                                    manualTradeUpdate.setSellPrice(price);
                                    manualTradeUpdate.setSellOrderId(sellOrderId+"");

                                    manualTradeMapper.modManualTrade(manualTradeUpdate);

                                    manualTradeBeanCache.cleanUp();
                                }
                            }else if(price > manualTradeBean.getHighOperPrice()){

                                float minIncome = manualTradeBean.getBuyPrice() * (1+manualTradeBean.getMinIncome());

                                //更新移动止盈值
                                if(price > minIncome){
                                    float low = 0.02f;//下降2%
                                    float high = 0.015f;//下降1.5%

                                    if(manualTradeBean.getHighOperPrice() > minIncome){//增加移动止盈的值
                                        low = 0.03f;//下降3%
                                        high = 0.025f;//下降2.5%
                                    }

                                    updateLowHighPrice(price, manualTradeBean, low, high);
                                }
                            }

                        }else{

                            float minIncome = manualTradeBean.getBuyPrice() * (1+manualTradeBean.getMinIncome());
                            if(price > minIncome){//达到指定的止盈点后 开始计算跳跃值 进行交易
                                float low = 0.02f;//下降2%
                                float high = 0.015f;//下降1.5%

                                updateLowHighPrice(price, manualTradeBean, low, high);
                            }
                        }
                    }
                }

            }
        }

    }

    private void updateLowHighPrice(float price, ManualTradeBean manualTradeBean, float low, float high) {
        float lowOperPrice = new BigDecimal(price * (1 - low)).setScale(manualTradeBean.getPricePrecision(), BigDecimal.ROUND_DOWN).floatValue();
        float highOperPrice = new BigDecimal(price * (1 - high)).setScale(manualTradeBean.getPricePrecision(), BigDecimal.ROUND_DOWN).floatValue();

        ManualTradeUpdate manualTradeUpdate = new ManualTradeUpdate();
        manualTradeUpdate.setId(manualTradeBean.getId());
        manualTradeUpdate.setLowOperPrice(lowOperPrice);
        manualTradeUpdate.setHighOperPrice(highOperPrice);
        manualTradeMapper.modManualTrade(manualTradeUpdate);

        manualTradeBeanCache.cleanUp();
    }

    /**
     * 查看挂单是否成功
     *
     * @param manualTradeBean
     */
    private void checkOrder(ManualTradeBean manualTradeBean) {

        if (manualTradeBean.getStatus() == Constant.ManualTradeStatusEnum.BUY.getCode()) {

            boolean isFilled = huoBiService.checkOrder(Long.parseLong(manualTradeBean.getBuyOrderId()), manualTradeBean.getSymbol());
            if (isFilled) {
                ManualTradeUpdate manualTradeUpdate = new ManualTradeUpdate();
                manualTradeUpdate.setId(manualTradeBean.getId());
                manualTradeUpdate.setStatus(Constant.ManualTradeStatusEnum.NORMAL.getCode());

                manualTradeMapper.modManualTrade(manualTradeUpdate);

                manualTradeBeanCache.cleanUp();
            }

        } else if (manualTradeBean.getStatus() == Constant.ManualTradeStatusEnum.SELL.getCode()) {

            boolean isFilled = huoBiService.checkOrder(Long.parseLong(manualTradeBean.getSellOrderId()), manualTradeBean.getSymbol());
            if (isFilled) {
                int delCount = manualTradeMapper.delById(manualTradeBean.getId());
                if (delCount > 0) {

                    BuySellHistoryRecordModel buySellHistoryRecordModel = new BuySellHistoryRecordModel();
                    buySellHistoryRecordModel.setSymbol(manualTradeBean.getSymbol());
                    buySellHistoryRecordModel.setType(Constant.SELL_TYPE);
                    buySellHistoryRecordModel.setOperType(Constant.MANUAL_TYPE);
                    buySellHistoryRecordModel.setBuyPrice(manualTradeBean.getBuyPrice());
                    buySellHistoryRecordModel.setSellPrice(manualTradeBean.getSellPrice());
                    buySellHistoryRecordModel.setBuyOrderId(manualTradeBean.getBuyOrderId());
                    buySellHistoryRecordModel.setSellOrderId(manualTradeBean.getSellOrderId());
                    buySellHistoryRecordModel.setAmount(manualTradeBean.getAmount());
                    buySellHistoryRecordModel.setOperIndex(System.currentTimeMillis()/1000);

                    buyRecordMapper.insertHistory(buySellHistoryRecordModel);

                }
                manualTradeBeanCache.cleanUp();
            }
        }
    }
}
