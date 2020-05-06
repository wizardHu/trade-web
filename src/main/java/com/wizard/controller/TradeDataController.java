package com.wizard.controller;

import com.wizard.model.BuyRecordModel;
import com.wizard.model.CommonListResult;
import com.wizard.model.from.BuyRecordQuery;
import com.wizard.service.BuyRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/tradeData")
@RestController
public class TradeDataController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private BuyRecordService buyRecordService;

	@RequestMapping("/buyData")
    public CommonListResult<BuyRecordModel> getBuyData(BuyRecordQuery query) {
        return buyRecordService.getBuyRecordList(query);
    }
//
//	@RequestMapping("/recordData")
//    public CommonListResult<RecordAndStatisticsModel> getRecordData() {
//        return sshTradeDataImpl.getRecordDataModels();
//    }
//
//	@RequestMapping("/getPresentPrice")
//	public CommonResult getPresentPrice(String symbol) {
//		return sshTradeDataImpl.getPresentPrice(symbol);
//	}
//
//	@RequestMapping("/getKline")
//	public CommonResult getKline(String symbol, String period, int size) {
//		return sshTradeDataImpl.getKline(symbol, period, size);
//	}
//
//	@RequestMapping("/symbolRecordData")
//    public CommonListResult<RecordDataModel> getRecordDataBySymbol(String symbol) {
//        return sshTradeDataImpl.getRecordDataBySymbol(symbol);
//    }
//
//	@RequestMapping("/getPresentBlance")
//    public CommonResult getPresentBlance(String currency) {
//        return sshTradeDataImpl.getPresentBlance(currency);
//    }
//
//	@RequestMapping("/getSellCount")
//    public CommonResult getSellCount() {
//        return sshTradeDataImpl.getSellCount();
//    }

}
