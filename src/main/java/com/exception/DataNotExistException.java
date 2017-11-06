package com.exception;

public class DataNotExistException extends Exception {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  public DataNotExistException() {
    // TODO Auto-generated constructor stub
    super();
  }

  public DataNotExistException(String msg) {
    super(msg);
  }

  public DataNotExistException(Throwable t) {
    super(t);
  }

  public DataNotExistException(String msg, Throwable t) {
    super(msg, t);
  }
}
