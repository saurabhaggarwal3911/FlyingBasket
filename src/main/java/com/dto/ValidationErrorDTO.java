package com.dto;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorDTO {
  private List<FieldErrorDTO> fieldErrors = new ArrayList<>();
  private boolean success;

  public ValidationErrorDTO() {

  }

  public void addFieldError(String path, String message) {
    FieldErrorDTO error = new FieldErrorDTO(path, message);
    fieldErrors.add(error);
  }

  public List<FieldErrorDTO> getFieldErrors() {
    return fieldErrors;
  }

  public void setFieldErrors(List<FieldErrorDTO> fieldErrors) {
    this.fieldErrors = fieldErrors;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }



}
