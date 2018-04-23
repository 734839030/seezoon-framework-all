package com.seezoon.framework.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import com.seezoon.framework.modules.system.entity.SysFile;

public class ReflectionUtilsTest {

	@Test
	public void t1() {
		SysFile file = new SysFile();
		Method findMethod = ReflectionUtils.findMethod(SysFile.class, "getId");
		System.out.println(findMethod.getName());
		Field findField = ReflectionUtils.findField(SysFile.class, "1id");
		System.out.println(findField.getName());
	}
}
