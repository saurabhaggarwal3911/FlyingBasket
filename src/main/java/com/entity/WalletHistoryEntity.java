package com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Wallet_History")
public class WalletHistoryEntity extends WorkspaceBaseEntity {

	private int id;
	private Double amount;
	private boolean valid;
	private boolean credit;
	private String remarks;
	private WalletEntity wallet;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
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
	@Column(name="is_valid", columnDefinition="bit default 0", nullable=false)
	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	@Column(nullable = false, length = 255, name = "remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Column(name="is_credit", columnDefinition="bit default 0", nullable=false)
	public boolean isCredit() {
		return credit;
	}

	public void setCredit(boolean credit) {
		this.credit = credit;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="wallet_id")
	public WalletEntity getWallet() {
		return wallet;
	}

	public void setWallet(WalletEntity wallet) {
		this.wallet = wallet;
	}
	
}
