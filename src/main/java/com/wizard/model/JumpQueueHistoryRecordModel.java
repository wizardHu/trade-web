package com.wizard.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class JumpQueueHistoryRecordModel {

    private Integer id;

    private String symbol;

    private Integer type;

    private String orderId;

    private Float lowPrice;

    private Float highPrice;

    private Float jumpPrice;

    private Integer jumpCount;

    private Date createTime;

    private Float oriPrice;

    private Float operPrice;

    private Float amount;

    private Date updateTime;
}
