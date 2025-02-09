package com.cafe24.entertainment.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.cafe24.entertainment.Entity.CardReading;
import com.cafe24.entertainment.Entity.ReadingCategory;
import com.cafe24.entertainment.Entity.ReadingType;
import com.cafe24.entertainment.Entity.TarotCard;
import com.cafe24.entertainment.Entity.dto.CardReadingRequestDto;
import com.cafe24.entertainment.Entity.dto.TarotCardRequestDto;
import com.cafe24.entertainment.Service.CardReadingService;
import com.cafe24.entertainment.Service.TarotCardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@WebMvcTest(controllers = CardReadingController.class)
class CardReadingControllerTest {

  @Autowired
  MockMvc mockMvc;
  @MockitoBean //가짜 객체
  CardReadingService cardReadingService;
  @MockitoBean
  TarotCardService tarotCardService;

  String nameEn = "The Fool";
  String nameKr = "바보";
  Long number = 0L;
  String imageUrl = null;
  String keyword = "새로운 시작, 순수함, 자유로운 영혼, 모험";
  String description = "설명입니다.";

  @Test
  public void 카드리딩_저장_조회() throws Exception {
    // given
    Map map = new HashMap();
    map.put("cardNumber", number);
    map.put("description", description);
    map.put("readingCategory", ReadingCategory.GENERAL);
    map.put("readingType", ReadingType.UPRIGHT);
    TarotCard tarotCard = TarotCard.createTarotCard(new TarotCardRequestDto(nameEn, nameKr, number, imageUrl, keyword));
    CardReading cardReading = CardReading.createCardReading(new CardReadingRequestDto(
        number, description, ReadingCategory.GENERAL, ReadingType.UPRIGHT
    ));
    cardReading.setTarotCard(tarotCard);
    ObjectMapper obj = new ObjectMapper();
    String content = obj.writeValueAsString(map);
    map.remove("cardNumber");
    String contentValid = obj.writeValueAsString(map);

    // when
    when(tarotCardService.findByCardNumber(number)).thenReturn(tarotCard);
    when(cardReadingService.save(any())).thenReturn(1L);
    mockMvc.perform(
            post("/api/v1/cardReading")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(content)
        )
        .andExpect(status().isCreated())
        .andDo(print());

    //save()-Valid 테스트
    mockMvc.perform(
            post("/api/v1/cardReading")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(contentValid)
        )
        .andExpect(status().isBadRequest()) //400
        .andDo(print());

    //findById()
    when(cardReadingService.findById(1L)).thenReturn(Optional.of(cardReading));
    mockMvc.perform(
            get("/api/v1/cardReading/" + 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
        .andExpect(status().isOk())
        .andDo(print());

    // then
    verify(cardReadingService).save(any());
    verify(cardReadingService).findById(any());
    verify(tarotCardService).findByCardNumber(number);
  }

  @Test
  public void 오늘의타로() throws Exception {
    // given
    TarotCard tarotCard = TarotCard.createTarotCard(new TarotCardRequestDto(nameEn, nameKr, number, imageUrl, keyword));
    CardReading cardReading = CardReading.createCardReading(new CardReadingRequestDto(
        number, description, ReadingCategory.GENERAL, ReadingType.UPRIGHT
    ));
    cardReading.setTarotCard(tarotCard);

    // when
    when(tarotCardService.findByCardNumber(0L)).thenReturn(tarotCard);
    when(cardReadingService.getRandomReading(tarotCard)).thenReturn(cardReading);
    mockMvc.perform(
            get("/api/v1/cardReading/today/" + 0L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
        .andExpect(status().isOk())
        .andDo(print());

    // then
    verify(tarotCardService).findByCardNumber(any());
    verify(cardReadingService).getRandomReading(any());
  }

  @Test
  public void 카테고리별타로() throws Exception {
    // given
    TarotCard tarotCard = TarotCard.createTarotCard(new TarotCardRequestDto(nameEn, nameKr, number, imageUrl, keyword));
    CardReading cardReading = CardReading.createCardReading(new CardReadingRequestDto(
        number, description, ReadingCategory.GENERAL, ReadingType.UPRIGHT
    ));
    cardReading.setTarotCard(tarotCard);

    // when
    when(tarotCardService.findByCardNumber(0L)).thenReturn(tarotCard);
    when(cardReadingService.getRandomReadingByCategory(tarotCard, "general")).thenReturn(cardReading);
    mockMvc.perform(
            get("/api/v1/cardReading/category/" + 0L + "/general")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
        .andExpect(status().isOk())
        .andDo(print());

    // then
    verify(tarotCardService).findByCardNumber(any());
    verify(cardReadingService).getRandomReadingByCategory(any(), any());
  }
}