package com.wizard.service;

import com.wizard.model.CommonListResult;
import com.wizard.model.CommonResult;
import com.wizard.model.SelectData;
import com.wizard.model.TransactionConfigModel;
import com.wizard.model.from.TransactionConfigQuery;
import com.wizard.model.from.TransactionConfigUpdate;
import com.wizard.persistence.trade.TransactionConfigMapper;
import com.wizard.util.CommonUtil;
import com.wizard.util.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TransactionConfigRecordService {

    @Resource
    private TransactionConfigMapper transactionConfigMapper;

    private long lastTime = 0;

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

    /**
     * 修改
     * @param transactionConfigUpdate
     * @return
     */
    public CommonResult modTransactionConfigModel(TransactionConfigUpdate transactionConfigUpdate){

        String pwd = CommonUtil.getNewPwd();

        if(System.currentTimeMillis() - lastTime < 30*1000){
            return CommonResult.getFailResult("等会再试");
        }

        if(!pwd.equals(transactionConfigUpdate.getPassWord())){
            lastTime = System.currentTimeMillis();
            return CommonResult.getFailResult("密码错误");
        }

        int modCount = transactionConfigMapper.modTransactionConfigModel(transactionConfigUpdate);

        if(modCount > 0){
            return CommonResult.getSuccResult();
        }

        return CommonResult.getFailResult("稍后再试");
    }

    /**
     * 获取下拉框
     * @return
     */
    public CommonListResult<SelectData> getSymbolSelectDate() {

        CommonListResult<SelectData> result = CommonListResult.getSuccListResult();
        List<SelectData> selectDataList = new ArrayList<SelectData>();

        TransactionConfigQuery query = new TransactionConfigQuery();
        query.setPage(1);
        query.setPageSize(100);

        query.generateStartIndex();

        List<TransactionConfigModel> list = transactionConfigMapper.getTransactionConfigRecord(query);

        for (TransactionConfigModel transactionConfigModel : list) {

            SelectData selectData = new SelectData();
            selectData.setId(transactionConfigModel.getSymbol());
            selectData.setValue(transactionConfigModel.getSymbol());

            selectDataList.add(selectData);
        }

        result.setResultList(selectDataList);
        return result;
    }


}
