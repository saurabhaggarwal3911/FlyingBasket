package com.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserDto implements Comparable<UserDto> {

	private Integer id;
	@NotBlank
	private String name;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String password;
//	@NotBlank
    private boolean valid;
    private int role;
    private String loginInfo;
    @NotBlank
    @Length(max=10, min=10)
    private String mobile;
    @NotBlank
    private String address;
    @NotBlank
    private String referralCode;;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

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

   

    public String getPassword() {
	return password;
    }
    
    public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPassword(String password) {
	this.password = password;
    }

    public boolean isValid() {
	return valid;
    }

    public void setValid(boolean valid) {
	this.valid = valid;
    }

    public int getRole() {
	return role;
    }

    public void setRole(int role) {
	this.role = role;
    }

    public String getLoginInfo() {
	return loginInfo;
    }

    public void setLoginInfo(String loginInfo) {
	this.loginInfo = loginInfo;
    }
    
    public String getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

	@Override
    public int compareTo(UserDto obj) {
	int x = String.CASE_INSENSITIVE_ORDER.compare(this.getName(), obj.getName());
	if (x == 0) {
	    x = this.getName().compareTo(obj.getName());
	}
	return x;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (this == null || obj == null) {
	    return false;
	}
	if (this.getId() == ((UserDto) obj).getId()) {
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public int hashCode() {
    	if(id != 0){
    		return id;
    	}
	return super.hashCode();
    }

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", valid=" + valid + ", role=" + role + ", loginInfo=" + loginInfo + ", mobile=" + mobile + ", address=" + address + "]";
	}

}
