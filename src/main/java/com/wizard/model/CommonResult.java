package com.wizard.model;

public class CommonResult{

	public int code;
	public boolean isSuccess;
	public String description;
	private Object result;


	public CommonResult() {

	}

	public CommonResult(String description) {
		this.description = description;
		this.isSuccess = true;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean success) {
		isSuccess = success;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
