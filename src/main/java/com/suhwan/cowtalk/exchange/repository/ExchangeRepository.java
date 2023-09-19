package com.suhwan.cowtalk.exchange.repository;

import com.suhwan.cowtalk.exchange.entity.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

    Optional<Exchange> findByEnglishName(String englishName);
}
