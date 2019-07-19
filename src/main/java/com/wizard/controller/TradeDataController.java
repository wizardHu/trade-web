package com.wizard.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wizard.model.BuyDataModel;
import com.wizard.model.CommonListResult;
import com.wizard.model.CommonMapResult;
import com.wizard.model.CommonResult;
import com.wizard.model.RecordAndStatisticsModel;
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
    public CommonListResult<RecordAndStatisticsModel> getRecordData() {
        return sshTradeDataImpl.getRecordDataModels();
    }
	
	@RequestMapping("/getPresentPrice")
	public CommonResult getPresentPrice(String symbol) {
		return sshTradeDataImpl.getPresentPrice(symbol);
	}
	
	@RequestMapping("/getKline")
	public CommonResult getKline(String symbol, String period, int size) {
		return sshTradeDataImpl.getKline(symbol, period, size);
	}
	
	@RequestMapping("/symbolRecordData")
    public CommonListResult<RecordDataModel> getRecordDataBySymbol(String symbol) {
        return sshTradeDataImpl.getRecordDataBySymbol(symbol);
    }
	
}
