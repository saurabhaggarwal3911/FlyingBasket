package com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "User_Product_Transacation_Detail")
public class UserProductTransacationDetailEntity extends BaseEntity{
	private Integer id;
	private Integer quanity;
	private Double price;
	private Double total;
	private Integer productId;
	private String productName;
	private Integer userId;
	private String status;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "quanity", nullable = false)
	public Integer getQuanity() {
		return quanity;
	}

	public void setQuanity(Integer quanity) {
		this.quanity = quanity;
	}
	@Column(name = "total", nullable = false, columnDefinition = "Decimal(10,2) default '00.00'")
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@Column(name = "price", nullable = false, columnDefinition = "Decimal(10,2) default '00.00'")
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "product_id", nullable = false)
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "user_id", nullable = false)
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	@Column(name = "product_name", nullable = false)
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	

	
}
