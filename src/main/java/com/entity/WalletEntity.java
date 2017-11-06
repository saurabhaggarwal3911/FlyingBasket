package com.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Wallet")
public class WalletEntity extends WorkspaceBaseEntity {

	private Integer walletId;
	private Double amount;
	private boolean valid;
	private String remarks;
	private List<WalletHistoryEntity> WalletHistoryList = new ArrayList<>();
	private UserEntity userEntity;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "wallet_id")
	public Integer getWalletId() {
		return walletId;
	}

	public void setWalletId(Integer walletId) {
		this.walletId = walletId;
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
	@OneToMany(mappedBy="wallet", cascade=CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval=true)
	public List<WalletHistoryEntity> getWalletHistoryList() {
		return WalletHistoryList;
	}

	public void setWalletHistoryList(List<WalletHistoryEntity> walletHistoryList) {
		WalletHistoryList = walletHistoryList;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy="wallet", cascade=CascadeType.ALL)
	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
}
