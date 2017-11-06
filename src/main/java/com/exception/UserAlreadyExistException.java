package com.exception;

public class UserAlreadyExistException extends Throwable {
  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  public UserAlreadyExistException() {
    super();
  }

  public UserAlreadyExistException(Throwable throwable) {
    super(throwable);
  }

  public UserAlreadyExistException(String message, Throwable throwable) {
    super(message, throwable);
  }

  public UserAlreadyExistException(String message) {
    super(message);
  }
}
