package com.wizard.service;

import com.wizard.model.CommonListResult;
import com.wizard.model.SellOrderRecordModel;
import com.wizard.model.from.SellOrderRecordQuery;
import com.wizard.persistence.trade.SellOrderRecordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SellOrderRecordService {

    @Resource
    private SellOrderRecordMapper sellOrderRecordMapper;

    public CommonListResult<SellOrderRecordModel> getSellOrderRecordList(SellOrderRecordQuery query){

        CommonListResult<SellOrderRecordModel> result = CommonListResult.getSuccListResult();

        query.generateStartIndex();

        List<SellOrderRecordModel> list = sellOrderRecordMapper.getSellOrderRecord(query);
        int count = sellOrderRecordMapper.getSellOrderRecordCount(query);

        result.setResultList(list);
        result.setPage(query.getPage());
        result.setPagesize(query.getPageSize());
        result.setTotalCount(count);
        return result;
    }

}
