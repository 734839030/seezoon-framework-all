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
		map.put("search", null);
		map.put("map", map);

		List<Map<String,Object>> l = new ArrayList();
		map.put("list", l);
		l.add(map);
		l.add(map);
		String renderTemplate = FreeMarkerUtils.renderTemplate("gen/test.ftl", map);
		System.out.println(renderTemplate);
	}
	

}
