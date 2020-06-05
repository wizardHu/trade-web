package com.wizard.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class BuyRecordModel {

	private Integer id;

	private String symbol;

	private Float price;

	private Float oriPrice;

	private Long buyIndex;
	
	private Float amount;
	
	private String orderId;

	private Float minIncome;

	private Float lastPrice;

	private Integer status;

	private Date updateTime;

	private Date createTime;


}
