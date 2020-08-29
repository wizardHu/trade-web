package com.wizard.service;

import com.huobi.client.model.Candlestick;
import com.huobi.client.model.enums.CandlestickInterval;
import com.wizard.model.SymbolConfigModel;
import com.wizard.model.from.ManualTradeAdd;
import com.wizard.persistence.trade.SymbolConfigMapper;
import com.wizard.util.MACDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class StrategyService {

    @Resource
    private SymbolConfigMapper symbolConfigMapper;

    @Resource
    private HuoBiService huoBiService;

    @Resource
    private ManualTradeService manualTradeService;

    private Map<String,Long> lastIdMap = new HashMap<>();

    public void macdStrategy(){

        List<SymbolConfigModel> symbolConfigModelList = symbolConfigMapper.getAllConfig();

        for (SymbolConfigModel symbolConfigModel : symbolConfigModelList) {

            Long lastId = lastIdMap.get(symbolConfigModel.getSymbol());

            //空 意味着没有初始化
            if(lastId == null){
                lastId = init(symbolConfigModel);
                lastIdMap.put(symbolConfigModel.getSymbol(), lastId);
                continue;
            }

            List<Candlestick> candlestickList = huoBiService.getCandlestick(symbolConfigModel.getSymbol(), CandlestickInterval.MIN30,1);
            if(!CollectionUtils.isEmpty(candlestickList)){

                Candlestick candlestick = candlestickList.get(0);

                //与上次的id不相等  即为新的k线
                if(lastId.longValue() != candlestick.getId().longValue()){
                    MACDUtil.add(symbolConfigModel.getSymbol(), candlestick.getClose().floatValue());
                    lastIdMap.put(symbolConfigModel.getSymbol(), candlestick.getId());
                }

                boolean canBuy = MACDUtil.check(symbolConfigModel.getSymbol(), candlestick.getClose().floatValue());
                if(canBuy){
                    ManualTradeAdd manualTradeAdd = buildManualTradeAdd(symbolConfigModel, candlestick.getClose().floatValue());
                    log.info("can buy manualTradeAdd={}",manualTradeAdd);
                    manualTradeService.buy(manualTradeAdd);
                }

            }

        }

    }

    private ManualTradeAdd buildManualTradeAdd(SymbolConfigModel symbolConfigModel, float floatValue) {

        ManualTradeAdd manualTradeAdd = new ManualTradeAdd();

        manualTradeAdd.setSymbol(symbolConfigModel.getSymbol());
        manualTradeAdd.setBuyPrice(floatValue);
        manualTradeAdd.setExpense(10f);
        manualTradeAdd.setPricePrecision(symbolConfigModel.getPricePrecision());
        manualTradeAdd.setAmountPrecision(symbolConfigModel.getAmountPrecision());
        manualTradeAdd.setMinIncome(0.05f);

        return manualTradeAdd;
    }

    /**
     * 初始化 返回当前K线的id
     * @param symbolConfigModel
     * @return
     */
    private Long init(SymbolConfigModel symbolConfigModel) {

        List<Candlestick> candlestickList = huoBiService.getCandlestick(symbolConfigModel.getSymbol(), CandlestickInterval.MIN30,15);

        if(!CollectionUtils.isEmpty(candlestickList)){
            for (Candlestick candlestick : candlestickList) {
                MACDUtil.add(symbolConfigModel.getSymbol(), candlestick.getClose().floatValue());
            }

            return candlestickList.get(candlestickList.size()-1).getId();
        }

        return null;
    }
}
