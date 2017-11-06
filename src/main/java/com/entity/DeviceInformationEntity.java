package com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Device_Info")
public class DeviceInformationEntity {
  private String deviceId;
  private String regId;
  private UserEntity user;
  private String versionNum;

  @Id
  @Column(name = "deviceId", nullable = false, length = 50)
  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", nullable = false)
  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  @Column(name = "regId")
  public String getRegId() {
    return regId;
  }

  public void setRegId(String regId) {
    this.regId = regId;
  }

  @Column(name = "versionNum")
  public String getVersionNum() {
    return versionNum;
  }

  public void setVersionNum(String versionNum) {
    this.versionNum = versionNum;
  }

}
