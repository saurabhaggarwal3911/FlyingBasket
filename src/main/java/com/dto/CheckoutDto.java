package com.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
public class CheckoutDto {
	
	private int id;
//	@NotBlank
	private String address;
//	@NotBlank
//	@Size(min = 10, max = 10)
	private String mobileNum;
	private String name;
	private String emailId;
	@NotNull
	private Double amount;
	private Double usedWallet;
	@NotNull
	private Double shipping;
	private String date;
	private String payMode;
	private String status;

	
	@Valid
	@NotEmpty 
	private List<PurchasedItemDto> data = new ArrayList<>();


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getMobileNum() {
		return mobileNum;
	}


	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmailId() {
		return emailId;
	}


	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}


	public Double getAmount() {
		return amount;
	}


	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getShipping() {
		return shipping;
	}


	public void setShipping(Double shipping) {
		this.shipping = shipping;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getPayMode() {
		return payMode;
	}


	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}


	public List<PurchasedItemDto> getData() {
		return data;
	}


	public void setData(List<PurchasedItemDto> data) {
		this.data = data;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public Double getUsedWallet() {
		return usedWallet;
	}


	public void setUsedWallet(Double usedWallet) {
		this.usedWallet = usedWallet;
	}


	@Override
	public String toString() {
		return "CheckoutDto [id=" + id + ", address=" + address + ", mobileNum=" + mobileNum + ", name=" + name
				+ ", emailId=" + emailId + ", amount=" + amount + ", usedWallet=" + usedWallet + ", shipping="
				+ shipping + ", date=" + date + ", payMode=" + payMode + ", status=" + status + ", data=" + data + "]";
	}
}
