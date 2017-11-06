package com.exception;

public class DataAlreadyExistException extends Exception {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  public DataAlreadyExistException() {
    // TODO Auto-generated constructor stub
    super();
  }

  public DataAlreadyExistException(String msg) {
    super(msg);
  }

  public DataAlreadyExistException(Throwable t) {
    super(t);
  }

  public DataAlreadyExistException(String msg, Throwable t) {
    super(msg, t);
  }
}
