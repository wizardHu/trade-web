package com.wizard.service;

import com.wizard.model.BuyRecordModel;
import com.wizard.model.BuySellHistoryRecordModel;
import com.wizard.model.CommonListResult;
import com.wizard.model.from.BuyRecordQuery;
import com.wizard.model.from.BuySellHistoryRecordQuery;
import com.wizard.persistence.trade.BuyRecordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BuyRecordService {

    @Resource
    private BuyRecordMapper buyRecordMapper;

    public CommonListResult<BuyRecordModel> getBuyRecordList(BuyRecordQuery query){

        CommonListResult<BuyRecordModel> result = CommonListResult.getSuccListResult();

        query.generateStartIndex();

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

        List<BuySellHistoryRecordModel> list = buyRecordMapper.getBuySellHistoryRecord(query);
        int count = buyRecordMapper.getBuySellHistoryRecordCount(query);

        result.setResultList(list);
        result.setPage(query.getPage());
        result.setPagesize(query.getPageSize());
        result.setTotalCount(count);
        return result;
    }

}
