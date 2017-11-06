package com.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class ForgotPasswordDto {
  @NotBlank
  @Email
  private String email;


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }



}
