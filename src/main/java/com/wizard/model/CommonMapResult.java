package com.wizard.model;

import java.util.Map;

public class CommonMapResult<T,K> extends CommonResult{

	private Map<T,K> resultMap;

	
	public Map<T, K> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<T, K> resultMap) {
		this.resultMap = resultMap;
	}

	public CommonMapResult() {

	}

	public CommonMapResult(String description) {
		this.description = description;
		this.isSuccess = true;
	}



	public static <T,K> CommonMapResult<T,K> getFailedResult(int code, String message) {
		CommonMapResult<T,K> result = new CommonMapResult<T,K>();
		result.code = code;
		result.description = message;
		result.isSuccess = false;
		return result;
	}

	public static <T,K> CommonMapResult<T,K> getSuccResult() {
		CommonMapResult<T,K> result = new CommonMapResult<T,K>();
		result.setSuccess(true);
		return result;
	}

	public static <T,K> CommonMapResult<T,K> getSuccResultWithData(Map<T,K> resultMap) {
		CommonMapResult<T,K> result = new CommonMapResult<T,K>();
		result.setSuccess(true);
		result.setResultMap(resultMap);
		return result;
	}

}
