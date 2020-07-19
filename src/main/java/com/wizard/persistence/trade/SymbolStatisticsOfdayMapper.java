package com.wizard.persistence.trade;

import com.wizard.model.SymbolStatisticsOfdayModel;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SymbolStatisticsOfdayMapper {

    int addRecord(SymbolStatisticsOfdayModel symbolStatisticsOfdayModel);

    SymbolStatisticsOfdayModel getRecordBySymbolAndTime(@Param("symbol") String symbol, @Param("createTime") Date createTime);

    List<SymbolStatisticsOfdayModel> getRecordByTime( @Param("time") Date time);
}
