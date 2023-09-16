package com.suhwan.cowtalk.exchange.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suhwan.cowtalk.exchange.entity.Exchange;
import com.suhwan.cowtalk.exchange.model.ExchangeEditRequest;
import com.suhwan.cowtalk.exchange.model.ExchangeEditResponse;
import com.suhwan.cowtalk.exchange.service.ExchangeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExchangeApiController.class)
class ExchangeApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ExchangeService exchangeService;

    @Test
    void 거래소를_추가한다() throws Exception {
        // given
        given(exchangeService.insertExchange(anyString()))
                .willReturn(Exchange.builder()
                        .id(1L)
                        .name("코인빗")
                        .build());

        // then
        mockMvc.perform(post("/api/exchange/코인빗")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("코인빗"))
                .andDo(print());
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

    @Test
    void 거래소를_수정한다() throws Exception {
        // given
        given(exchangeService.updateExchange(any(ExchangeEditRequest.class)))
                .willReturn(Exchange.builder()
                        .id(1L)
                        .name("코인빗")
                        .build());

        // when
        when(exchangeService.updateExchange(any(ExchangeEditRequest.class)))
                .thenReturn(Exchange.builder()
                        .id(1L)
                        .name("코빗")
                        .build());

        // then
        mockMvc.perform(put("/api/exchange/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new ExchangeEditRequest(1L, "코빗")
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("코빗"))
                .andDo(print());
    }
}