package com.vishwakarma.vastu.exception;

public class VastuException extends RuntimeException{
		 
		private String exceptionMsg;
		private String errorCode = "400";
	 
		public VastuException() {
			super();
		}
		
		public VastuException(String exceptionMsg, String errorCode) {
			super();
			this.exceptionMsg = exceptionMsg;
			this.errorCode = errorCode;
		}

		public VastuException(String arg0, Throwable arg1) {
			super(arg0, arg1);
		}

		public VastuException(Throwable arg0) {
			super(arg0);
		}

		public VastuException(String exceptionMsg) {
			this.exceptionMsg = exceptionMsg;
		}
		
		public String getExceptionMsg(){
			return this.exceptionMsg;
		}
		
		public void setExceptionMsg(String exceptionMsg) {
			this.exceptionMsg = exceptionMsg;
		}

		public String getErrorCode() {
			return errorCode;
		}

		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}
}

