

package com.wizard.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class StopLossHistoryRecordModel {

    private Integer id;

    private Integer type;

    private String symbol;

    private Date createTime;

    private Float operPrice;

    private Float lastPrice;

    private Float amount;

    private String oriOrderId;

    private String orderId;

}
