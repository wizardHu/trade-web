package com.huobi.client.impl;

import com.alibaba.fastjson.JSON;
import com.huobi.client.SyncRequestClient;
import com.huobi.client.model.Account;
import com.huobi.client.model.enums.AccountType;


public class TestCreateOrder {


  public static void main(String[] args) {

    SyncRequestClient syncClient = SyncRequestClient.create(
            "8419e46c-b85831c9-8f1c62d2-f226c",
            "d3c5dfc3-c126780c-4dff10f0-5d3f1");

   /* NewOrderRequest req = new NewOrderRequest("xrpusdt", AccountType.SPOT, OrderType.SELL_LIMIT,
            new BigDecimal(75), new BigDecimal("0.183"));
   System.out.println(JSON.toJSONString(syncClient.createOrder(req)));
    syncClient.cancelOrder("htusdt", Long.parseLong(JSON.toJSONString(syncClient.createOrder(req))));*/
      Account account =  syncClient.getAccountBalance(AccountType.SPOT) ;
      System.out.println(JSON.toJSONString(account));


  }
}
