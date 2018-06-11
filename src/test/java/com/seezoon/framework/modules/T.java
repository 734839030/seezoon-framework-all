package com.seezoon.framework.modules;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class T {

	protected Logger logger = LoggerFactory.getLogger(T.class);
	@Test
	public void t2() throws Exception  {
		File file = new File("/Users/hdf/Downloads/1.txt"); 
		PrintStream ps = new PrintStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(ps);
		BufferedWriter bw = new BufferedWriter(osw);
		
		while (true) {
			Thread.sleep(1000);
			bw.write("dsasssss");
			bw.flush();
		}
	}
	@Test
	public void t3() throws Exception  {
		Thread.sleep(2000);
		File file = new File("/Users/hdf/Downloads/1.txt"); 
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		while (true) {
			Thread.sleep(1000);
			System.out.println(br.readLine());
		}
	}
	@Test
	public void t4() {
		System.out.println(StringEscapeUtils.escapeHtml4("https://mmt4ossbucket1.oss-cn-hangzhou.aliyuncs.com"));
	}
	@Test
	public void t5() throws IOException {
		FileWriter fw = new FileWriter("/Users/hdf/Documents/temp/x1.txt");
		fw.write("sss");
		fw.close();
	}
	@Test
	public void t6() throws Exception {
		ZipOutputStream zos  = new ZipOutputStream(new FileOutputStream("/Users/hdf/Documents/temp/x1.zip"));
		zos.putNextEntry(new ZipEntry("/Users/hdf/Documents/temp/x1.txt"));
		IOUtils.write("xx", zos);
		zos.closeEntry();
		zos.close();
	}
	@Test
	public void t7() {
//		Field field = ReflectionUtils.findField(demoParam.getClass(), "id");
//		Class<?> declaringClass = field.getDeclaringClass();
//		System.out.println(declaringClass.getName());
//		Class clazz2 = demoParam.getClass();  
//	    Type type2 = clazz2.getGenericSuperclass();  
//	    ParameterizedType parameterizedType2 = (ParameterizedType) type2;  
//	    System.out.println(parameterizedType2);  
//	    System.out.println(parameterizedType2.getActualTypeArguments()[0].equals(Integer.class));  
	}
	@Test
	public void t8() {
		try {
			File file= null;
			file.exists();
			System.out.println(1/0);
		} catch (Exception e) {
			logger.error("dsadsa",e);
		}
	}
}
