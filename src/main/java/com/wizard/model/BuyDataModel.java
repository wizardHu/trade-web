package com.wizard.model;

public class BuyDataModel {
	
	private Float price;
	
	private Float amount;
	
	private String orderId;
	
	private Long timeStamp;
	
	private String symbol;

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	
	public BuyDataModel(Float price, Float amount, String orderId, Long timeStamp, String symbol) {
		this.price = price;
		this.amount = amount;
		this.orderId = orderId;
		this.timeStamp = timeStamp;
		this.symbol = symbol;
	}
	
	public BuyDataModel() {
	}

	@Override
	public String toString() {
		return "BuyDataModel [price=" + price + ", amount=" + amount + ", orderId=" + orderId + ", timeStamp="
				+ timeStamp + ", symbol=" + symbol + "]";
	}
	
}
