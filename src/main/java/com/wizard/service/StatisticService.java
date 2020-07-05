package com.wizard.service;

import com.huobi.client.model.Candlestick;
import com.wizard.model.BuySellHistoryRecordModel;
import com.wizard.model.CommonResult;
import com.wizard.model.StatisticProfitModel;
import com.wizard.model.from.BuySellHistoryRecordQuery;
import com.wizard.persistence.trade.BuyRecordMapper;
import com.wizard.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class StatisticService {

    @Resource
    private BuyRecordMapper buyRecordMapper;

    @Resource
    private HuoBiService huoBiService;

    public CommonResult statisticProfit(){

        Date threeMonth = DateUtils.addMonth(new Date(), -3);//最近三个月
        Date oneMonth = DateUtils.addMonth(new Date(), -1);//最近一个月
        Date lastWeek = DateUtils.addDay(new Date(), -7);//最近这一周

        BuySellHistoryRecordQuery query = new BuySellHistoryRecordQuery();
        query.setBeginCreateTime(DateUtils.formatDefaultDate(threeMonth));
        List<BuySellHistoryRecordModel> list = buyRecordMapper.getBuySellHistoryRecord(query);

        Map<String,StatisticProfitModel> statisticProfitModelMap = new HashMap<String,StatisticProfitModel>();

        if(!CollectionUtils.isEmpty(list)){

            StatisticProfitModel allStatisticProfitModel = new StatisticProfitModel();//统计所有
            allStatisticProfitModel.setBeginTime(threeMonth);
            allStatisticProfitModel.setEndTime(new Date());
            allStatisticProfitModel.setSymbol("all");

            StatisticProfitModel oneMonthStatisticProfitModel = new StatisticProfitModel();//统计最近一个月
            oneMonthStatisticProfitModel.setBeginTime(oneMonth);
            oneMonthStatisticProfitModel.setEndTime(new Date());
            oneMonthStatisticProfitModel.setSymbol("oneMonth");

            StatisticProfitModel lastWeekStatisticProfitModel = new StatisticProfitModel();//统计最近这一周
            lastWeekStatisticProfitModel.setBeginTime(lastWeek);
            lastWeekStatisticProfitModel.setEndTime(new Date());
            lastWeekStatisticProfitModel.setSymbol("lastWeek");

            statisticProfitModelMap.put("allStatisticProfitModel",allStatisticProfitModel);
            statisticProfitModelMap.put("oneMonthStatisticProfitModel",oneMonthStatisticProfitModel);
            statisticProfitModelMap.put("lastWeekStatisticProfitModel",lastWeekStatisticProfitModel);


            for (BuySellHistoryRecordModel buySellHistoryRecordModel : list) {

                if(buySellHistoryRecordModel.getSellPrice() != null && buySellHistoryRecordModel.getSellPrice() != 0){//卖出的

                    Float profit = (buySellHistoryRecordModel.getSellPrice() - buySellHistoryRecordModel.getBuyPrice()) * buySellHistoryRecordModel.getAmount();

                    statistic(profit,statisticProfitModelMap,buySellHistoryRecordModel);

                }else {

                    Candlestick candlestick = huoBiService.candlestickCache.getUnchecked(buySellHistoryRecordModel.getSymbol());

                    if(candlestick != null && candlestick.getClose()!=null){

                        Float profit = (candlestick.getClose().floatValue() - buySellHistoryRecordModel.getBuyPrice() ) * buySellHistoryRecordModel.getAmount();

                        statistic(profit,statisticProfitModelMap,buySellHistoryRecordModel);
                    }
                }
            }
        }

        CommonResult commonResult = CommonResult.getSuccResult();
        commonResult.setResult(statisticProfitModelMap);

        return commonResult;
    }

    private StatisticProfitModel getStatisticProfitModel(String key, Map<String,StatisticProfitModel> statisticProfitModelMap){

        StatisticProfitModel statisticProfitModel = statisticProfitModelMap.get(key);
        if(statisticProfitModel == null) statisticProfitModel = new StatisticProfitModel();

        return statisticProfitModel;

    }

    /**
     * 区分时间段统计
     * @param profit
     * @param statisticProfitModelMap
     * @param buySellHistoryRecordModel
     */
    private void statistic(Float profit, Map<String,StatisticProfitModel> statisticProfitModelMap,BuySellHistoryRecordModel buySellHistoryRecordModel){


        StatisticProfitModel allStatisticProfitModel = getStatisticProfitModel("allStatisticProfitModel",statisticProfitModelMap);
        StatisticProfitModel oneMonthStatisticProfitModel = getStatisticProfitModel("oneMonthStatisticProfitModel",statisticProfitModelMap);
        StatisticProfitModel lastWeekStatisticProfitModel = getStatisticProfitModel("lastWeekStatisticProfitModel",statisticProfitModelMap);

        addProfit(profit,statisticProfitModelMap,allStatisticProfitModel,"all"+buySellHistoryRecordModel.getSymbol(),buySellHistoryRecordModel);

        if(buySellHistoryRecordModel.getCreateTime().getTime() >= oneMonthStatisticProfitModel.getBeginTime().getTime()){//最近一个月
            addProfit(profit,statisticProfitModelMap,oneMonthStatisticProfitModel,"oneMonth"+buySellHistoryRecordModel.getSymbol(),buySellHistoryRecordModel);
        }

        if(buySellHistoryRecordModel.getCreateTime().getTime() >= lastWeekStatisticProfitModel.getBeginTime().getTime()){//最近一周
            addProfit(profit,statisticProfitModelMap,lastWeekStatisticProfitModel,"lastWeek"+buySellHistoryRecordModel.getSymbol(),buySellHistoryRecordModel);
        }

    }

    /**
     * 增加收益
     * @param profit
     * @param statisticProfitModelMap
     * @param allStatisticProfitModel
     * @param symbolKey
     * @param buySellHistoryRecordModel
     */
    private void addProfit(Float profit, Map<String,StatisticProfitModel> statisticProfitModelMap,StatisticProfitModel allStatisticProfitModel,String symbolKey,BuySellHistoryRecordModel buySellHistoryRecordModel){

        DecimalFormat df2 = new DecimalFormat("0.0000");//格式化小数

        allStatisticProfitModel.setProfit(Float.parseFloat(df2.format(allStatisticProfitModel.getProfit() + profit)));

        StatisticProfitModel symbolStatisticProfitModel = getStatisticProfitModel(symbolKey,statisticProfitModelMap);

        symbolStatisticProfitModel.setProfit(Float.parseFloat(df2.format(symbolStatisticProfitModel.getProfit() + profit)));
        symbolStatisticProfitModel.setSymbol(buySellHistoryRecordModel.getSymbol());
        symbolStatisticProfitModel.setBeginTime(allStatisticProfitModel.getBeginTime());
        symbolStatisticProfitModel.setEndTime(allStatisticProfitModel.getEndTime());

        statisticProfitModelMap.put(symbolKey,symbolStatisticProfitModel);

    }

}
