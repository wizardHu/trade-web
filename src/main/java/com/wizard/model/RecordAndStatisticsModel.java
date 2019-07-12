package com.wizard.model;

import java.util.List;

public class RecordAndStatisticsModel {

	private String symbol;
	
	private List<RecordDataModel> recordDataModelList;
	
	private StatisticsModel statisticsModel;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public List<RecordDataModel> getRecordDataModelList() {
		return recordDataModelList;
	}

	public void setRecordDataModelList(List<RecordDataModel> recordDataModelList) {
		this.recordDataModelList = recordDataModelList;
	}

	public StatisticsModel getStatisticsModel() {
		return statisticsModel;
	}

	public void setStatisticsModel(StatisticsModel statisticsModel) {
		this.statisticsModel = statisticsModel;
	}
	
}
