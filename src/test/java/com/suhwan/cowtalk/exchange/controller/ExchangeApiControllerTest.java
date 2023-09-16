package com.suhwan.cowtalk.exchange.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suhwan.cowtalk.exchange.entity.Exchange;
import com.suhwan.cowtalk.exchange.model.ExchangeResponse;
import com.suhwan.cowtalk.exchange.service.ExchangeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ExchangeApiControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ExchangeService exchangeService;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ExchangeApiController(exchangeService)).build();
    }

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
}