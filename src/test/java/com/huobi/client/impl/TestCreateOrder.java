package com.huobi.client.impl;

import com.alibaba.fastjson.JSON;
import com.huobi.client.SyncRequestClient;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.request.NewOrderRequest;

import java.math.BigDecimal;


public class TestCreateOrder {


  public static void main(String[] args) {

    SyncRequestClient syncClient = SyncRequestClient.create(
            "1",
            "1");

    NewOrderRequest req = new NewOrderRequest("xrpusdt", AccountType.SPOT, OrderType.SELL_LIMIT,
            new BigDecimal(75), new BigDecimal("0.183"));
   System.out.println(JSON.toJSONString(syncClient.createOrder(req)));
    syncClient.cancelOrder("htusdt", Long.parseLong(JSON.toJSONString(syncClient.createOrder(req))));
  }
}
