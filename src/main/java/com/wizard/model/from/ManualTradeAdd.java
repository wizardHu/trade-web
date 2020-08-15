package com.wizard.model.from;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ManualTradeAdd {

    private String symbol;
    private Float buyPrice;
    private Float amount;
    private Float expense;
    private Float minIncome;
    private String buyOrderId;
    private Integer pricePrecision;
    private Integer amountPrecision;
}
