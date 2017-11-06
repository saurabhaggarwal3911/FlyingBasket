package com.exception;

public class UserEmailAlreadyExistException extends Throwable {
  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  public UserEmailAlreadyExistException() {
    super();
  }

  public UserEmailAlreadyExistException(Throwable throwable) {
    super(throwable);
  }

  public UserEmailAlreadyExistException(String message, Throwable throwable) {
    super(message, throwable);
  }

  public UserEmailAlreadyExistException(String message) {
    super(message);
  }
}
