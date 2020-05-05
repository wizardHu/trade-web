package com.wizard.persistence.trade;

import com.wizard.model.SellOrderRecordModel;
import com.wizard.model.from.SellOrderRecordQuery;

import java.util.List;

public interface SellOrderRecordMapper {

    List<SellOrderRecordModel> getSellOrderRecord(SellOrderRecordQuery query);

    int getSellOrderRecordCount(SellOrderRecordQuery query);

}
