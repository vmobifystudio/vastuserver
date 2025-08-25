package com.vishwakarma.vastu.exception;

public class VastuCheckedException extends Exception{
		 
		protected String exceptionMsg;
	 
		public VastuCheckedException() {
			super();
		}
				
		public VastuCheckedException(String arg0, Throwable arg1) {
			super(arg0, arg1);
		}


		public VastuCheckedException(Throwable arg0) {
			super(arg0);
		}

		public VastuCheckedException(String exceptionMsg) {
			this.exceptionMsg = exceptionMsg;
		}
		
		public String getExceptionMsg(){
			return this.exceptionMsg;
		}
		
		public void setExceptionMsg(String exceptionMsg) {
			this.exceptionMsg = exceptionMsg;
		}
}

