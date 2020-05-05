package com.wizard.model.from;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StopLossHistoryRecordQuery extends CommonQueryBase{

    private Integer type;

    private String oriOrderId;

}
