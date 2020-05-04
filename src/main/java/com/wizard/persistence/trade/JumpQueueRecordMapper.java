package com.wizard.persistence.trade;

import com.wizard.model.BuyRecordModel;
import com.wizard.model.BuySellHistoryRecordModel;
import com.wizard.model.JumpQueueHistoryRecordModel;
import com.wizard.model.JumpQueueRecordModel;
import com.wizard.model.from.BuyRecordQuery;
import com.wizard.model.from.BuySellHistoryRecordQuery;
import com.wizard.model.from.JumpQueueHistoryRecordQuery;
import com.wizard.model.from.JumpQueueRecordQuery;

import java.util.List;

public interface JumpQueueRecordMapper {

    List<JumpQueueRecordModel> getJumpQueueRecord(JumpQueueRecordQuery query);

    int getJumpQueueRecordCount(JumpQueueRecordQuery query);

    List<JumpQueueHistoryRecordModel> getJumpQueueHistoryRecord(JumpQueueHistoryRecordQuery query);

    int getJumpQueueHistoryRecordCount(JumpQueueHistoryRecordQuery query);

}
