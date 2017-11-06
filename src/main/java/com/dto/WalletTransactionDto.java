package com.dto;

import java.util.List;


public class WalletTransactionDto  {
    private int id;
    private UserDto user;
    
    private double amount;
    private String date;
    private List<DebitCreditCardDto> cardDetails;
    private String status;
    private boolean isCredited;
    private String remarks;

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isCredited() {
		return isCredited;
	}

	public void setCredited(boolean isCredited) {
		this.isCredited = isCredited;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public List<DebitCreditCardDto> getCardDetails() {
		return cardDetails;
	}

	public void setCardDetails(List<DebitCreditCardDto> cardDetails) {
		this.cardDetails = cardDetails;
	}

	/*
	@Override
    public int compareTo(WalletTransactionDto obj) {
	int x = String.CASE_INSENSITIVE_ORDER.compare(this.getTitle(), obj.getTitle());
	if (x == 0) {
	    x = this.getTitle().compareTo(obj.getTitle());
	}
	return x;
    }
*/
    @Override
    public boolean equals(Object obj) {
	// TODO Auto-generated method stub
	if (this == obj) {
	    return true;
	}
	if (this == null || obj == null) {
	    return false;
	}
	if (this.getId() == ((WalletTransactionDto) obj).getId()) {
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public int hashCode() {
	// TODO Auto-generated method stub
	return id;
    }

    @Override
	public String toString() {
		return "WalletTransactionDto [id=" + id + ", user=" + user + ", amount=" + amount + ", date=" + date + ", status=" + status + ", isCredited=" + isCredited + ", remarks=" + remarks + "]";
	}

}
