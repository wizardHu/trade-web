package com.wizard.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class SellOrderRecordModel {

    private Integer id;

    private String symbol;

    private Float buyPrice;

    private Float sellPrice;

    private Long buyIndex;

    private Long sellIndex;

    private String buyOrderId;

    private String sellOrderId;

    private Float amount;

    private Date createTime;

}
