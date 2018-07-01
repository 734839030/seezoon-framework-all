package com.seezoon.framework.front.wechat.service;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.seezoon.framework.common.service.BaseService;
import com.seezoon.framework.front.wechat.dto.PayResult;

@Component
public class DemoPayHandler extends BaseService implements PayResultHandler{

	@Override
	public boolean handle(PayResult result) {
		logger.info("handle PayResult:" + JSON.toJSONString(result));
		return true;
	}

}
