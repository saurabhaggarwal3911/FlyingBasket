package com.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Client", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class ClientEntity extends BaseEntity {
  private int id;
  private String name;
  private boolean active;
  private List<UserEntity> adminList = new ArrayList<>();
  private List<WorkspaceEntity> workspaceList = new ArrayList<>();

  // private List<UserEntity> userList = new ArrayList<>();

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

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
  public List<UserEntity> getAdminList() {
    return adminList;
  }

  public void setAdminList(List<UserEntity> adminList) {
    this.adminList = adminList;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
  public List<WorkspaceEntity> getWorkspaceList() {
    return workspaceList;
  }

  public void setWorkspaceList(List<WorkspaceEntity> workspaceList) {
    this.workspaceList = workspaceList;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (this == null || obj == null) {
      return false;
    }
    if (this.getId() == ((ClientEntity) obj).getId()) {
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
