package com.vishwakarma.vastu.exception;

public class ObjectNotFoundException extends Exception{
		 
		private String exceptionMsg;
	 
		public ObjectNotFoundException() {
			super();
		}
				
		public ObjectNotFoundException(String arg0, Throwable arg1) {
			super(arg0, arg1);
		}


		public ObjectNotFoundException(Throwable arg0) {
			super(arg0);
		}

		public ObjectNotFoundException(String exceptionMsg) {
			this.exceptionMsg = exceptionMsg;
		}
		
		public String getExceptionMsg(){
			return this.exceptionMsg;
		}
		
		public void setExceptionMsg(String exceptionMsg) {
			this.exceptionMsg = exceptionMsg;
		}
}

