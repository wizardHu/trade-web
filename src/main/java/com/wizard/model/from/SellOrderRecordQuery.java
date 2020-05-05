package com.wizard.model.from;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SellOrderRecordQuery extends CommonQueryBase{

    private String sellOrderId;

    private String buyOrderId;

}
