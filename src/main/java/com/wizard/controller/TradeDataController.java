package com.wizard.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wizard.model.BuyDataModel;
import com.wizard.model.CommonMapResult;
import com.wizard.model.CommonResult;
import com.wizard.model.RecordDataModel;
import com.wizard.service.ITradeData;

@RequestMapping("/tradeData")
@RestController
public class TradeDataController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private ITradeData sshTradeDataImpl;
	
	@RequestMapping("/buyData")
    public CommonMapResult<String,List<BuyDataModel>> getBuyData() {
        return sshTradeDataImpl.getBuyDataModels();
    }
	
	@RequestMapping("/recordData")
    public CommonResult getRecordData() {
        return sshTradeDataImpl.getRecordDataModels();
    }
	
	@RequestMapping("/getStatistics")
	public CommonResult getStatistics(String symbol) {
		return null;
	}
	
}
