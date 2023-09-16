package com.suhwan.cowtalk.exchange.service;

import com.suhwan.cowtalk.exchange.entity.Exchange;
import com.suhwan.cowtalk.exchange.model.ExchangeEditRequest;
import com.suhwan.cowtalk.exchange.repository.ExchangeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExchangeService {

    private final ExchangeRepository exchangeRepository;

    // 거래소 추가
    public Exchange insertExchange(String name) {
        if (exchangeRepository.findByName(name).isPresent()) {
            // 예외처리 임시
            log.error("IllegalStateException: 이미 존재하는 거래소입니다.");
            throw new IllegalStateException("이미 존재하는 거래소입니다.");
        }

        return exchangeRepository.save(Exchange.builder()
                .name(name)
                .createDate(LocalDateTime.now())
                .build());
    }

    // 거래소 정보
    @Transactional(readOnly = true)
    public Exchange readExchange(Long id) {
        // 예외처리 임시
        return exchangeRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("찾을 수 없는 거래소 번호입니다."));
    }

    // 거래소 수정
    @Transactional
    public Exchange updateExchange(ExchangeEditRequest request) {
        Exchange exchange = exchangeRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalStateException("찾을 수 없는 거래소 번호입니다."));

        exchange.update(request.getName(), LocalDateTime.now());
        return exchange;
    }

    // 거래소 삭제
    @Transactional
    public Exchange deleteExchange(Long id) {
        Exchange exchange = exchangeRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("찾을 수 없는 거래소 번호입니다."));

        exchange.delete(LocalDateTime.now());
        return exchange;
    }
}