package com.suhwan.cowtalk.exchange.service;

import static com.suhwan.cowtalk.common.type.ErrorCode.ALREADY_EXIST_EXCHANGE;
import static com.suhwan.cowtalk.common.type.ErrorCode.INVALID_EXCHANGE_ID;

import com.suhwan.cowtalk.common.exception.ExchangeException;
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
    if (exchangeRepository.findByEnglishName(request.getEnglishName()).isPresent()) {
      throw new ExchangeException(ALREADY_EXIST_EXCHANGE);
    }

    return ExchangeDto.fromEntity(
        exchangeRepository.save(Exchange.builder()
            .koreanName(request.getKoreanName())
            .englishName(request.getEnglishName())
            .build())
    );
  }

  // 거래소 정보
  @Transactional(readOnly = true)
  public ExchangeDto getExchange(Long id) {
    Exchange exchange = exchangeRepository.findById(id)
        .orElseThrow(() -> new ExchangeException(INVALID_EXCHANGE_ID));

    return ExchangeDto.fromEntity(exchange);
  }

  // 거래소 수정
  @Transactional
  public ExchangeDto updateExchange(Long id, UpdateExchangeRequest request) {
    Exchange exchange = exchangeRepository.findById(id)
        .orElseThrow(() -> new ExchangeException(INVALID_EXCHANGE_ID));

    exchange.update(request.getKoreanName(), request.getEnglishName(), LocalDateTime.now());

    return ExchangeDto.fromEntity(exchange);
  }

  // 거래소 삭제
  @Transactional
  public ExchangeDto deleteExchange(Long id) {
    Exchange exchange = exchangeRepository.findById(id)
        .orElseThrow(() -> new ExchangeException(INVALID_EXCHANGE_ID));

    exchange.delete(LocalDateTime.now());

    return ExchangeDto.fromEntity(exchange);
  }
}