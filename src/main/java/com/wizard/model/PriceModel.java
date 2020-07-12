package com.wizard.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PriceModel {

    private Float price;
    private String symbol;

    public PriceModel(){}

    public PriceModel(Float price,String symbol){
        this.price = price;
        this.symbol = symbol;
    }

}
