package com.wizard.model.from;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TransactionConfigAdd {

    private String symbol;

    private Float everyExpense;

    private Float tradeGap;

    private Float minIncome;

    private String period;

    private Integer precision;

    private Float stopLoss;

    private Integer status;

    private String passWord;

    private Integer pricePrecision;

}
