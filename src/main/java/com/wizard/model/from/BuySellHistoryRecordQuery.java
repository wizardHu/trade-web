package com.wizard.model.from;

import lombok.Data;

@Data
public class BuySellHistoryRecordQuery extends CommonQueryBase{

    private Integer type;

    private String buyOrderId;

    private String sellOrderId;

}
