package com.suhwan.cowtalk.coin.repository;

import com.suhwan.cowtalk.coin.entity.Coin;
import com.suhwan.cowtalk.exchange.entity.Exchange;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin, Long> {

  Optional<Coin> findByInitialAndExchange(String initial, Exchange exchange);

  Page<Coin> findAllByExchange(Exchange exchange, Pageable pageable);
}
