package com.wizard.model.from;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BuyRecordQuery extends CommonQueryBase{

    private Integer status;

    private String beginUpdateTime;

    private String endUpdateTime;

    private String updateTime;

}
