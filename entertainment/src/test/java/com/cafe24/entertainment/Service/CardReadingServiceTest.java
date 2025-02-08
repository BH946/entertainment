package com.cafe24.entertainment.Service;

import static org.junit.jupiter.api.Assertions.*;

import com.cafe24.entertainment.Entity.CardReading;
import com.cafe24.entertainment.Entity.ReadingCategory;
import com.cafe24.entertainment.Entity.ReadingType;
import com.cafe24.entertainment.Entity.TarotCard;
import com.cafe24.entertainment.Entity.dto.CardReadingRequestDto;
import com.cafe24.entertainment.Entity.dto.TarotCardRequestDto;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Order;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest //@Autowired
@Slf4j
class CardReadingServiceTest {

  @Autowired
  CardReadingService cardReadingService;
  @Autowired
  TarotCardService tarotCardService;
  @Autowired
  EntityManager em;
  /*
  save, findById
  List<CardReading> findByTarotCard(TarotCard tarotCard);
   */
  String description = "당신의 삶에 새로운 시작과 기회가 찾아오고 있습니다. 순수한 마음과 열린 태도로 모험을 시작하세요. 걱정과 두려움을 내려놓고 자유롭게 도전하면 무한한 가능성이 열릴 것입니다.";

  @Test
  @Order(1)
  @Transactional //동일한 범위의 영속성 컨텍스트 사용
  @Rollback(value = false)
  public void 카드리딩_저장_조회() throws Exception {
    // given
    TarotCard tarotCard = TarotCard.createTarotCard(new TarotCardRequestDto(
        "test", "테스트", 0L, null, "힐"
    ));
    CardReading cardReading = CardReading.createCardReading(new CardReadingRequestDto(
      0L, description, ReadingCategory.GENERAL, ReadingType.UPRIGHT
    ));
    tarotCardService.save(tarotCard); //FK 오류방지 순서 중요
    cardReading.setTarotCard(tarotCard);

    // when
    Long id = cardReadingService.save(cardReading);
    CardReading findCardReading = cardReadingService.findById(id).orElse(null);

    // then
    Assertions.assertEquals(id, findCardReading.getId());
    Assertions.assertEquals(cardReading, findCardReading); // @Transactional 없으면 객체 주소 다름
    log.info("description: {}", findCardReading.getDescription());
  }

  @Test
  @Order(2)
  public void 카드리딩조회_타로카드() throws Exception {
    // given
    TarotCard tarotCard = tarotCardService.findByCardNumber(0L);
    log.info("findNumber: {}", tarotCard.getCardNumber());

    // when
    List<CardReading> readings = cardReadingService.findByTarotCard(tarotCard);

    // then
    for (CardReading reading : readings) {
      Assertions.assertEquals(reading.getTarotCard().getId(), tarotCard.getId());
    }
    log.info("description: {}", readings.get(0).getDescription());
  }

}