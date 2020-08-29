package com.wizard.amplee;

import com.huobi.client.model.enums.CandlestickInterval;
import com.wizard.model.*;
import com.wizard.model.from.*;
import com.wizard.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class AmpleeApplicationTests {

	@Resource
	private BuyRecordService buyRecordService;

	@Resource
	private JumpQueueRecordService jumpQueueRecordService;

	@Resource
	private StopLossRecordService stopLossRecordService;

	@Resource
	private SellOrderRecordService sellOrderRecordService;

	@Resource
	private TransactionConfigRecordService transactionConfigRecordService;

	@Resource
	private OrderDetailService orderDetailService;

	@Resource
	private KLineService kLineService;

	@Resource
	private StatisticService statisticService;

	@Resource
	private ManualTradeService manualTradeService;

	@Resource
	private HuoBiService huoBiService;

	@Test
	public void getBuyRecord(){

		BuyRecordQuery query = new BuyRecordQuery();
		query.setPage(1);
		query.setPageSize(100);
		query.setSymbol("eosusdt");

		CommonListResult<BuyRecordModel> result = buyRecordService.getBuyRecordList(query);

		System.out.println(result);
	}

	@Test
	public void getBuySellHistoryRecord(){

		BuySellHistoryRecordQuery query = new BuySellHistoryRecordQuery();
		query.setPage(1);
		query.setPageSize(100);
		query.setSymbol("eosusdt");

		CommonListResult<BuySellHistoryRecordModel> result = buyRecordService.getBuySellHistoryRecordList(query);

		System.out.println(result);
	}

	@Test
	public void getJumpQueueRecord(){

		JumpQueueRecordQuery query = new JumpQueueRecordQuery();
		query.setPage(1);
		query.setPageSize(100);
		query.setSymbol("btcusdt");

		CommonListResult<JumpQueueRecordModel> result = jumpQueueRecordService.getJumpQueueRecordList(query);

		System.out.println(result);
	}

	@Test
	public void getJumpQueueHistoryRecordList(){

		JumpQueueHistoryRecordQuery query = new JumpQueueHistoryRecordQuery();
		query.setPage(1);
		query.setPageSize(100);
		query.setSymbol("btcusdt");

		CommonListResult<JumpQueueHistoryRecordModel> result = jumpQueueRecordService.getJumpQueueHistoryRecordList(query);

		System.out.println(result);
	}

	@Test
	public void getStopLossRecordList(){

		StopLossRecordQuery query = new StopLossRecordQuery();
		query.setPage(1);
		query.setPageSize(100);
		query.setSymbol("btcusdt");

		CommonListResult<StopLossRecordModel> result = stopLossRecordService.getStopLossRecordList(query);

		System.out.println(result);
	}


	@Test
	public void getStopLossHistoryRecordList(){

		StopLossHistoryRecordQuery query = new StopLossHistoryRecordQuery();
		query.setPage(1);
		query.setPageSize(100);
		query.setSymbol("btcusdt");

		CommonListResult<StopLossHistoryRecordModel> result = stopLossRecordService.getStopLossHistoryRecordList(query);

		System.out.println(result);
	}

	@Test
	public void getSellOrderRecordList(){

		SellOrderRecordQuery query = new SellOrderRecordQuery();
		query.setPage(1);
		query.setPageSize(100);
		query.setSymbol("btcusdt");

		CommonListResult<SellOrderRecordModel> result = sellOrderRecordService.getSellOrderRecordList(query);

		System.out.println(result);
	}

	@Test
	public void getTransactionConfigRecordList(){

		TransactionConfigQuery query = new TransactionConfigQuery();

		CommonListResult<TransactionConfigModel> result = transactionConfigRecordService.getTransactionConfigRecordList(query);

		System.out.println(result);
	}

	@Test
	public void getOrderDetailModels(){
		CommonListResult<OrderDetailModel> result = orderDetailService.getOrderDetailModels("69289411629437610");

		System.out.println(result);
	}

	@Test
	public void modTransactionConfigModel(){

		TransactionConfigUpdate transactionConfigUpdate = new TransactionConfigUpdate();
		transactionConfigUpdate.setId(5);
		transactionConfigUpdate.setEveryExpense(10.3f);
		transactionConfigUpdate.setStatus(0);

		CommonResult result = transactionConfigRecordService.modTransactionConfigModel(transactionConfigUpdate);

		System.out.println(result);
	}

	@Test
	public void getKline(){

		System.out.println(kLineService.getKline("eosusdt", "2020-07-04 03:10:00"));

	}

	@Test
	public void statisticProfit(){

		System.out.println(statisticService.statisticProfit());

	}

	@Test
	public void testBuy(){

		ManualTradeAdd manualTradeAdd = new ManualTradeAdd();
		manualTradeAdd.setSymbol("eosusdt");
		manualTradeAdd.setAmountPrecision(2);
		manualTradeAdd.setBuyPrice(2.3f);
		manualTradeAdd.setPricePrecision(2);
		manualTradeAdd.setExpense(10f);

		System.out.println(manualTradeService.buy(manualTradeAdd));

	}

	@Test
	public void checkManualTrade(){
		manualTradeService.checkManualTrade();
	}

	@Test
	public void getCandlestick(){
		huoBiService.getCandlestick("eosusdt", CandlestickInterval.MIN30,10);
	}
}
