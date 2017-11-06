package com.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Workspace", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "client_id"}))
public class WorkspaceEntity extends BaseEntity {
  private int id;
  private String name;
  private boolean active;
  private List<UserEntity> adminList = new ArrayList<>();
  private List<UserEntity> userList = new ArrayList<>();
  private ClientEntity client;


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Column(nullable = false)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "workspace")
  public List<UserEntity> getAdminList() {
    return adminList;
  }

  public void setAdminList(List<UserEntity> adminList) {
    this.adminList = adminList;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "client_id", nullable = false)
  public ClientEntity getClient() {
    return client;
  }

  public void setClient(ClientEntity client) {
    this.client = client;
  }


  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "workspaceList")
  public List<UserEntity> getUserList() {
    return userList;
  }

  public void setUserList(List<UserEntity> userList) {
    this.userList = userList;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (this == null || obj == null) {
      return false;
    }
    if (this.getId() == ((WorkspaceEntity) obj).getId()) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return id;
  }
}
