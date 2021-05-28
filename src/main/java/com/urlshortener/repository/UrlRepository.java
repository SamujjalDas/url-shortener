package com.urlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.urlshortener.model.Url;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
	@Transactional
	public Url findByShortLink(String shortLink);
}
