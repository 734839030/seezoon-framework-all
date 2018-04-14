package com.seezoon.framework.common.context.beans;

import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.annotation.JSONField;
import com.seezoon.framework.common.context.utils.NDCUtils;

/**
 * 通用返回参数模型
 * -1 和 90开头的错误码为系统使用，其他场景自定义错误码即可
 * @author hdf 2018年3月31日
 */
public class ResponeModel {

	/**
	 * 成功响应吗
	 */
	private final static String GLOBAL_DEFAULT_SUCCESS = "0";
	/**
	 * 默认逻辑错误响应吗,ResponeCodeException  默认错误也是这个，前端会对这个进行统一错误提示。
	 */
	private final static String GLOBAL_DEFAULT_ERROR = "-1";

	/**
	 * 响应码
	 */
	private String responeCode = GLOBAL_DEFAULT_SUCCESS;
	/**
	 * 响应信息
	 */
	private String responeMsg;
	/**
	 * 线程id
	 */
	private String requestId;
	/**
	 * 实际数据
	 */
	private Object data;
	/**
	 * messsage format 参数
	 */
	@JSONField(serialize = false)
	private Object[] params;

	public ResponeModel() {
		// 减小因map的开销
		requestId = NDCUtils.peek();
	}

	public String getResponeCode() {
		return responeCode;
	}

	public void setResponeCode(String responeCode) {
		this.responeCode = responeCode;
	}

	public String getResponeMsg() {
		return responeMsg;
	}

	public void setResponeMsg(String responeMsg) {
		this.responeMsg = responeMsg;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static ResponeModel ok() {
		return new ResponeModel();
	}

	public static ResponeModel ok(Object data) {
		ResponeModel responeModel = new ResponeModel();
		responeModel.setData(data);
		return responeModel;
	}

	public static ResponeModel error(String responeCode, String responeMsg) {
		ResponeModel responeModel = new ResponeModel();
		responeModel.setResponeCode(responeCode);
		responeModel.setResponeMsg(responeMsg);
		return responeModel;
	}

	public static ResponeModel error(String responeMsg) {
		ResponeModel responeModel = new ResponeModel();
		responeModel.setResponeCode(GLOBAL_DEFAULT_ERROR);
		responeModel.setResponeMsg(responeMsg);
		return responeModel;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	/**
	 * 将data设置成为map
	 * 
	 * @param key
	 * @param value
	 */
	public void addProperty(String key, String value) {
		if (null == data || (!(data instanceof Map))) {
			Map<String, Object> propertyMap = new HashMap<>();
			propertyMap.put(key, value);
			this.setData(propertyMap);
		} else {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>)data;
			map.put(key, value);
		}
	}
}
