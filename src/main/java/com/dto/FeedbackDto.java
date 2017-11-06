package com.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;


public class FeedbackDto {
	@NotBlank
	private String name;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String toc;
	@NotBlank
	private String message;
	private String accessType;
	private String versionNum;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToc() {
		return toc;
	}
	public void setToc(String toc) {
		this.toc = toc;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getAccessType() {
		return accessType;
	}
	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}
	public String getVersionNum() {
		return versionNum;
	}
	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}
	
}
