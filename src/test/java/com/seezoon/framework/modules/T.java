package com.seezoon.framework.modules;

import org.junit.Test;

public class T {

	@Test
	public void t1() {
		String path = "/sdas/dsada/1111.jpg";
		System.out.println(path.substring(path.lastIndexOf("/") + 1 , path.lastIndexOf(".")));
	}
}
