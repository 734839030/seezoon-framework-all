package com.seezoon.framework.common.context.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.seezoon.framework.common.context.utils.NDCUtils;

@Component
public class SystemStartEventHandler implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		NDCUtils.push();
		if (event.getApplicationContext().getParent() == null) {
			printKeyLoadMessage();
		}
	}
	/**
	 * 获取Key加载信息
	 */
	public static void printKeyLoadMessage(){
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n======================================================================\r\n");
		sb.append("\r\n    欢迎使用 "+ "上征快速开发平台" +"  - Powered By huangdf\r\n");
		sb.append("\r\n======================================================================\r\n");
		System.out.println(sb.toString());
	}
}

