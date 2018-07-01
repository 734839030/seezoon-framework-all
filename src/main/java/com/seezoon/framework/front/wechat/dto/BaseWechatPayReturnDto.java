package com.seezoon.framework.front.wechat.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 微信支付相关接口通用返回
 * @author hdf
 * 2018年6月29日
 */
@JacksonXmlRootElement(localName="xml")
public class BaseWechatPayReturnDto {

	protected String return_code;
	private String return_msg;
	private String err_code;
	private String err_code_des;
	private String result_code;
	@JsonIgnore
	public boolean isSuccess() {
		return "SUCCESS".equals(result_code) && "SUCCESS".equals(return_code);
	}
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	
	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public String getErr_code_des() {
		return err_code_des;
	}
	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	
}
