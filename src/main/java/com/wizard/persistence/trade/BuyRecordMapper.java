package com.wizard.persistence.trade;

import com.wizard.model.BuyRecordModel;
import com.wizard.model.BuySellHistoryRecordModel;
import com.wizard.model.from.BuyRecordQuery;
import com.wizard.model.from.BuySellHistoryRecordQuery;

import java.util.List;

public interface BuyRecordMapper {

    List<BuyRecordModel> getBuyRecord(BuyRecordQuery query);

    int getBuyRecordCount(BuyRecordQuery query);

    List<BuySellHistoryRecordModel> getBuySellHistoryRecord(BuySellHistoryRecordQuery query);

    int getBuySellHistoryRecordCount(BuySellHistoryRecordQuery query);

    int delById(Integer id);
}
