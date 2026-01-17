package com.study.mySite.user;

import lombok.Getter;

@Getter
public enum UserRole {//enum 클래스
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER");
	
	UserRole(String value){
		this.value=value;
	}
	private String value;
}
