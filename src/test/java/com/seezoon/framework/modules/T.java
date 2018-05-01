package com.seezoon.framework.modules;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

import org.junit.Test;

public class T {

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
		char c = '1';
		
	}
}
