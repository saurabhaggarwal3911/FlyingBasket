package com.exception;

public class UserCanNotRemoveFromPoolException extends Exception {
	private static final long serialVersionUID = 1L;

	private final String name;
	private final String type;

	public UserCanNotRemoveFromPoolException(String name, String type) {
		// TODO Auto-generated constructor stub
		super();
		this.name = name;
		this.type = type;
	}

	public UserCanNotRemoveFromPoolException(Throwable throwable, String name, String type) {
		// TODO Auto-generated constructor stub
		super(throwable);
		this.name = name;
		this.type = type;
	}

	public UserCanNotRemoveFromPoolException(String message, Throwable throwable, String name, String type) {
		// TODO Auto-generated constructor stub
		super(message, throwable);
		this.name = name;
		this.type = type;
	}

	public UserCanNotRemoveFromPoolException(String message, String name, String type) {
		// TODO Auto-generated constructor stub
		super(message);
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

//	public void setName(String name) {
//		this.name = name;
//	}

	public String getType() {
		return type;
	}

//	public void setType(String type) {
//		this.type = type;
//	}

}
