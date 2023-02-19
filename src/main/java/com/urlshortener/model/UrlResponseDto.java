package com.urlshortener.model;

import java.time.LocalDateTime;

public class UrlResponseDto {

	private String originalUrl;
	private String shortLink;
	private LocalDateTime expirationDate;
	
	public UrlResponseDto() {
		super();
	}
	public String getOriginalUrl() {
		return originalUrl;
	}
	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}
	public String getShortLink() {
		return shortLink;
	}
	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}
	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(LocalDateTime expirationDate) {
		this.expirationDate = expirationDate;
	}
	@Override
	public String toString() {
		return "urlResponseDto [originalUrl=" + originalUrl + ", shortLink=" + shortLink + ", expirationDate="
				+ expirationDate + "]";
	}

}
