package com.venta.domain;

import org.springframework.beans.factory.annotation.Value;

public class Login {
	
	@Value("${login.user}")
	String name;
	@Value("${login.password}")
	String password;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
		

}
