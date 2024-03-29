package com.urlshortener.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.hash.Hashing;
import com.urlshortener.model.Url;
import com.urlshortener.model.UrlDto;
import com.urlshortener.repository.UrlRepository;

@Component
public class UrlServiceImpl implements UrlService {
	@Autowired
	private UrlRepository urlRepository;

	@Override
	public Url generateShortLink(UrlDto urlDto) {
		if (StringUtils.isNotEmpty(urlDto.getUrl())) {
			String encodeUrl = encodeUrl(urlDto.getUrl());
			Url urlToPersist = new Url();
			urlToPersist.setCreationDate(LocalDateTime.now());
			String originalUrl = checkAndModifyUrl(urlDto.getUrl());
			urlToPersist.setOriginalUrl(originalUrl);
			urlToPersist.setShortLink(encodeUrl);
			urlToPersist
					.setExpirationDate(getExpirationDate(urlDto.getExpirationDate(), urlToPersist.getCreationDate()));
			Url urlToRet = persistShortLink(urlToPersist);
			String baseurl = System.getenv("baseurl");
			if(baseurl==null) baseurl = "http://localhost:8080";
			urlToPersist.setShortLink(baseurl+"/s/"+encodeUrl);
			return urlToRet;
		}
		return null;
	}

	private LocalDateTime getExpirationDate(String expirationDate, LocalDateTime creationDate) {
		if (StringUtils.isBlank(expirationDate))
			return creationDate.plusMonths(3);
		LocalDateTime expirationDateToRet = LocalDateTime.parse(expirationDate);
		return expirationDateToRet;
	}

	private String encodeUrl(String url) {
		String encodeUrl = "";
		LocalDateTime time = LocalDateTime.now();
		encodeUrl = Hashing.murmur3_32().hashString(url.concat(time.toString()), StandardCharsets.UTF_8).toString();
		return encodeUrl;
	}

	@Override
	public Url persistShortLink(Url url) {
		Url urlToRet = urlRepository.save(url);
		return urlToRet;
	}

	@Override
	public Url getEncodedUrl(String url) {
		Url urlToRet = urlRepository.findByShortLink(url);
		return urlToRet;
	}

	@Override
	public void deleteShortLink(Url url) {
		urlRepository.delete(url);
	}

	@Override
	public void deleteAllLinks() {
		urlRepository.deleteAll();
	}

	private String checkAndModifyUrl(String url) {
		// Check if the URL starts with "http://"
		if (!url.startsWith("http://") && !url.startsWith("https://")) {
			// If it doesn't, append "https://"
			url = "http://" + url;
		}
		return url;
	}

}
