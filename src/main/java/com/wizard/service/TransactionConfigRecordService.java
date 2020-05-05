package com.wizard.service;

import com.wizard.model.CommonListResult;
import com.wizard.model.TransactionConfigModel;
import com.wizard.model.from.TransactionConfigQuery;
import com.wizard.persistence.trade.TransactionConfigMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TransactionConfigRecordService {

    @Resource
    private TransactionConfigMapper transactionConfigMapper;

    public CommonListResult<TransactionConfigModel> getTransactionConfigRecordList(TransactionConfigQuery query){

        CommonListResult<TransactionConfigModel> result = CommonListResult.getSuccListResult();

        query.generateStartIndex();

        List<TransactionConfigModel> list = transactionConfigMapper.getTransactionConfigRecord(query);
        int count = transactionConfigMapper.getTransactionConfigRecordCount(query);

        result.setResultList(list);
        result.setPage(query.getPage());
        result.setPagesize(query.getPageSize());
        result.setTotalCount(count);
        return result;
    }
}
