package com.seezoon.framework.modules.demo.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.seezoon.framework.common.entity.BaseEntity;

public class DemoTest extends BaseEntity<String>{

	@NotNull
	@Length(min=1,max=100)
    private String name;

    private Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
    

}