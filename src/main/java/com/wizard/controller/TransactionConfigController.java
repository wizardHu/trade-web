package com.wizard.controller;

import com.wizard.model.CommonListResult;
import com.wizard.model.SelectData;
import com.wizard.service.TransactionConfigRecordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/transactionConfig")
@RestController
public class TransactionConfigController {

    @Resource
    private TransactionConfigRecordService transactionConfigRecordService;

    @RequestMapping("/getSymbolSelectDate")
    public CommonListResult<SelectData> getSymbolSelectDate() {
        return transactionConfigRecordService.getSymbolSelectDate();
    }
}
