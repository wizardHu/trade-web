package com.wizard.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SymbolConfigModel {

    private Integer id;
    private String symbol;
    private Integer pricePrecision;
    private Integer amountPrecision;
    private Integer status;
}
