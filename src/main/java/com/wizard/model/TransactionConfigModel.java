package com.wizard.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class TransactionConfigModel {

    private Integer id;

    private String symbol;

    private Float everyExpense;

    private Float tradeGap;

    private Float minIncome;

    private String period;

    private Integer precision;

    private Float stopLoss;

    private Integer status;

    private Date updateTime;

    private Integer pricePrecision;

}
