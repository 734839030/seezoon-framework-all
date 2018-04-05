package com.seezoon.framework.common.context.support;

import org.junit.Test;

import com.seezoon.framework.common.context.beans.ResponeModel;

public class TestExtends {

	@Test
	public void test() {
		Student s = new Student();
		s.setCls("1");
		s.setName("2");
		Person p = (Person) s;
		p.setName("3");
		Student s1 = (Student)p;
		System.out.println(s.getName());
	}
	
	@Test
	public void t2() {
		System.out.println(null instanceof ResponeModel);
	}

}
