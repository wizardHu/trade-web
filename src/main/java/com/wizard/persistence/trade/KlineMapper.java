package com.wizard.persistence.trade;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface KlineMapper {

    List<Map<String,Object>> getKline(@Param("symbol") String symbol, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

}
