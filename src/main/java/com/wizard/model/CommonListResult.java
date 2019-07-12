package com.wizard.model;

import java.util.List;

public class CommonListResult<T> extends CommonResult{

	private List<T> resultList;

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

	public CommonListResult() {

	}

	public CommonListResult(String description) {
		this.description = description;
		this.isSuccess = true;
	}



	public static <T> CommonListResult<T> getFailedResult(int code, String message) {
		CommonListResult<T> result = new CommonListResult<T>();
		result.code = code;
		result.description = message;
		result.isSuccess = false;
		return result;
	}

	public static <T> CommonListResult<T> getSuccResult() {
		CommonListResult<T> result = new CommonListResult<T>();
		result.setSuccess(true);
		return result;
	}

	public static <T> CommonListResult<T> getSuccResultWithData(List<T> resultList) {
		CommonListResult<T> result = new CommonListResult<T>();
		result.setSuccess(true);
		result.setResultList(resultList);
		return result;
	}

}
