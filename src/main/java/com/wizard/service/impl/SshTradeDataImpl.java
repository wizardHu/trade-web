package com.wizard.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wizard.model.BuyDataModel;
import com.wizard.model.CommonListResult;
import com.wizard.model.CommonMapResult;
import com.wizard.model.CommonResult;
import com.wizard.model.RecordAndStatisticsModel;
import com.wizard.model.RecordDataModel;
import com.wizard.model.StatisticsModel;
import com.wizard.service.ITradeData;
import com.wizard.util.Constant;
import com.wizard.util.SshUtil;

@Service("sshTradeDataImpl")
public class SshTradeDataImpl implements ITradeData {
	
	private static final Logger logger = LoggerFactory.getLogger(SshTradeDataImpl.class);
	
	@Override
	public CommonMapResult<String, List<BuyDataModel>> getBuyDataModels() {
		List<BuyDataModel> list = doGetBuyDataModels();

		Map<String, List<BuyDataModel>> map = list.stream().collect(Collectors.groupingBy(BuyDataModel::getSymbol));
		
		return CommonMapResult.getSuccResultWithData(map);
	}


	@Override
	public CommonListResult<RecordAndStatisticsModel> getRecordDataModels() {

		
		List<RecordAndStatisticsModel> recordAndStatisticsModelList = new ArrayList<RecordAndStatisticsModel>();
		
		List<RecordDataModel> list = doGetRecordDataModels();

		Map<String, List<RecordDataModel>> map = list.stream().collect(Collectors.groupingBy(RecordDataModel::getSymbol));
		
		for (Map.Entry<String,List<RecordDataModel>>  entry : map.entrySet()) {
			
			RecordAndStatisticsModel recordAndStatisticsModel = buildRecordAndStatisticsModel(entry);
			
			recordAndStatisticsModelList.add(recordAndStatisticsModel);
			
		}
		
		return CommonListResult.getSuccResultWithData(recordAndStatisticsModelList);
		
	}
	
	
	@Override
	public CommonResult getPresentPrice(String symbol) {

		String jsonResult = SshUtil.execCommand("python amplee/BiTradeUtil.py "+symbol);
		
		logger.info("command myshell/getRecord.sh result={}",jsonResult);
		
		JSONObject json = JSONObject.parseObject(jsonResult);
		if(json != null) {
			JSONArray array = JSON.parseArray(json.get("data")+"");
			if(array!= null && array.size() > 0) {
				String close = array.getJSONObject(0).get("close")+"";
				return CommonResult.getSuccResultWithData(close);
			}
		}
		
		return CommonResult.getSuccResult();
	}
	

	/**
	 * 参数组装
	 * @author hulujie
	 * @since 2019年7月12日 下午6:34:44
	 * @param entry
	 * @return
	 */
	private RecordAndStatisticsModel buildRecordAndStatisticsModel(Map.Entry<String, List<RecordDataModel>> entry) {
		RecordAndStatisticsModel recordAndStatisticsModel = new RecordAndStatisticsModel();
		
		String symbol = entry.getKey();
		List<RecordDataModel> recordDataModelList = entry.getValue();
		
		StatisticsModel statisticsModel = buildStatisticsModel(recordDataModelList);
		
		recordAndStatisticsModel.setRecordDataModelList(recordDataModelList);
		recordAndStatisticsModel.setSymbol(symbol);
		recordAndStatisticsModel.setStatisticsModel(statisticsModel);
		
		return recordAndStatisticsModel;
	}

	
	private StatisticsModel buildStatisticsModel(List<RecordDataModel> recordDataModelList) {
		
		StatisticsModel statisticsModel = new StatisticsModel();
		
		float expend = 0.0f;
		float surplus = 0.0f;
		float tradeProfit = 0.0f;
		Integer buyCount = 0;
		Integer sellCount = 0;
		
		for (RecordDataModel recordDataModel : recordDataModelList) {
			
			if(recordDataModel == null) continue;
			
			if(Constant.BUY_TYPE == recordDataModel.getType()) {
				
				expend -= recordDataModel.getExpend();
				surplus += recordDataModel.getAmount();
				buyCount++;
				
			}else if(Constant.SELL_TYPE == recordDataModel.getType()){
				expend += recordDataModel.getExpend();
				tradeProfit += recordDataModel.getProfit();
				surplus -= recordDataModel.getAmount();
				sellCount++;
			}
		}
		
		statisticsModel.setExpend(expend);
		statisticsModel.setTradeProfit(tradeProfit);
		statisticsModel.setSurplus(surplus);
		statisticsModel.setBuyCount(buyCount);
		statisticsModel.setSellCount(sellCount);
		
		if(surplus != 0) {
			statisticsModel.setSurplusAvgPrice(Math.abs(expend/surplus));
		}else {
			statisticsModel.setSurplusAvgPrice(0f);
		}
		
		
		return statisticsModel;
	}


