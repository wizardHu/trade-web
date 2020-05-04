package com.wizard.model;

import lombok.Data;

@Data
public class CommonResult{

	public int code;
	public boolean isSuccess;
	public String description;
	private Object result;

	private int page;

	private int totalCount;

	private int pagesize;


	public CommonResult() {

	}

	public CommonResult(String description) {
		this.description = description;
		this.isSuccess = true;
	}

	public static CommonResult getSuccResult() {
		CommonResult result = new CommonResult();
		result.setSuccess(true);
		return result;
	}

	public static CommonResult getSuccResultWithData(Object data) {
		CommonResult result = new CommonResult();
		result.setSuccess(true);
		result.setResult(data);
		return result;
	}
	

}
