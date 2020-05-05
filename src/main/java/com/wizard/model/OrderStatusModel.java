package com.wizard.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderStatusModel {

    private Integer status;

    private String desc;

    private String symbol;

    private String orderId;

}
