package com.wizard.service;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.huobi.client.SyncRequestClient;
import com.huobi.client.model.Account;
import com.huobi.client.model.Candlestick;
import com.huobi.client.model.Order;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.CandlestickInterval;
import com.huobi.client.model.enums.OrderState;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.request.NewOrderRequest;
import com.wizard.model.CommonListResult;
import com.wizard.model.PriceModel;
import com.wizard.model.TransactionConfigModel;
import com.wizard.model.from.TransactionConfigQuery;
import com.wizard.persistence.trade.TransactionConfigMapper;
import com.wizard.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class HuoBiService {

    @Autowired(required = false)
    private SyncRequestClient syncRequestClient;

    @Resource
    private TransactionConfigMapper transactionConfigMapper;

    private Integer MAX_TRY = 3;

    public LoadingCache<String, Candlestick> candlestickCache = CacheBuilder.newBuilder()
            .expireAfterWrite(30, TimeUnit.SECONDS).build(new CacheLoader<String, Candlestick >() {
        @Override
        public Candlestick  load(String key) {

            int tryTime = 0;
            while (tryTime < MAX_TRY){
                try {
                    List<Candlestick> candlestickList = syncRequestClient.getLatestCandlestick(
                            key, CandlestickInterval.MIN1, 1);

                    if(!CollectionUtils.isEmpty(candlestickList)){

                        Candlestick candlestick = candlestickList.get(0);
                        if(candlestick == null ){
                            CommonUtil.sleep(1000);
                            tryTime++;
                            continue;
                        }

                        return candlestick;
                    }

                } catch (Exception e) {
                    tryTime++;
                    CommonUtil.sleep(1000);
                }

            }

            return new Candlestick();

          /* Candlestick candlestick = new Candlestick();
           candlestick.setClose(new BigDecimal("4.5"));
            return candlestick;*/

        }
    });

    /**
     * 得到所有交易对的当前价格
     * @return
     */
    public CommonListResult<PriceModel> getNowPrice() {

        CommonListResult<PriceModel> result = CommonListResult.getSuccListResult();
        List<PriceModel> priceModelList = new ArrayList<PriceModel>();

        TransactionConfigQuery query = new TransactionConfigQuery();
        List<TransactionConfigModel>  transactionConfigModelList = transactionConfigMapper.getTransactionConfigRecord(query);

        for (TransactionConfigModel transactionConfigModel : transactionConfigModelList) {
            Candlestick candlestick = candlestickCache.getUnchecked(transactionConfigModel.getSymbol());

            PriceModel priceModel = new PriceModel(candlestick.getClose().floatValue(),transactionConfigModel.getSymbol());
            priceModelList.add(priceModel);
        }

        result.setResultList(priceModelList);
        return result;
    }

    /**
     * 得到该账号类型下的余额
     * @param accountType
     * @return
     */
    public Account getAccountBalance(AccountType accountType){

        Account account = null;

        try {
            account = syncRequestClient.getAccountBalance(accountType) ;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            CommonUtil.sleep(1000);
            account = syncRequestClient.getAccountBalance(accountType) ;
        }

        return account;
    }

    /**
     * 创建订单
     * @param symbol
     * @param amount
     * @param price
     * @param type
     * @return
     */
    public Long createOrder(String symbol,BigDecimal amount,BigDecimal price, OrderType type){
        try {

            log.info("createOrder symbol={},amount={},price={},type={}",symbol,amount.floatValue(),price.floatValue(),type.toString());

            NewOrderRequest request = new NewOrderRequest(
                    symbol, AccountType.SPOT, type, amount, price);

            log.info("createOrder request={}", JSON.toJSONString(request));
            long id = syncRequestClient.createOrder(request);
            log.info("createOrder id={}", id);

            return id;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        return null;
//        return 12l;
    }

    public Candlestick getCurrentCandlestick(String symbol){
        try {
            List<Candlestick> candlestickList = syncRequestClient.getLatestCandlestick(
                    symbol, CandlestickInterval.MIN1, 1);

            if(!CollectionUtils.isEmpty(candlestickList)) return candlestickList.get(0);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return null;

       /* if("eosusdt".equals(symbol)){
            Candlestick candlestick = new Candlestick();
            candlestick.setClose(new BigDecimal("3.35"));
            return candlestick;
        }else{
            Candlestick candlestick = new Candlestick();
            candlestick.setClose(new BigDecimal("4.6775"));
            return candlestick;
        }*/

    }

    public boolean checkOrder(Long orderId,String symbol) {
        try {
            log.info("checkOrder symbol={} orderId={}",symbol,orderId);
            Order order = syncRequestClient.getOrder(symbol,orderId);
            log.info("checkOrder resp={}", JSON.toJSONString(order));

            return OrderState.FILLED.toString().equals(order.getState().toString());

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return false;
//        return true;
    }
}
