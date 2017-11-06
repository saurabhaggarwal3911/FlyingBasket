package com.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class CartCheckoutDto {
	
	@NotBlank
	@Size(min=1)
    private String address;
	@NotBlank
	@Size(min=10, max=10)
	private String mobile;
	private String name;
    private String emailId;
    private Double amount;
    private String date;
    private String payMode;
    @Valid
    @NotEmpty
    private List<PurchasedItemDto> items = new ArrayList<>();

	/*@Override
    public int compareTo(OrderTransactionDto obj) {
	int x = String.CASE_INSENSITIVE_ORDER.compare(this.id(), obj.getDate());
	if (x == 0) {
	    x = this.getDate().compareTo(obj.getDate());
	}
	return x;
    }*/

    public String getAddress() {
		return address;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public List<PurchasedItemDto> getItems() {
		return items;
	}

	public void setItems(List<PurchasedItemDto> items) {
		this.items = items;
	}
	
	@Override
	public String toString() {
		return "CartCheckoutDto [address=" + address + ", mobile=" + mobile + ", name=" + name + ", emailId=" + emailId
				+ ", amount=" + amount + ", date=" + date + ", payMode=" + payMode + ", items=" + items + "]";
	}

}
