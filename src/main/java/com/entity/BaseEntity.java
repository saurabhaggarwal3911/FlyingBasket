package com.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class BaseEntity {
  private Integer createdBy;
  private Integer modifiedBy;
  private Date createdOn;
  private Date modifiedOn;



  @Column(updatable = false, name = "createdBy")
  public Integer getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(Integer createdBy) {
    this.createdBy = createdBy;
  }

  @Column(name = "modifiedBy")
  public Integer getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(Integer modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  @Column(updatable = false, name = "createdOn")
  @Temporal(TemporalType.TIMESTAMP)
  public Date getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Date createdOn) {
    this.createdOn = createdOn;
  }

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "modifiedOn")
  public Date getModifiedOn() {
    return modifiedOn;
  }

  public void setModifiedOn(Date modifiedOn) {
    this.modifiedOn = modifiedOn;
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    // TODO Auto-generated method stub
    BaseEntity clone = (BaseEntity) super.clone();
    clone.setCreatedBy(null);
    clone.setCreatedOn(null);
    clone.setModifiedBy(null);
    clone.setModifiedOn(null);
    return clone;
  }
}
