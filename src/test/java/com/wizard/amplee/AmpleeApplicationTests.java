package com.wizard.amplee;

import com.wizard.model.*;
import com.wizard.model.from.BuyRecordQuery;
import com.wizard.model.from.BuySellHistoryRecordQuery;
import com.wizard.model.from.JumpQueueHistoryRecordQuery;
import com.wizard.model.from.JumpQueueRecordQuery;
import com.wizard.service.BuyRecordService;
import com.wizard.service.JumpQueueRecordService;
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


}
