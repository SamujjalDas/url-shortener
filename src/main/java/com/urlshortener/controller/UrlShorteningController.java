package com.urlshortener.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.urlshortener.model.Url;
import com.urlshortener.model.UrlDto;
import com.urlshortener.model.UrlErrorResponseDto;
import com.urlshortener.model.UrlResponseDto;

import com.urlshortener.service.UrlService;

@Controller
public class UrlShorteningController {

	@Autowired
	private UrlService urlService;

	@PostMapping("/generate")
	public ResponseEntity<?> generateShortLink(@RequestBody UrlDto urlDto) {
		Url urlToRet = urlService.generateShortLink(urlDto);

		if (urlToRet != null) {
			UrlResponseDto urlResponseDto = new UrlResponseDto();
			urlResponseDto.setOriginalUrl(urlToRet.getOriginalUrl());
			urlResponseDto.setExpirationDate(urlToRet.getExpirationDate());
			urlResponseDto.setShortLink(urlToRet.getShortLink());
			return new ResponseEntity<UrlResponseDto>(urlResponseDto, HttpStatus.OK);
		}
		UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
		urlErrorResponseDto.setStatus("404");
		urlErrorResponseDto.setError("There was and error processing your request. please try again");
		return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
	}

	@GetMapping("/s/{shortLink}")
	public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortLink, HttpServletResponse response)
			throws IOException {
		if (StringUtils.isEmpty(shortLink)) {
			UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
			urlErrorResponseDto.setStatus("400");
			urlErrorResponseDto.setError("Invalid URL");
			return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
		}

		Url urlToRet = urlService.getEncodedUrl(shortLink);

		if (urlToRet == null) {
			UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
			urlErrorResponseDto.setStatus("400");
			urlErrorResponseDto.setError("URL doesn't exist or it might have expired");
			return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
		}

/*		if (urlToRet.getExpirationDate().isBefore(LocalDateTime.now())) {
			urlService.deleteShortLink(urlToRet);
			UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
			urlErrorResponseDto.setStatus("200");
			urlErrorResponseDto.setError("URL has expired. Please generate a new one");
			return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
		}*/
		response.sendRedirect(urlToRet.getOriginalUrl());
		return null;
	}

	@GetMapping("/test")
	public String testConnection() {
		return "Service is Up";
	}

	@DeleteMapping("/delete")
	public String deleteAllLinks() {
		urlService.deleteAllLinks();
		return "Deleted all links";
	}

	@PostMapping("/generateShortLink")
	public String generateShortLinkWeb(@RequestParam Map<String, String> url, HttpServletResponse response, Model model) throws IOException {
		Url urlToRet = urlService.generateShortLink(new UrlDto(url.get("url"), null));
		response.setStatus(201);
		model.addAttribute("sh", urlToRet);
		return "index";
	}

}