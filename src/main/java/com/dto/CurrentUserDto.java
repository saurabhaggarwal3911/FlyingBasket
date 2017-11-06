package com.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.entity.RoleEntity;
import com.entity.UserEntity;

public class CurrentUserDto extends User {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private int roleId;
	private Integer workspaceId;
	private Integer clientId;
	private Double wallet;
	private String mobile;
	private String referralCode;
	private String membershipType;
	private String address;

	public CurrentUserDto(UserEntity userEntity) {
		super(userEntity.getUsername(), userEntity.getPassword() == null ? "" : userEntity.getPassword(),
				userEntity.isValid(), true, true, true, buildUserAuthority(userEntity.getRole()));
		this.id = userEntity.getId();
		this.name = userEntity.getName();
		this.roleId = userEntity.getRole().getId();
		this.workspaceId = userEntity.getWorkspace() == null ? null : userEntity.getWorkspace().getId();
		this.clientId = userEntity.getClient() != null ? userEntity.getClient().getId() : null;
		this.wallet = userEntity.getWallet() != null ? userEntity.getWallet().getAmount() : 0;
		this.mobile = userEntity.getMobile();
		this.membershipType = userEntity.getMembershipType();
		this.address = userEntity.getAddress();
		this.referralCode = userEntity.getReferenceCode();
	}

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

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public Integer getWorkspaceId() {
		return workspaceId;
	}

	public void setWorkspaceId(Integer workspaceId) {
		this.workspaceId = workspaceId;
	}

	private static List<GrantedAuthority> buildUserAuthority(RoleEntity role) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		// for (UserRole userRole : userRoles) {
		setAuths.add(new SimpleGrantedAuthority(role.getName()));
		// }

		List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(setAuths);

		return result;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Double getWallet() {
		return wallet;
	}

	public void setWallet(Double wallet) {
		this.wallet = wallet;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMembershipType() {
		return membershipType;
	}

	public void setMembershipType(String membershipType) {
		this.membershipType = membershipType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

	@Override
	public String toString() {
		return "CurrentUserDto [id=" + id + ", name=" + name + ", roleId=" + roleId + ", workspaceId=" + workspaceId
				+ ", clientId=" + clientId + ", wallet=" + wallet + ", mobile=" + mobile + ", membershipType="
				+ membershipType + ", address=" + address + "]";
	}

}
