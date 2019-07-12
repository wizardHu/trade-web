package com.wizard.service;

import java.util.List;

import com.wizard.model.BuyDataModel;
import com.wizard.model.CommonListResult;
import com.wizard.model.CommonMapResult;
import com.wizard.model.RecordAndStatisticsModel;

public interface ITradeData {

	/**
	 * 得到购买的数据
	 * @author hulujie
	 * @since 2019年7月12日 下午6:34:22
	 * @return
	 */
	CommonMapResult<String,List<BuyDataModel>> getBuyDataModels();
	
	/**
	 * 得到交易数据
	 * @author hulujie
	 * @since 2019年7月12日 下午6:34:31
	 * @return
	 */
	CommonListResult<RecordAndStatisticsModel> getRecordDataModels();
	
}
