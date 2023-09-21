package com.suhwan.cowtalk.coin.service;

import com.suhwan.cowtalk.coin.entity.Coin;
import com.suhwan.cowtalk.coin.model.CoinDto;
import com.suhwan.cowtalk.coin.repository.CoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CoinService {

  private final CoinRepository coinRepository;

  // 코인 삭제
  @Transactional
  public CoinDto deleteCoin(Long id) {
    Coin coin = coinRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 코인 번호입니다."));

    if (!coinRepository.existsByIdAndDeleteDateIsNull(id)) {
      throw new IllegalStateException("이미 삭제된 코인 번호입니다.");
    }

    coin.delete();

    return CoinDto.fromEntity(coin);
  }
}
