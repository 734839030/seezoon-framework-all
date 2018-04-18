package com.seezoon.framework.common.context.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;
import com.seezoon.framework.common.context.utils.NDCUtils;

@Component
public class SystemStopEventHandler implements ApplicationListener<ContextStoppedEvent> {

	@Override
	public void onApplicationEvent(ContextStoppedEvent event) {
		NDCUtils.push();
		printKeyLoadMessage();
	}

	/**
	 * 获取Key加载信息
	 */
	public static void printKeyLoadMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n======================================================================\r\n");
		sb.append("\r\n    欢迎再次使用 " + "上征快速开发平台" + "  - Powered By huangdf\r\n");
		sb.append("\r\n======================================================================\r\n");
		System.out.println(sb.toString());
	}
}
