package com.urlshortener.service;

import org.springframework.stereotype.Service;
import com.urlshortener.model.UrlDto;
import com.urlshortener.model.Url;

@Service
public interface UrlService {

	public Url generateShortLink(UrlDto urlDto);

	public Url persistShortLink(Url url);

	public Url getEncodedUrl(String url);

	public void deleteShortLink(Url url);
}
