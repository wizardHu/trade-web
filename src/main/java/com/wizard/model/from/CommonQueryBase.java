package com.wizard.model.from;

import lombok.Data;


@Data
public class CommonQueryBase extends CommonPaginationQueryBase{

    private String symbol;

    private Integer id;

    private String beginCreateTime;

    private String endCreateTime;

    private String orderId;
}
