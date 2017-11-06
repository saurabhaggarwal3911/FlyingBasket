package com.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class WorkspaceBaseEntity extends BaseEntity {
  private Integer workspaceId;

  @Column(name = "workspace_id")
  public Integer getWorkspaceId() {
    return workspaceId;
  }

  public void setWorkspaceId(Integer workspaceId) {
    this.workspaceId = workspaceId;
  }

}
