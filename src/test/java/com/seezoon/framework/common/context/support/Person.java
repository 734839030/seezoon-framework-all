package com.seezoon.framework.common.context.support;

class Person implements Cloneable{

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static void main(String[] args) throws CloneNotSupportedException {
		Person p1 = new Person();
		p1.setName("11");
		Person p2 = (Person) p1.clone();
		p2.setName("22");
		System.out.println(p1.getName());
		System.out.println(p2.getName());

	}
}
