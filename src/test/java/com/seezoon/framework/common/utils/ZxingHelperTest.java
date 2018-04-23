package com.seezoon.framework.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.junit.Test;

public class ZxingHelperTest {
	String path = "/Users/hdf/Downloads/1.jpg";
	@Test
	public void test() throws Exception {
		ZxingHelper helper = new ZxingHelper();
		try {
			File file = new File(path);
			helper.ecnode("huangdf 黄", new FileInputStream("/Users/hdf/Downloads/avatar.png"), new FileOutputStream(path), true);
			System.out.println(helper.decode(new FileInputStream(path)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
