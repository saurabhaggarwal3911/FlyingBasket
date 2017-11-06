package com.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "User_Transaction_History")
public class UserTransactionHistoryEntity extends WorkspaceBaseEntity {

	/**
	 * 
	 */
	private int id;
	private Double amount;
	private Double usedWallet;
	private Double cashbackAllowed;
	private Double shippingCost;
	private String payMode;
	private String billName;
	private String billMobile;
	private String billAddress;
	private String billEmail;
//	public Set<ProductEntity> productList;
	public List<UserProductTransacationDetailEntity> userProductTransacationDetailList;
	private Integer userId;
//	private UserEntity user;
	private String status;
	
	@Column(name = "status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "amount", nullable = false, columnDefinition="Decimal(10,2) default '00.00'")
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@Column(name = "payment_mode", nullable = false)
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	@Column(name = "bill_name", nullable = false)
	public String getBillName() {
		return billName;
	}
	public void setBillName(String billName) {
		this.billName = billName;
	}
	@Column(name = "bill_mobile", nullable = false)
	public String getBillMobile() {
		return billMobile;
	}
	public void setBillMobile(String billMobile) {
		this.billMobile = billMobile;
	}
	@Column(name = "bill_address", nullable = false)
	public String getBillAddress() {
		return billAddress;
	}
	public void setBillAddress(String billAddress) {
		this.billAddress = billAddress;
	}
	@Column(name = "bill_email", nullable = false)
	public String getBillEmail() {
		return billEmail;
	}
	public void setBillEmail(String billEmail) {
		this.billEmail = billEmail;
	}
	//	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//	@JoinTable(
//			name = "Product__Transaction_History",
//			joinColumns = @JoinColumn(name="bill_id"),
//			inverseJoinColumns = @JoinColumn(name="product_id")
//	)
//	public Set<ProductEntity> getProductList() {
//		return productList;
//	}
//	
//	public void setProductList(Set<ProductEntity> productSet) {
//		this.productList = productSet;
//	}
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinTable(
			name = "User___Product__Transaction_Detail__History",
			joinColumns = @JoinColumn(name="history_id"),
			inverseJoinColumns = @JoinColumn(name="transation_detail_id")
	)
	public List<UserProductTransacationDetailEntity> getUserProductTransacationDetailList() {
		return userProductTransacationDetailList;
	}
	public void setUserProductTransacationDetailList(
			List<UserProductTransacationDetailEntity> userProductTransacationDetailList) {
		this.userProductTransacationDetailList = userProductTransacationDetailList;
	}
	@Column(name = "user_id", nullable = false)
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
    @Column(name = "price", nullable = false, columnDefinition="Decimal(10,2) default '00.00'")
	public Double getUsedWallet() {
		return usedWallet;
	}
	public void setUsedWallet(Double usedWallet) {
		this.usedWallet = usedWallet;
	}
    @Column(name = "allowed_cashback", nullable = false, columnDefinition="Decimal(10,2) default '00.00'")
	public Double getCashbackAllowed() {
		return cashbackAllowed;
	}
	public void setCashbackAllowed(Double cashbackAllowed) {
		this.cashbackAllowed = cashbackAllowed;
	}
    @Column(name = "shipping_cost", nullable = false, columnDefinition="Decimal(10,2) default '00.00'")
	public Double getShippingCost() {
		return shippingCost;
	}
	public void setShippingCost(Double shippingCost) {
		this.shippingCost = shippingCost;
	}
//	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//	@JoinTable(
//			name="User__Transaction_History",
//			joinColumns = @JoinColumn(name = "bill_id"),
//			inverseJoinColumns = @JoinColumn(name="user_id")
//	)
//	public UserEntity getUser() {
//		return user;
//	}
//	public void setUser(UserEntity user) {
//		this.user = user;
//	}
	
	
	
	
	
}
