package com.wizard.controller;

import com.wizard.model.BuyRecordModel;
import com.wizard.model.BuySellHistoryRecordModel;
import com.wizard.model.CommonListResult;
import com.wizard.model.CommonResult;
import com.wizard.model.from.BuyRecordQuery;
import com.wizard.model.from.BuySellHistoryRecordQuery;
import com.wizard.service.BuyRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/tradeData")
@RestController
@Slf4j
public class TradeDataController {

	@Resource
	private BuyRecordService buyRecordService;

	@RequestMapping("/buyData")
	public CommonListResult<BuyRecordModel> getBuyData(BuyRecordQuery query) {
		log.info("getBuyData req={}",query);
		return buyRecordService.getBuyRecordList(query);
	}

	@RequestMapping("/buySellHistoryData")
	public CommonListResult<BuySellHistoryRecordModel> getBuySellHistoryData(BuySellHistoryRecordQuery query) {
		log.info("getSellHistoryData req={}",query);
		return buyRecordService.getBuySellHistoryRecordList(query);
	}

	@RequestMapping("/delBuyData")
	public CommonResult delBuyData(Integer id) {
		log.info("delBuyData id={} ",id);
		return buyRecordService.delBuyData(id);
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
