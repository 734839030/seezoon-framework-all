package com.seezoon.framework.front.wechat.service;

import com.seezoon.framework.front.wechat.dto.PayResult;

public interface PayResultHandler {

	public boolean handle(PayResult result);
}
