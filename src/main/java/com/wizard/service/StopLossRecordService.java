package com.wizard.service;

import com.wizard.model.CommonListResult;
import com.wizard.model.StopLossHistoryRecordModel;
import com.wizard.model.StopLossRecordModel;
import com.wizard.model.from.StopLossHistoryRecordQuery;
import com.wizard.model.from.StopLossRecordQuery;
import com.wizard.persistence.trade.StopLossRecordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StopLossRecordService {

    @Resource
    private StopLossRecordMapper stopLossRecordMapper;

    public CommonListResult<StopLossRecordModel> getStopLossRecordList(StopLossRecordQuery query){

        CommonListResult<StopLossRecordModel> result = CommonListResult.getSuccListResult();

        query.generateStartIndex();

        List<StopLossRecordModel> list = stopLossRecordMapper.getStopLossRecord(query);
        int count = stopLossRecordMapper.getStopLossRecordCount(query);

        result.setResultList(list);
        result.setPage(query.getPage());
        result.setPagesize(query.getPageSize());
        result.setTotalCount(count);
        return result;
    }

    public CommonListResult<StopLossHistoryRecordModel> getStopLossHistoryRecordList(StopLossHistoryRecordQuery query){

        CommonListResult<StopLossHistoryRecordModel> result = CommonListResult.getSuccListResult();

        query.generateStartIndex();

        List<StopLossHistoryRecordModel> list = stopLossRecordMapper.getStopLossHistoryRecord(query);
        int count = stopLossRecordMapper.getStopLossHistoryRecordCount(query);

        result.setResultList(list);
        result.setPage(query.getPage());
        result.setPagesize(query.getPageSize());
        result.setTotalCount(count);
        return result;
    }

}
