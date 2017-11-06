package com.exception;

public class InsertUserException extends Exception {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  public InsertUserException() {
    // TODO Auto-generated constructor stub
    super();
  }

  public InsertUserException(String msg) {
    super(msg);
  }

  public InsertUserException(Throwable t) {
    super(t);
  }

  public InsertUserException(String msg, Throwable t) {
    super(msg, t);
  }
}
