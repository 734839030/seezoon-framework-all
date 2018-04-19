package com.seezoon.framework.common.context.support;

import org.junit.Test;

import com.seezoon.framework.common.context.utils.PropertyUtil;

public class PropertyUtilTest {

	@Test
	public void t1() {
		System.out.println(PropertyUtil.getBoolean("cors.switch1"));
		System.out.println(PropertyUtil.getString("cors.switch1"));
	}
}
