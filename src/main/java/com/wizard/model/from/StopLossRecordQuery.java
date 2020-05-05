package com.wizard.model.from;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StopLossRecordQuery extends CommonQueryBase{

    private Integer status;

    private String oriOrderId;

}
