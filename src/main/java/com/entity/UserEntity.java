package com.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Users", uniqueConstraints = @UniqueConstraint(columnNames = {"mobile" }))
@NamedEntityGraph(name = "userWithRole", attributeNodes = @NamedAttributeNode("role"))
public class UserEntity extends BaseEntity implements Comparable<UserEntity> {
    private Integer id;
    private String name;
    private String username;
    private String password;
    private boolean valid;
    private RoleEntity role;
    private String mobile;
    private String address;
    private String loginInfo;
    private boolean deleteUser;
    private String referenceCode;
    private Integer referenceBy;
    private String membershipType;
    private WorkspaceEntity workspace;
    private ClientEntity client;
    private WalletEntity wallet;
    private List<WorkspaceEntity> workspaceList = new ArrayList<>();
    private List<DeviceInformationEntity> deviceInfo = new ArrayList<>();
	
	private Set<UserTransactionHistoryEntity> transactionHistory;

    

    public UserEntity() {
	super();
    }

    public UserEntity(Integer id, String name) {
	super();
	this.id = id;
	this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    @Column(nullable = false, name = "name")
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }


    @Column(name = "password")
    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    @Column(name = "isValid")
    public boolean isValid() {
	return valid;
    }

    public void setValid(boolean valid) {
	this.valid = valid;
    }

    @Column(name = "is_delete", columnDefinition = "bit default 0", nullable = false)
    public boolean isDeleteUser() {
	return deleteUser;
    }

    public void setDeleteUser(boolean deleteUser) {
	this.deleteUser = deleteUser;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    public RoleEntity getRole() {
	return role;
    }

    public void setRole(RoleEntity role) {
	this.role = role;
    }


    @Column(name = "email_id", nullable = false, length = 255)
    public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id"/* , nullable= false */)
    public ClientEntity getClient() {
      return client;
    }

    public void setClient(ClientEntity client) {
      this.client = client;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id"/* , nullable= false */)
    public WorkspaceEntity getWorkspace() {
      return workspace;
    }

    public void setWorkspace(WorkspaceEntity workspace) {
      this.workspace = workspace;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Workspace__User", joinColumns = {@JoinColumn(name = "user_id", nullable = false)}, inverseJoinColumns = {@JoinColumn(name = "workspace_id", nullable = false)})
    public List<WorkspaceEntity> getWorkspaceList() {
      return workspaceList;
    }

    public void setWorkspaceList(List<WorkspaceEntity> workspaceList) {
      this.workspaceList = workspaceList;
    }
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    public List<DeviceInformationEntity> getDeviceInfo() {
      return deviceInfo;
    }

    public void setDeviceInfo(List<DeviceInformationEntity> deviceInfo) {
      this.deviceInfo = deviceInfo;
    }
    

    @Override
    public boolean equals(Object obj) {

	if (this == obj) {
	    return true;
	}
	if (this == null || obj == null) {
	    return false;
	}
	if (this.getId() == ((UserEntity) obj).getId()) {
	    return true;
	} else {
	    return false;
	}
    }
	
    @Column(name = "mobile", nullable = false, length = 10)
    public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
   
	@Column(name = "membership_type", length = 15)
	public String getMembershipType() {
		return membershipType;
	}

	public void setMembershipType(String membershipType) {
		this.membershipType = membershipType;
	}

	@OneToOne(fetch = FetchType.LAZY,  cascade=CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="wallet_id",insertable = true)
    public WalletEntity getWallet() {
		return wallet;
	}

	public void setWallet(WalletEntity wallet) {
		this.wallet = wallet;
	}

	@Override
    public int hashCode() {

	return id;
    }

    @Override
    public int compareTo(UserEntity obj) {

	if (this == obj || obj == null || this == null) {
	    return 0;
	}
	return 1;
    }

    @Column(name = "login_info")
    public String getLoginInfo() {
	return loginInfo;
    }

    public void setLoginInfo(String loginInfo) {
	this.loginInfo = loginInfo;
    }
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY, orphanRemoval=true)
	@JoinTable(
			name="User__Transaction_History",
			joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="bill_id")
	)

    public Set<UserTransactionHistoryEntity> getTransactionHistory() {
		return transactionHistory;
	}

	public void setTransactionHistory(Set<UserTransactionHistoryEntity> transactionHistory) {
		this.transactionHistory = transactionHistory;
	}
	@Column(name = "reference_code", length = 15)
	public String getReferenceCode() {
		return referenceCode;
	}

	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}
	@Column(name = "reference_by")
	public Integer getReferenceBy() {
		return referenceBy;
	}

	public void setReferenceBy(Integer referenceBy) {
		this.referenceBy = referenceBy;
	}
	@Column(name = "address", length=255)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", name=" + name + ", username=" + username + ", password=" + password
				+ ", valid=" + valid + ", role=" + role + ", mobile=" + mobile + ", address=" + address + ", loginInfo="
				+ loginInfo + ", deleteUser=" + deleteUser + ", referenceCode=" + referenceCode + ", referenceBy="
				+ referenceBy + ", membershipType=" + membershipType + ", workspace=" + workspace + ", client=" + client
				+ ", wallet=" + wallet + ", workspaceList=" + workspaceList + ", deviceInfo=" + deviceInfo
				+ ", transactionHistory=" + transactionHistory + "]";
	}

}
