package com.wizard.service;

import com.wizard.model.BuyRecordModel;
import com.wizard.model.BuySellHistoryRecordModel;
import com.wizard.model.CommonListResult;
import com.wizard.model.TimeBean;
import com.wizard.model.from.BuyRecordQuery;
import com.wizard.model.from.BuySellHistoryRecordQuery;
import com.wizard.persistence.trade.BuyRecordMapper;
import com.wizard.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.List;

@Service
@Slf4j
public class BuyRecordService {

    @Resource
    private BuyRecordMapper buyRecordMapper;

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
            }
        }

        int count = buyRecordMapper.getBuySellHistoryRecordCount(query);

        result.setResultList(list);
        result.setPage(query.getPage());
        result.setPagesize(query.getPageSize());
        result.setTotalCount(count);
        return result;
    }

}
