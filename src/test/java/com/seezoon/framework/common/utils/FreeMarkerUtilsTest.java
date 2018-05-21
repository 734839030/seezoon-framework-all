package com.seezoon.framework.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class FreeMarkerUtilsTest {

	@Test
	public void t1() {
		Map<String,Object> map = new HashMap<>();
		map.put("a", "PRI");
		map.put("search", "=");

		Map<String,Object> map1 = new HashMap<>();
		map1.put("a", "PRI");
		map1.put("search", "1");
		List<Map<String,Object>> l = new ArrayList();
		map.put("list", l);
		l.add(map);
		l.add(map1);
		String renderTemplate = FreeMarkerUtils.renderTemplate("gen/test.ftl", map);
		System.out.println(renderTemplate);
	}
	

}
