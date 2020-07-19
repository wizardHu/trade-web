package com.wizard.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.huobi.client.SyncRequestClient;
import com.huobi.client.model.Account;
import com.huobi.client.model.Candlestick;
import com.huobi.client.model.enums.AccountType;
import com.wizard.model.CommonListResult;
import com.wizard.model.PriceModel;
import com.wizard.model.TransactionConfigModel;
import com.wizard.model.from.TransactionConfigQuery;
import com.wizard.persistence.trade.TransactionConfigMapper;
import com.wizard.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public LoadingCache<String, Candlestick> candlestickCache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES).build(new CacheLoader<String, Candlestick >() {
        @Override
        public Candlestick  load(String key) {

           /* List<Candlestick> candlestickList = syncRequestClient.getLatestCandlestick(
                    key, CandlestickInterval.MIN1, 1);

            if(!CollectionUtils.isEmpty(candlestickList)){
                return candlestickList.get(0);
            }

            return new Candlestick();*/

           Candlestick candlestick = new Candlestick();
           candlestick.setClose(new BigDecimal("4.5"));
            return candlestick;

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
}