	private List<RecordDataModel> doGetRecordDataModels() {
		String result = SshUtil.execCommand("myshell/getRecord.sh");
		logger.info("command myshell/getRecord.sh result.length={}",result.length());
		List<RecordDataModel> list = new ArrayList<RecordDataModel>();
		
		if(!StringUtils.isEmpty(result)) {
			
			String sz[] = result.split("\r\n");
			String symbol = "";
			
			try {
				
				for (String data : sz) {
					
					if(data.startsWith("-")) {//-xrpusdt-record
						symbol = data.substring(1, data.length()-7);
						continue;
					}
					
					RecordDataModel recordDataModel = null;
					String[] params = data.split(",");
					Integer type = Integer.parseInt(params[0]);
					long id = Long.parseLong(params[1]);
					float buyPrice = Float.parseFloat(params[2]);
					float amount = Float.parseFloat(params[3]);
					
					
					if(data.startsWith(Constant.BUY_TYPE+"")) {
						long timeStamp = Long.parseLong(params[4]);
						String timeStampStr = params[5];
						float fee = (float) (buyPrice * amount * 0.002);
						float expend = buyPrice * amount;
						
						recordDataModel = new RecordDataModel(type, id, buyPrice, amount, timeStamp, timeStampStr, symbol,fee,expend);
						
					}else if(data.startsWith(Constant.SELL_TYPE+"")) {
						float sellPrice = Float.parseFloat(params[4]);
						long timeStamp = Long.parseLong(params[5]);
						String timeStampStr = params[6];
						float fee = (float) (sellPrice * amount * 0.002);
						float profit = (float) ((sellPrice-buyPrice) * amount * 0.996);
						float expend = sellPrice * amount;
						
						recordDataModel = new RecordDataModel(type, id, buyPrice, amount, sellPrice, timeStamp, timeStampStr, symbol,fee,expend);
						recordDataModel.setProfit(profit);
					}
					
					if(recordDataModel != null) {
						list.add(recordDataModel);
					}
				}
				
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		list.sort((RecordDataModel h1, RecordDataModel h2) -> h2.getTimeStamp().compareTo(h1.getTimeStamp()));
		
		return list;
	}


	private List<BuyDataModel> doGetBuyDataModels() {
		
		String result = SshUtil.execCommand("myshell/getBuy.sh");
		logger.info("command myshell/getBuy.sh result.length={}",result.length());
		List<BuyDataModel> list = new ArrayList<BuyDataModel>();
		
		if(!StringUtils.isEmpty(result)) {
			
			String sz[] = result.split("\r\n");
			String symbol = "";
			
			try {
				
				for (String data : sz) {
					
					if(data.startsWith("-")) {//-eosusdtbuy
						symbol = data.substring(1, data.length()-3);
						continue;
					}
					
					String[] params = data.split(",");
					float price = Float.parseFloat(params[0]);
					float amount = Float.parseFloat(params[1]);
					String orderId = params[2];
					long timeStamp = Long.parseLong(params[3]);
					
					BuyDataModel buyDataModel = new BuyDataModel(price, amount, orderId, timeStamp, symbol);
					list.add(buyDataModel);
					
				}
				
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		return list;
	}

}
