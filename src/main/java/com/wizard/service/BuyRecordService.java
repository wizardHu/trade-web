package com.wizard.service;

import com.huobi.client.model.Candlestick;
import com.wizard.model.*;
import com.wizard.model.from.BuyRecordQuery;
import com.wizard.model.from.BuySellHistoryRecordQuery;
import com.wizard.persistence.trade.BuyRecordMapper;
import com.wizard.persistence.trade.JumpQueueRecordMapper;
import com.wizard.util.CommonUtil;
import com.wizard.util.Constant;
import com.wizard.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.List;

@Service
@Slf4j
public class BuyRecordService {

    private long lastTime = 0;

    @Resource
    private BuyRecordMapper buyRecordMapper;

    @Resource
    private JumpQueueRecordMapper jumpQueueRecordMapper;

    @Resource
    private HuoBiService huoBiService;

    public CommonListResult<BuyRecordModel> getBuyRecordList(BuyRecordQuery query){

        CommonListResult<BuyRecordModel> result = CommonListResult.getSuccListResult();
        query.generateStartIndex();

        TimeBean updateTime = DateUtils.splitTime(query.getUpdateTime());
        if(updateTime != null){
            query.setBeginUpdateTime(updateTime.getBegin());
            query.setEndUpdateTime(updateTime.getEnd());
        }

        TimeBean createTime = DateUtils.splitTime(query.getCreateTime());
        if(createTime != null){
            query.setBeginCreateTime(createTime.getBegin());
            query.setEndCreateTime(createTime.getEnd());
        }

        List<BuyRecordModel> list = buyRecordMapper.getBuyRecord(query);

        for (BuyRecordModel buyRecordModel : list) {

            Float minTradePrice = buyRecordModel.getPrice() + buyRecordModel.getPrice() * buyRecordModel.getMinIncome();

            DecimalFormat df2 = new DecimalFormat("0.0000");//格式化小数
            buyRecordModel.setMinTradePrice(Float.parseFloat(df2.format(minTradePrice)));

        }

        int count = buyRecordMapper.getBuyRecordCount(query);

        result.setResultList(list);
        result.setPage(query.getPage());
        result.setPagesize(query.getPageSize());
        result.setTotalCount(count);
        return result;
    }


    public CommonListResult<BuySellHistoryRecordModel> getBuySellHistoryRecordList(BuySellHistoryRecordQuery query){

        CommonListResult<BuySellHistoryRecordModel> result = CommonListResult.getSuccListResult();

        query.generateStartIndex();

        TimeBean createTime = DateUtils.splitTime(query.getCreateTime());
        if(createTime != null){
            query.setBeginCreateTime(createTime.getBegin());
            query.setEndCreateTime(createTime.getEnd());
        }

        List<BuySellHistoryRecordModel> list = buyRecordMapper.getBuySellHistoryRecord(query);
        for (BuySellHistoryRecordModel buySellHistoryRecordModel : list) {
            if(buySellHistoryRecordModel.getSellPrice() != null && buySellHistoryRecordModel.getSellPrice() != 0){
                Float profit = buySellHistoryRecordModel.getSellPrice() - buySellHistoryRecordModel.getBuyPrice();

                DecimalFormat df2 = new DecimalFormat("0.0000");//格式化小数
                buySellHistoryRecordModel.setProfit(Float.parseFloat(df2.format(profit * buySellHistoryRecordModel.getAmount())));
                buySellHistoryRecordModel.setProfitPercentage(Float.parseFloat(df2.format(profit*100/buySellHistoryRecordModel.getBuyPrice())));
            }else {

                String buyOrderId = buySellHistoryRecordModel.getBuyOrderId();
                BuySellHistoryRecordQuery buyOrderIdQuery = new BuySellHistoryRecordQuery();
                buyOrderIdQuery.setBuyOrderId(buyOrderId);

                int buyCount = buyRecordMapper.getBuySellHistoryRecordCount(buyOrderIdQuery);

                if(buyCount == 1){//只有一次 买

                    Candlestick candlestick = huoBiService.candlestickCache.getUnchecked(buySellHistoryRecordModel.getSymbol());
                    if(candlestick != null && candlestick.getClose()!=null){

                        Float profit = candlestick.getClose().floatValue() - buySellHistoryRecordModel.getBuyPrice() ;

                        DecimalFormat df2 = new DecimalFormat("0.0000");//格式化小数
                        buySellHistoryRecordModel.setProfit(Float.parseFloat(df2.format(profit * buySellHistoryRecordModel.getAmount())));
                        buySellHistoryRecordModel.setProfitPercentage(Float.parseFloat(df2.format(profit*100/buySellHistoryRecordModel.getBuyPrice())));

                    }

                }
            }
        }

        int count = buyRecordMapper.getBuySellHistoryRecordCount(query);

        result.setResultList(list);
        result.setPage(query.getPage());
        result.setPagesize(query.getPageSize());
        result.setTotalCount(count);
        return result;
    }

    /**
     * 删除
     * @param id
     * @param passWord
     * @return
     */
    @Transactional(value="tradeTransactionManager")
    public CommonResult delBuyData(Integer id, String passWord) {

        String pwd = CommonUtil.getNewPwd();

        if(System.currentTimeMillis() - lastTime < 30*1000){
            return CommonResult.getFailResult("等会再试");
        }

        if(!pwd.equals(passWord)){
            lastTime = System.currentTimeMillis();
            return CommonResult.getFailResult("密码错误");
        }

        BuyRecordQuery query = new BuyRecordQuery();
        query.setId(id);
        query.setStatus(Constant.BuyRecordStatusEnum.BUY_QUEUE.getStatus());

        List<BuyRecordModel> list = buyRecordMapper.getBuyRecord(query);
        if(!CollectionUtils.isEmpty(list)){
            BuyRecordModel buyRecordModel = list.get(0);
            log.info("del buyRecordModel={}",buyRecordModel);

            int delCount = buyRecordMapper.delById(buyRecordModel.getId());
            if(delCount > 0){
                jumpQueueRecordMapper.delByOrderId(buyRecordModel.getOrderId());
            }else {
                return CommonResult.getFailResult("稍后再试");
            }
        }else {
            return CommonResult.getFailResult("该状态不允许删除");
        }

        return CommonResult.getSuccResult();
    }
}
