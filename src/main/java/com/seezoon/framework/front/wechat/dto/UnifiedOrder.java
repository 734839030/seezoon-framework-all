package com.seezoon.framework.front.wechat.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 统一下单参数
 * @author hdf
 * 2018年6月29日
 */
@JacksonXmlRootElement(localName="xml")
public class UnifiedOrder{

	/**
	 * 公众账号ID 必填                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
	 */
	private String appid;
	/**
	 * 商户号 必填
	 */
	private String mch_id;
	/**
	 * 设备号 非必填
	 */
	private String device_info = "WEB";
	/**
	 * 随机字符串 必填
	 */
	private String nonce_str;
	/**
	 * 签名  必填
	 */
	private String sign;
	/**
	 * 签名类型 非必填 默认为MD5
	 */
	private String sign_type = "MD5";
	/**
	 * 商品描述  必填
	 */
	private String body;
	/**
	 * 附加数据 非必填  在查询API和支付通知中原样返回,我们用来识别支付回调场景所有必填
	 */
	private String attach;
	/**
	 * 商户订单号  必填
	 */
	private String out_trade_no;
	/**
	 * 标价币种 非必填 默认 CNY
	 */
	private String fee_type ="CNY";
	/**
	 * 标价金额 分 必填
	 */
	private Integer total_fee;
	/**
	 * 终端IP
	 */
	private String spbill_create_ip;
	/**
	 * 交易起始时间 非必填 20091225091010
	 */
	private String time_start;
	/**
	 * 通知地址 必填
	 */
	private String notify_url;
	/**
	 * 交易类型
	 * JSAPI 公众号支付
		NATIVE 扫码支付
		APP APP支付
	 */
	private String trade_type = "JSAPI";
	/**
	 * 商品ID 非必填 
	 * trade_type=NATIVE时（即扫码支付），此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
	 */
	private String product_id;
	/**
	 * 用户标识 非必填 
	 * trade_type=JSAPI时（即公众号支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识
	 */
	private String openid;
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
	public String getDevice_info() {
		return device_info;
	}
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getFee_type() {
		return fee_type;
	}
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}
	public Integer getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}
	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}
	public String getTime_start() {
		return time_start;
	}
	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
}
