package com.wizard.service;

import com.wizard.model.CommonListResult;
import com.wizard.persistence.trade.KlineMapper;
import com.wizard.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class KLineService {

    @Resource
    private KlineMapper klineMapper;

    public CommonListResult<Object[]> getKline(String symbol, String time) {

        Date tradeTime = DateUtils.stringToDate(time,"yyyy-MM-dd HH:mm:ss");
        Date beginTime = DateUtils.addMinute(tradeTime, -600);
        Date endTime = DateUtils.addMinute(tradeTime, 300);

        log.info("getKline tradeTime={} beginTime={} endTime={}",time,DateUtils.formatDate(beginTime,"yyyy-MM-dd HH:mm:ss"),
                DateUtils.formatDate(endTime,"yyyy-MM-dd HH:mm:ss"));

        List<Map<String,Object>> mapList = klineMapper.getKline(symbol,beginTime, endTime);

		List<Object[]> list = new ArrayList<>();

		if(!CollectionUtils.isEmpty(mapList)) {

            for (Map<String, Object> stringObjectMap : mapList) {

                String close = stringObjectMap.get("close")+"";
                String open =  stringObjectMap.get("open")+"";
                String high =  stringObjectMap.get("high")+"";
                String low =  stringObjectMap.get("low")+"";
                String date =  DateUtils.formatDate((Date) stringObjectMap.get("create_time"),"MM-dd HH:mm:ss");

                Object[] obj = new Object[] {date,open,close,low,high};
                list.add(obj);
            }
		}

		return CommonListResult.getSuccResultWithData(list);
	}

}
