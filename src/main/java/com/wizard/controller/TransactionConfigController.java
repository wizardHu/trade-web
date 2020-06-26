package com.wizard.controller;

import com.wizard.model.CommonListResult;
import com.wizard.model.CommonResult;
import com.wizard.model.SelectData;
import com.wizard.model.TransactionConfigModel;
import com.wizard.model.from.TransactionConfigAdd;
import com.wizard.model.from.TransactionConfigQuery;
import com.wizard.model.from.TransactionConfigUpdate;
import com.wizard.service.TransactionConfigRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/transactionConfig")
@RestController
@Slf4j
public class TransactionConfigController {

    @Resource
    private TransactionConfigRecordService transactionConfigRecordService;

    @RequestMapping("/getSymbolSelectDate")
    public CommonListResult<SelectData> getSymbolSelectDate() {
        return transactionConfigRecordService.getSymbolSelectDate();
    }

    @RequestMapping("/getTransactionConfig")
    public CommonListResult<TransactionConfigModel> getTransactionConfig(TransactionConfigQuery transactionConfigQuery) {
        log.info("getTransactionConfig req={}",transactionConfigQuery);
        return transactionConfigRecordService.getTransactionConfigRecordList(transactionConfigQuery);
    }

    @RequestMapping("/modTransactionConfigModel")
    public CommonResult modTransactionConfigModel(TransactionConfigUpdate transactionConfigUpdate){
        log.info("modTransactionConfigModel req={}",transactionConfigUpdate);
        return transactionConfigRecordService.modTransactionConfigModel(transactionConfigUpdate);
    }

    @RequestMapping("/addTransactionConfig")
    public CommonResult addTransactionConfig(TransactionConfigAdd transactionConfigAdd){
        log.info("addTransactionConfig req={}",transactionConfigAdd);
        return transactionConfigRecordService.addTransactionConfig(transactionConfigAdd);
    }
}
