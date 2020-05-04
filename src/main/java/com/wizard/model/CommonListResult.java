package com.wizard.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class CommonListResult<T> extends CommonResult{

	private List<T> resultList;

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

	public static <T> CommonListResult<T> getSuccListResult() {
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
