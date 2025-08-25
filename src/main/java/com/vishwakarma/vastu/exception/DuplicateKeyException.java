package com.vishwakarma.vastu.exception;

public class DuplicateKeyException extends Exception{
		 
		private String exceptionMsg;
	 
		public DuplicateKeyException() {
			super();
		}
				
		public DuplicateKeyException(String arg0, Throwable arg1) {
			super(arg0, arg1);
		}


		public DuplicateKeyException(Throwable arg0) {
			super(arg0);
		}

		public DuplicateKeyException(String exceptionMsg) {
			this.exceptionMsg = exceptionMsg;
		}
		
		public String getExceptionMsg(){
			return this.exceptionMsg;
		}
		
		public void setExceptionMsg(String exceptionMsg) {
			this.exceptionMsg = exceptionMsg;
		}
}

