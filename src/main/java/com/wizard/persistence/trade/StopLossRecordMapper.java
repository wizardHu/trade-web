package com.wizard.persistence.trade;

import com.wizard.model.StopLossHistoryRecordModel;
import com.wizard.model.StopLossRecordModel;
import com.wizard.model.from.StopLossHistoryRecordQuery;
import com.wizard.model.from.StopLossRecordQuery;

import java.util.List;

public interface StopLossRecordMapper {

    List<StopLossRecordModel> getStopLossRecord(StopLossRecordQuery query);

    int getStopLossRecordCount(StopLossRecordQuery query);

    List<StopLossHistoryRecordModel> getStopLossHistoryRecord(StopLossHistoryRecordQuery query);

    int getStopLossHistoryRecordCount(StopLossHistoryRecordQuery query);
}
