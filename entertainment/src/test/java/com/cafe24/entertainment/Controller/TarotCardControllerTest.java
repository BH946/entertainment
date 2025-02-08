package com.cafe24.entertainment.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cafe24.entertainment.Entity.TarotCard;
import com.cafe24.entertainment.Entity.dto.TarotCardRequestDto;
import com.cafe24.entertainment.Service.TarotCardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Slf4j
@WebMvcTest(controllers = TarotCardController.class)
class TarotCardControllerTest {

  @Autowired
  MockMvc mockMvc;
  @MockitoBean //가짜 객체
  TarotCardService tarotCardService;

  String nameEn = "The Fool";
  String nameKr = "바보";
  Long number = 0L;
  String imageUrl = null;
  String keyword = "새로운 시작, 순수함, 자유로운 영혼, 모험";

  @Test
  public void 타로카드_저장_조회() throws Exception {
    // given
    Map map = new HashMap();
//    map.put("nameEn", nameEn); //@valid 확인위해 일부러 누락
    map.put("nameKr", nameKr);
    map.put("number", number);
    map.put("imageUrl", imageUrl);
    map.put("keyword", keyword);
    TarotCardRequestDto dto = new TarotCardRequestDto(nameEn, nameKr, number, imageUrl, keyword);
    ObjectMapper obj = new ObjectMapper(); //json 형태 위해
    String content = obj.writeValueAsString(dto);
    String contentValid = obj.writeValueAsString(map);
    log.info("content: {}", content);
    log.info("contentValid: {}", contentValid);

    // when
    //save()
    when(tarotCardService.save(any())).thenReturn(1L);
    mockMvc.perform(
            post("/api/v1/tarotCard")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(content)
        )
        .andExpect(status().isCreated())
        .andDo(print());

    //save()-Valid 테스트
    mockMvc.perform(
            post("/api/v1/tarotCard")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(contentValid)
        )
        .andExpect(status().isBadRequest())
        .andDo(print());

    //findById()
    when(tarotCardService.findById(1L)).thenReturn(Optional.of(TarotCard.createTarotCard(dto)));
    mockMvc.perform(
            get("/api/v1/tarotCard/" + 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
        .andExpect(status().isOk())
        .andDo(print());

    // then
    verify(tarotCardService).save(any());
    verify(tarotCardService).findById(any());
  }

  @Test
  public void 타로카드조회_번호_이름() throws Exception {
    // given
    TarotCardRequestDto dto = new TarotCardRequestDto(nameEn, nameKr, number, imageUrl, keyword);
    TarotCard tarotCard = TarotCard.createTarotCard(dto);

    // when
    when(tarotCardService.findByCardNumber(number)).thenReturn(tarotCard);
    when(tarotCardService.findByNameEn(nameEn)).thenReturn(tarotCard);
    when(tarotCardService.findByNameKr(nameKr)).thenReturn(tarotCard);
    mockMvc.perform(
            get("/api/v1/tarotCard/number/" + number)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
        .andExpect(status().isOk())
        .andDo(print());

    mockMvc.perform(
            get("/api/v1/tarotCard/nameEn/" + nameEn)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
        .andExpect(status().isOk())
        .andDo(print());

    mockMvc.perform(
            get("/api/v1/tarotCard/nameKr/" + nameKr)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
        .andExpect(status().isOk())
        .andDo(print());

    // then
    verify(tarotCardService).findByCardNumber(number);
    verify(tarotCardService).findByNameEn(nameEn);
    verify(tarotCardService).findByNameKr(nameKr);
  }
}