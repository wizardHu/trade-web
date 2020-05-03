package com.wizard.service;

import java.util.List;

import com.wizard.model.BuyRecordModel;
import com.wizard.model.CommonListResult;
import com.wizard.model.CommonMapResult;
import com.wizard.model.CommonResult;
import com.wizard.model.RecordAndStatisticsModel;
import com.wizard.model.RecordDataModel;

public interface ITradeData {

	/**
	 * 得到购买的数据
	 * @author hulujie
	 * @since 2019年7月12日 下午6:34:22
	 * @return
	 */
	CommonMapResult<String,List<BuyRecordModel>> getBuyDataModels();
	
	/**
	 * 得到交易数据
	 * @author hulujie
	 * @since 2019年7月12日 下午6:34:31
	 * @return
	 */
	CommonListResult<RecordAndStatisticsModel> getRecordDataModels();
	
	/**
	 * 得到当前价格
	 * @author hulujie
	 * @since 2019年7月15日 上午11:05:16
	 * @param symbol
	 * @return
	 */
	CommonResult getPresentPrice(String symbol);
	
	/**
	 *  k线
	 * @author hulujie
	 * @since 2019年7月16日 下午8:32:00
	 * @param symbol
	 * @param period
	 * @param size
	 * @return
	 */
	CommonListResult<Object[]> getKline(String symbol, String period,int size);

	/**
	 * 根据交易对得到
	 * @author hulujie
	 * @since 2019年7月19日 下午3:45:22
	 * @param symbol
	 * @return
	 */
	CommonListResult<RecordDataModel> getRecordDataBySymbol(String symbol);
	
	/**
	 * 得到余额
	 * @author hulujie
	 * @since 2019年7月15日 上午11:05:16
	 * @param symbol
	 * @return
	 */
	CommonResult getPresentBlance(String symbol);

	/**
	 * 得到紧急卖出去的量
	 * @author Administrator
	 * @since 2019年7月27日 下午12:29:08
	 * @return
	 */
	CommonResult getSellCount();
}
