package com.suhwan.cowtalk.exchange.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suhwan.cowtalk.exchange.entity.Exchange;
import com.suhwan.cowtalk.exchange.model.ExchangeResponse;
import com.suhwan.cowtalk.exchange.service.ExchangeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExchangeApiController.class)
class ExchangeApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ExchangeService exchangeService;

    @Test
    void 거래소가_추가된다() throws Exception {
        // given
        String name = "코인빗";
        Exchange exchange = Exchange.builder()
                .name(name)
                .build();

        given(exchangeService.insertExchange(anyString())).willReturn(exchange);

        // when
        mockMvc.perform(post("/api/exchange/" + name))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(ExchangeResponse.of(exchange))));

        // then
        verify(exchangeService, times(1)).insertExchange(anyString());
    }

    @Test
    void 거래소를_조회한다() throws Exception {
        // given
        given(exchangeService.readExchange(anyLong()))
                .willReturn(Exchange.builder()
                        .id(1L)
                        .name("코인빗")
                        .build());

        // then
        mockMvc.perform(get("/api/exchange/1"))
                .andDo(print())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("코인빗"))
                .andExpect(status().isOk());
    }
}