package com.exception;

public class UserMobileAlreadyExistException extends Throwable {
  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  public UserMobileAlreadyExistException() {
    super();
  }

  public UserMobileAlreadyExistException(Throwable throwable) {
    super(throwable);
  }

  public UserMobileAlreadyExistException(String message, Throwable throwable) {
    super(message, throwable);
  }

  public UserMobileAlreadyExistException(String message) {
    super(message);
  }
}
