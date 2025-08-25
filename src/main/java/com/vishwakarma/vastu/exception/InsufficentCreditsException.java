package com.vishwakarma.vastu.exception;

public class InsufficentCreditsException extends VastuCheckedException {

	public InsufficentCreditsException() {
		super();
	}

	public InsufficentCreditsException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InsufficentCreditsException(String exceptionMsg) {
		super(exceptionMsg);
	}

	public InsufficentCreditsException(Throwable arg0) {
		super(arg0);
	}

}
