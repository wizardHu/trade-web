package com.wizard.model;

public class StatisticsModel {

	private Float expend;//消耗了多少个计价币
	
	private Float surplus;//剩余的基础币   eos/usdt  基础币/计价币   
	
	private Float surplusAvgPrice;//剩余的基础币的平均价
	
	//private Float presentPrice;//当前价格
	
	private Float tradeProfit;//交易盈利
	
	//private Float presentProfit;//当前盈利
	
	private Integer buyCount;
	
	private Integer sellCount;

	public Float getExpend() {
		return expend;
	}

	public void setExpend(Float expend) {
		this.expend = expend;
	}

	public Float getSurplus() {
		return surplus;
	}

	public void setSurplus(Float surplus) {
		this.surplus = surplus;
	}

	public Float getSurplusAvgPrice() {
		return surplusAvgPrice;
	}

	public void setSurplusAvgPrice(Float surplusAvgPrice) {
		this.surplusAvgPrice = surplusAvgPrice;
	}

	/*public Float getPresentPrice() {
		return presentPrice;
	}

	public void setPresentPrice(Float presentPrice) {
		this.presentPrice = presentPrice;
	}*/

	public Float getTradeProfit() {
		return tradeProfit;
	}

	public void setTradeProfit(Float tradeProfit) {
		this.tradeProfit = tradeProfit;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

	public Integer getSellCount() {
		return sellCount;
	}

	public void setSellCount(Integer sellCount) {
		this.sellCount = sellCount;
	}

	@Override
	public String toString() {
		return "StatisticsModel [expend=" + expend + ", surplus=" + surplus + ", surplusAvgPrice=" + surplusAvgPrice
				+ ", tradeProfit=" + tradeProfit + ", buyCount=" + buyCount + ", sellCount=" + sellCount + "]";
	}

	/*public Float getPresentProfit() {
		return presentProfit;
	}

	public void setPresentProfit(Float presentProfit) {
		this.presentProfit = presentProfit;
	}*/
}
