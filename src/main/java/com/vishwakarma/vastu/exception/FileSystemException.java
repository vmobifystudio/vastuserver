package com.vishwakarma.vastu.exception;

public class FileSystemException extends VastuCheckedException {

	public FileSystemException() {
		super();
	}
				
	public FileSystemException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}


	public FileSystemException(Throwable arg0) {
		super(arg0);
	}

	public FileSystemException(String exceptionMsg) {
		super(exceptionMsg);
	}

}
