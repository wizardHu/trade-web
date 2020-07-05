package com.wizard.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.huobi.client.SyncRequestClient;
import com.huobi.client.model.Candlestick;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class HuoBiService {

    @Autowired(required = false)
    private SyncRequestClient syncRequestClient;

    public LoadingCache<String, Candlestick> candlestickCache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES).build(new CacheLoader<String, Candlestick >() {
        @Override
        public Candlestick  load(String key) {

            /*List<Candlestick> candlestickList = syncRequestClient.getLatestCandlestick(
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

}
