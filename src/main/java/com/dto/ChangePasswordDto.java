package com.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * @author rksharma
 *
 */

public class ChangePasswordDto {
  @NotBlank
  @Size(min = 4)
  private String oldPassword;
  @NotBlank
  @Size(min = 4)
  private String newPassword;
  private String userId;

  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

}
