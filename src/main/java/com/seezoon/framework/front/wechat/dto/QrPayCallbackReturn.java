package com.seezoon.framework.front.wechat.dto;

/**
 * 扫码支付 模式二回调后返回微信服务器
 * @author hdf
 * 2018年7月1日
 */
public class QrPayCallbackReturn extends BaseWechatPayReturnDto{
	private String appid;
	private String mch_id;
	private String nonce_str;
	private String prepay_id;
	private String sign;
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getPrepay_id() {
		return prepay_id;
	}
	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
