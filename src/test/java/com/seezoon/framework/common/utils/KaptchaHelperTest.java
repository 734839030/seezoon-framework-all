package com.seezoon.framework.common.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.junit.Test;

public class KaptchaHelperTest {

	@Test
	public void test() throws FileNotFoundException {
		KaptchaHelper helper = new KaptchaHelper();
		String text = helper.createImage(new FileOutputStream("/Users/hdf/Documents/temp/4.jpg"));
	}

}
