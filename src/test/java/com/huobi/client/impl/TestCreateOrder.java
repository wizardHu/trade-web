package com.huobi.client.impl;

import com.alibaba.fastjson.JSON;
import com.huobi.client.RequestOptions;
import com.huobi.client.SyncRequestClient;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.model.Account;
import com.huobi.client.model.User;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.request.NewOrderRequest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AccountsInfoMap.class})
@PowerMockIgnore({"javax.crypto.*"})

public class TestCreateOrder {

  private RestApiRequestImpl impl = null;
  private NewOrderRequest CorrectnewOrderRequest;
  private NewOrderRequest WrongnewOrderRequest;

  private static final String data = "{\"status\":\"ok\",\"data\":\"24876987459\"}";
  private static final String Errordata = "{\"status\":\"ok\"}";

  User testAccount = new User();
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void Initialize() {
    impl = new RestApiRequestImpl("8419e46c-b85831c9-8f1c62d2-f226c", "d3c5dfc3-c126780c-4dff10f0-5d3f1", new RequestOptions());
    CorrectnewOrderRequest = new NewOrderRequest("htbtc",
        AccountType.SPOT,
        OrderType.SELL_LIMIT,
        BigDecimal.valueOf(1.0),
        BigDecimal.valueOf(1.0));
    WrongnewOrderRequest = new NewOrderRequest("!",
        AccountType.SPOT,
        OrderType.SELL_LIMIT,
        BigDecimal.valueOf(1.0),
        BigDecimal.valueOf(1.0));
    List<Account> accountList = new LinkedList<>();
    Account test = new Account();
    test.setType(AccountType.SPOT);
    test.setId(12345L);
    accountList.add(test);
    testAccount.setAccounts(accountList);
    //map.put("123",)
  }

  @Test
  public void testResult_Normal() {
    PowerMockito.mockStatic(AccountsInfoMap.class);
    PowerMockito.when(AccountsInfoMap.getUser("12345")).thenReturn(testAccount);
    RestApiRequest<Long> restApiRequest =
        impl.createOrder(CorrectnewOrderRequest);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(data);
    long id = restApiRequest.jsonParser.parseJson(jsonWrapper);
    assertEquals(24876987459L, id);

  }

  @Test
  public void testResult_Unexpected() {
    PowerMockito.mockStatic(AccountsInfoMap.class);
    PowerMockito.when(AccountsInfoMap.getUser("12345")).thenReturn(testAccount);
    RestApiRequest<Long> restApiRequest =
        impl.createOrder(CorrectnewOrderRequest);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(Errordata);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    restApiRequest.jsonParser.parseJson(jsonWrapper);
  }

  @Test
  public void testInvalidSymbol() {
    thrown.expect(HuobiApiException.class);
    impl.createOrder(WrongnewOrderRequest);
  }

  @Test
  public void testNullCurrency() {
    thrown.expect(HuobiApiException.class);
    NewOrderRequest req = new NewOrderRequest("btcusdt", null, null, null, null);
    impl.createOrder(req);
  }

  @Test
  public void test_limit() {
    thrown.expect(HuobiApiException.class);
    NewOrderRequest req = new NewOrderRequest("xrpusdt", AccountType.SPOT, OrderType.SELL_LIMIT,
        new BigDecimal(16), new BigDecimal(0.20));
    impl.createOrder(req);
  }

  public static void main(String[] args) {

    SyncRequestClient syncClient = SyncRequestClient.create(
            "1",
            "1");

//    RestApiRequestImpl impl = new RestApiRequestImpl("8419e46c-b85831c9-8f1c62d2-f226c", "d3c5dfc3-c126780c-4dff10f0-5d3f1", new RequestOptions());
//    AccountsInfoMap.updateUserInfo("8419e46c-b85831c9-8f1c62d2-f226c",impl);
    NewOrderRequest req = new NewOrderRequest("xrpusdt", AccountType.SPOT, OrderType.SELL_LIMIT,
            new BigDecimal(75), new BigDecimal("0.183"));
   System.out.println(JSON.toJSONString(syncClient.createOrder(req)));
    syncClient.cancelOrder("htusdt", Long.parseLong(JSON.toJSONString(syncClient.createOrder(req))));
  }
}
