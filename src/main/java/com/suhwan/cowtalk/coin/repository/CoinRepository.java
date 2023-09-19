package com.suhwan.cowtalk.coin.repository;

import com.suhwan.cowtalk.coin.entity.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin, Long> {

}
