package com.wizard.amplee;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wizard.model.CommonResult;
import com.wizard.service.ITradeData;
import com.wizard.util.SshUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AmpleeApplicationTests {

	@Resource(name="sshTradeDataImpl")
	private ITradeData sshTradeDataImpl;
	
	@Test
	public void testSSH() {
		System.out.println(JSON.toJSONString(sshTradeDataImpl.getBuyDataModels()));
		System.out.println(JSON.toJSONString(sshTradeDataImpl.getRecordDataModels()));
		
	}
	
	@Test
	public void contextLoads() {
		String jsonResult = SshUtil.execCommand("python amplee/BiTradeUtil.py eosusdt");
		System.out.println(jsonResult);
		JSONObject json = JSONObject.parseObject(jsonResult);
		if(json != null) {
			JSONArray array = JSON.parseArray(json.get("data")+"");
			String close = array.getJSONObject(0).get("close")+"";
			System.out.println(close);
		}
	}
	
	@Test
	public void presentUsdt() {
		String command = "python amplee/presentUsdt.py ";
		String result = SshUtil.execCommand(command);
		
		System.out.println( result);
		
	}

}
