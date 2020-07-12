package com.wizard.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class StatisticProfitModel {

    private Date beginTime;

    private Date endTime;

    private Float profit = Float.valueOf("0");

    private String symbol;

    private String statisticPeriod;

}
