package com.seezoon.framework.front.session;

import java.io.Serializable;

public class FrontUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	private String name;
	
	public FrontUser() {
		super();
	}
	public FrontUser(String userId, String name) {
		super();
		this.userId = userId;
		this.name = name;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
