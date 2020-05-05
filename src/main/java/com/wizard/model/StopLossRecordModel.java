

package com.wizard.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class StopLossRecordModel {

    private Integer id;

    private String symbol;

    private Date createTime;

    private Float sellPrice;

    private Float oriPrice;

    private Float oriAmount;

    private String oriOrderId;

    private String orderId;

    private Integer status;

    private Date updateTime;
}
