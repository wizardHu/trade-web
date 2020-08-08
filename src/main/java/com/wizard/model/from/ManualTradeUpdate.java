package com.wizard.model.from;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ManualTradeUpdate {

    private Integer id;
    private Integer status;
    private Float lowOperPrice;
    private Float highOperPrice;
    private Float sellPrice;
    private String sellOrderId;
}
