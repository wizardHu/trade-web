package com.wizard.util;

import java.util.Arrays;

public class Constant {

	public static final Integer BUY_TYPE = 1;
	public static final Integer SELL_TYPE = 0;

	public static final Integer BUY_SELL_FROM = 1;
	public static final Integer STOP_LOSS_FROM = 2;

	public enum ReturnCodeEnum{

		SUCCESS(1,"成功"),
		FAIL(0,"失败");

		private Integer code;

		private String desc;

		ReturnCodeEnum(Integer code,String desc){
			this.code = code;
			this.desc = desc;
		}

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}

	public enum BuyRecordStatusEnum{
		NORMAL(0,"待交易"),
		STOP_LOSS(1,"止损"),
		BUY_QUEUE(2,"进入买队列"),
		SELL_QUEUE(3,"进入卖队列"),
		LIMIT_ORDER(4,"挂单");

		private Integer status;

		private String desc;

		BuyRecordStatusEnum(Integer status,String desc){
			this.status = status;
			this.desc = desc;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public static BuyRecordStatusEnum convert(Integer status){
			return status == null ? null :
					Arrays.stream(BuyRecordStatusEnum.values())
							.filter(l -> l.getStatus() == status)
							.findFirst()
							.orElse(null);
		}
	}
	
}
