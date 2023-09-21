package com.suhwan.cowtalk.coin.repository;

import com.suhwan.cowtalk.coin.entity.Coin;
import com.suhwan.cowtalk.exchange.entity.Exchange;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin, Long> {

  Optional<Coin> findByInitialAndExchange(String initial, Exchange exchange);

  boolean existsByIdAndDeleteDateIsNull(Long id);

  List<Coin> findAllByExchange(Exchange exchange);
}
