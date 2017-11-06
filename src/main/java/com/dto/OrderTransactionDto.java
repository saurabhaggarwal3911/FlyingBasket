package com.dto;

import java.util.List;

public class OrderTransactionDto {
    private int id;
    private UserDto user;
    private double amount;
    private String date;
    private String payMode;
    private String card;
    private List<PurchasedItemDto> items;

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

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public List<PurchasedItemDto> getItems() {
		return items;
	}

	public void setItems(List<PurchasedItemDto> items) {
		this.items = items;
	}

	/*@Override
    public int compareTo(OrderTransactionDto obj) {
	int x = String.CASE_INSENSITIVE_ORDER.compare(this.id(), obj.getDate());
	if (x == 0) {
	    x = this.getDate().compareTo(obj.getDate());
	}
	return x;
    }*/

    @Override
    public boolean equals(Object obj) {
	// TODO Auto-generated method stub
	if (this == obj) {
	    return true;
	}
	if (this == null || obj == null) {
	    return false;
	}
	if (this.getId() == ((OrderTransactionDto) obj).getId()) {
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
		return "OrderTransactionDto [id=" + id + ", user=" + user + ", amount=" + amount + ", date=" + date + ", payMode=" + payMode + ", card=" + card + ", items=" + items + "]";
	}

}
