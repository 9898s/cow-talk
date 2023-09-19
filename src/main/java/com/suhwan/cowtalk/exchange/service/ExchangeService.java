package com.suhwan.cowtalk.exchange.service;

import com.suhwan.cowtalk.exchange.entity.Exchange;
import com.suhwan.cowtalk.exchange.model.AddExchangeRequest;
import com.suhwan.cowtalk.exchange.model.ExchangeDto;
import com.suhwan.cowtalk.exchange.model.UpdateExchangeRequest;
import com.suhwan.cowtalk.exchange.repository.ExchangeRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExchangeService {

  private final ExchangeRepository exchangeRepository;

  // 거래소 추가
  public ExchangeDto createExchange(AddExchangeRequest request) {
    if (exchangeRepository.findByName(request.getName()).isPresent()) {
      throw new IllegalStateException("이미 존재하는 거래소입니다.");
    }

    return ExchangeDto.fromEntity(
        exchangeRepository.save(Exchange.builder()
            .name(request.getName())
            .createDate(LocalDateTime.now())
            .build())
    );
  }

  // 거래소 정보
  @Transactional(readOnly = true)
  public ExchangeDto getExchange(Long id) {
    Exchange exchange = exchangeRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 거래소 번호입니다."));

    return ExchangeDto.fromEntity(exchange);
  }

  // 거래소 수정
  @Transactional
  public ExchangeDto updateExchange(Long id, UpdateExchangeRequest request) {
    Exchange exchange = exchangeRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 거래소 번호입니다."));

    exchange.update(request.getName(), LocalDateTime.now());

    return ExchangeDto.fromEntity(exchange);
  }

  // 거래소 삭제
  @Transactional
  public ExchangeDto deleteExchange(Long id) {
    Exchange exchange = exchangeRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 거래소 번호입니다."));

    exchange.delete(LocalDateTime.now());

    return ExchangeDto.fromEntity(exchange);
  }
}