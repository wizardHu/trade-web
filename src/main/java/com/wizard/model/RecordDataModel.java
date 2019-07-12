package com.wizard.model;

public class RecordDataModel {

	private Integer type;//1:buy  0:sell
	
	private Long id;
	
	private Float buyPrice;
	
	private Float amount;
	
	private Float sellPrice;
	
	private Long timeStamp;
	
	private String timeStampStr;
	
	private String symbol;
	
	private Float profit;
	
	private Float fee;
	
	private Float expend;
	
	public Float getExpend() {
		return expend;
	}
	
	public void setExpend(Float expend) {
		this.expend = expend;
	}
	
	public Float getProfit() {
		return profit;
	}
	
	public void setProfit(Float profit) {
		this.profit = profit;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Float buyPrice) {
		this.buyPrice = buyPrice;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Float getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Float sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getTimeStampStr() {
		return timeStampStr;
	}

	public void setTimeStampStr(String timeStampStr) {
		this.timeStampStr = timeStampStr;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public Float getFee() {
		return fee;
	}

	public void setFee(Float fee) {
		this.fee = fee;
	}

	public RecordDataModel(Integer type, Long id, Float buyPrice, Float amount, Long timeStamp, String timeStampStr,
			String symbol,Float fee,Float expend) {
		this.type = type;
		this.id = id;
		this.buyPrice = buyPrice;
		this.amount = amount;
		this.timeStamp = timeStamp;
		this.timeStampStr = timeStampStr;
		this.symbol = symbol;
		this.fee = fee;
		this.expend = expend;
	}
	
	public RecordDataModel() {
	}
	
	public RecordDataModel(Integer type, Long id, Float buyPrice, Float amount, Float sellPrice, Long timeStamp,
			String timeStampStr, String symbol,Float fee,Float expend) {
		this.type = type;
		this.id = id;
		this.buyPrice = buyPrice;
		this.amount = amount;
		this.sellPrice = sellPrice;
		this.timeStamp = timeStamp;
		this.timeStampStr = timeStampStr;
		this.symbol = symbol;
		this.fee = fee;
		this.expend = expend;
	}

	@Override
	public String toString() {
		return "RecordDataModel [type=" + type + ", id=" + id + ", buyPrice=" + buyPrice + ", amount=" + amount
				+ ", sellPrice=" + sellPrice + ", timeStamp=" + timeStamp + ", timeStampStr=" + timeStampStr
				+ ", symbol=" + symbol + ", profit=" + profit + ", fee=" + fee + ", expend=" + expend + "]";
	}

}
