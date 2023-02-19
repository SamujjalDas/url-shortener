package com.urlshortener.model;

public class UrlErrorResponseDto {
	private String status;
	private String error;
	
	public UrlErrorResponseDto() {
		super();
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	@Override
	public String toString() {
		return "UrlErrorResponseDto [status=" + status + ", error=" + error + "]";
	}
	
}
