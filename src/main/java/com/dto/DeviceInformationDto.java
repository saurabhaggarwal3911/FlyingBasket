package com.dto;


public class DeviceInformationDto {
  private String deviceId;
  private String regId;
  private int userId;
  private String versionNum;

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getRegId() {
    return regId;
  }

  public void setRegId(String regId) {
    this.regId = regId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getVersionNum() {
    return versionNum;
  }

  public void setVersionNum(String versionNum) {
    this.versionNum = versionNum;
  }
}
