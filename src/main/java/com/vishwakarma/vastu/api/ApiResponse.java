package com.vishwakarma.vastu.api;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ApiResponse implements Serializable{

	private boolean success;
	private ApiError error;
	private Map<String, Object> data;

	public ApiResponse(boolean success) {
		this.success = success;
	}

	public class ApiError implements Serializable {

		private String message, code;

		public ApiError() {
			super();
		}

		public ApiError(String message, String code) {
			super();
			this.message = message;
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

	}

	public ApiResponse setError(String errorMessage, String errorCode) {
		error = new ApiError(errorMessage, errorCode);
		return this;
	}

	public ApiResponse addData(String key, Object value) {
		if (null == data) {
			data = new HashMap<String, Object>();
		}
		data.put(key, value);
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public ApiResponse setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public ApiError getError() {
		return error;
	}

	public void setError(ApiError error) {
		this.error = error;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

}
