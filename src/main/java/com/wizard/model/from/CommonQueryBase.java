package com.wizard.model.from;

import lombok.Data;

import java.util.Date;

@Data
public class CommonQueryBase extends CommonPaginationQueryBase{

    private String symbol;

    private Integer id;

    private String beginCreateTime;

    private String endCreateTime;

    private String orderId;
}
