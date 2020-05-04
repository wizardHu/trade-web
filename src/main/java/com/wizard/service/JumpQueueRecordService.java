package com.wizard.service;

import com.wizard.model.CommonListResult;
import com.wizard.model.JumpQueueHistoryRecordModel;
import com.wizard.model.JumpQueueRecordModel;
import com.wizard.model.from.JumpQueueHistoryRecordQuery;
import com.wizard.model.from.JumpQueueRecordQuery;
import com.wizard.persistence.trade.JumpQueueRecordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class JumpQueueRecordService {

    @Resource
    private JumpQueueRecordMapper jumpQueueRecordMapper;

    public CommonListResult<JumpQueueRecordModel> getJumpQueueRecordList(JumpQueueRecordQuery query){

        CommonListResult<JumpQueueRecordModel> result = CommonListResult.getSuccListResult();

        query.generateStartIndex();

        List<JumpQueueRecordModel> list = jumpQueueRecordMapper.getJumpQueueRecord(query);
        int count = jumpQueueRecordMapper.getJumpQueueRecordCount(query);

        result.setResultList(list);
        result.setPage(query.getPage());
        result.setPagesize(query.getPageSize());
        result.setTotalCount(count);
        return result;
    }

    public CommonListResult<JumpQueueHistoryRecordModel> getJumpQueueHistoryRecordList(JumpQueueHistoryRecordQuery query){

        CommonListResult<JumpQueueHistoryRecordModel> result = CommonListResult.getSuccListResult();

        query.generateStartIndex();

        List<JumpQueueHistoryRecordModel> list = jumpQueueRecordMapper.getJumpQueueHistoryRecord(query);
        int count = jumpQueueRecordMapper.getJumpQueueHistoryRecordCount(query);

        result.setResultList(list);
        result.setPage(query.getPage());
        result.setPagesize(query.getPageSize());
        result.setTotalCount(count);
        return result;
    }

}
