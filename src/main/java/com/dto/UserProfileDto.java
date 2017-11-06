package com.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserProfileDto {
    private String email;
    private String name;
    private String address;
    private String userId;
    private String role;
    private boolean valid;
    private String mobile;
    private String referralCode;
    private String regDate;
    private String membershipType;
    private String appUrl;
//    private int cartLength;
    private List<CheckoutDto> order;
    private List<DebitCreditCardDto> card;
    List<String> workspaceList;
    private double wallet;
    
    

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

    public String getRole() {
	return role;
    }

    public void setRole(String role) {
	this.role = role;
    }

    public boolean isValid() {
	return valid;
    }

    public void setValid(boolean valid) {
	this.valid = valid;
    }

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getMembershipType() {
		return membershipType;
	}

	public void setMembershipType(String membershipType) {
		this.membershipType = membershipType;
	}

//	public int getCartLength() {
//		return cartLength;
//	}
//
//	public void setCartLength(int cartLength) {
//		this.cartLength = cartLength;
//	}

	public List<CheckoutDto> getOrder() {
		return order;
	}

	public void setOrder(List<CheckoutDto> order) {
		this.order = order;
	}

	public List<DebitCreditCardDto> getCard() {
		return card;
	}

	public void setCard(List<DebitCreditCardDto> card) {
		this.card = card;
	}

	public double getWallet() {
		return wallet;
	}

	public void setWallet(double wallet) {
		this.wallet = wallet;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<String> getWorkspaceList() {
		return workspaceList;
	}

	public void setWorkspaceList(List<String> workspaceList) {
		this.workspaceList = workspaceList;
	}

	public String getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}
}
