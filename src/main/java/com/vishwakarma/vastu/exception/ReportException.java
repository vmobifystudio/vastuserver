package com.vishwakarma.vastu.exception;

public class ReportException extends VastuCheckedException {

	public ReportException() {
		super();
	}
				
	public ReportException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}


	public ReportException(Throwable arg0) {
		super(arg0);
	}

	public ReportException(String exceptionMsg) {
		super(exceptionMsg);
	}

}
