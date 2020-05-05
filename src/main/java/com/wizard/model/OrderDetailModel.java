package com.wizard.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class OrderDetailModel {

    private Integer type;

    private Date createTime;

    private Float operPrice;

    private String symbol;

    private Float amount;

    private String orderId;

    private Integer from;//1 正常买卖   2 止损买卖

}
