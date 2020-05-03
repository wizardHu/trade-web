package com.wizard.amplee;

import javax.annotation.Resource;

import com.wizard.persistence.trade.BuyRecordMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class AmpleeApplicationTests {

	@Resource
	private BuyRecordMapper buyRecordMapper;

	@Test
	public void getBuyRecord(){
		System.out.println(buyRecordMapper.getBuyRecord());
	}

}
