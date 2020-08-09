package com.wizard.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class ManualTradeBean {

    private Integer id;
    private String symbol;
    private Float buyPrice;
    private Float amount;
    private String buyOrderId;
    private String sellOrderId;
    private Integer pricePrecision;
    private Integer amountPrecision;
    private Integer status;
    private Float lowOperPrice;
    private Float highOperPrice;
    private Float minIncome;
    private Date createTime;
    private Date updateTime;
    private Float sellPrice;

    private Float profit;
    private Float profitPercentage;
}
