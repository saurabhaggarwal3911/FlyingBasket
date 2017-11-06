package com.dto;


public class DebitCreditCardDto {
    private int id;
    private String name;
    private String Type;
    private String expiry;
    private String number;
    private String cvv;


	/*@Override
    public int compareTo(OrderTransactionDto obj) {
	int x = String.CASE_INSENSITIVE_ORDER.compare(this.id(), obj.getDate());
	if (x == 0) {
	    x = this.getDate().compareTo(obj.getDate());
	}
	return x;
    }*/

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	@Override
    public boolean equals(Object obj) {
	// TODO Auto-generated method stub
	if (this == obj) {
	    return true;
	}
	if (this == null || obj == null) {
	    return false;
	}
	if (this.getId() == ((DebitCreditCardDto) obj).getId()) {
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
		return "DebitCreditCardDto [id=" + id + ", name=" + name + ", Type=" + Type + ", expiry=" + expiry + ", number=" + number + ", cvv=" + cvv + "]";
	}

}
