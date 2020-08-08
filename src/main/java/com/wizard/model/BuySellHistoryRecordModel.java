package com.wizard.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class BuySellHistoryRecordModel {

	private Integer id;

	private String symbol;

	private Integer type;

	private Integer operType;

	private Float buyPrice;

	private Float sellPrice;

	private Float profit;

	private Float profitPercentage;

	private String buyOrderId;

	private String sellOrderId;

	private Float amount;

	private Long operIndex;

	private Date createTime;

}
