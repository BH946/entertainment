package com.cafe24.entertainment.Service;

import static org.junit.jupiter.api.Assertions.*;

import com.cafe24.entertainment.Entity.CardReading;
import com.cafe24.entertainment.Entity.ReadingCategory;
import com.cafe24.entertainment.Entity.ReadingType;
import com.cafe24.entertainment.Entity.TarotCard;
import com.cafe24.entertainment.Entity.dto.CardReadingRequestDto;
import com.cafe24.entertainment.Entity.dto.TarotCardRequestDto;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

  @Test
  @Order(4)
  public void 오늘의타로() throws Exception {
    // given
    TarotCard tarotCard = tarotCardService.findByCardNumber(20L);
    CardReading findCardReading = null;

    // when
    for (int i = 0; i < 10; i++) {
      findCardReading = cardReadingService.getRandomReading(tarotCard);
      log.info("오늘의타로 추출: {}", findCardReading.getDescription());
    }

    // then
    Assertions.assertInstanceOf(CardReading.class, findCardReading);
  }

  @Test
  @Order(5)
  public void 카테고리별타로() throws Exception {
    // given
    TarotCard tarotCard = tarotCardService.findByCardNumber(20L);
    CardReading findCardReading = null;

    // when
    for (int i = 0; i < 10; i++) {
      findCardReading = cardReadingService.getRandomReadingByCategory(tarotCard, "love");
      log.info("카테고리별타로 추출: {}", findCardReading.getDescription());
      findCardReading = cardReadingService.getRandomReadingByCategory(tarotCard, "yesno");
      log.info("카테고리별타로 추출: {}", findCardReading.getDescription());
    }

    // then
    Assertions.assertInstanceOf(CardReading.class, findCardReading);
  }


  //데이터 삽입용
  @Test
  @Order(3)
  @Transactional //동일한 범위의 영속성 컨텍스트 사용
  @Rollback(value = false)
  public void init() {
    // 타로 카드 생성
    TarotCard card = TarotCard.createTarotCard(new TarotCardRequestDto(
        "The Fool", "바보", 20L, null, "시작, 모험"
    ));
    tarotCardService.save(card);

    // 카드 리딩 데이터 생성
    createReading(card, 20L, "일반 관련 조언입니다.UP", ReadingCategory.GENERAL, ReadingType.UPRIGHT);
    createReading(card, 20L, "건강 관련 조언입니다.UP", ReadingCategory.HEALTH, ReadingType.UPRIGHT);
    createReading(card, 20L, "직업 관련 조언입니다.UP", ReadingCategory.CAREER, ReadingType.UPRIGHT);
    createReading(card, 20L, "재물 관련 조언입니다.UP", ReadingCategory.WEALTH, ReadingType.UPRIGHT);
    createReading(card, 20L, "사랑 관련 조언입니다.UP", ReadingCategory.LOVE, ReadingType.UPRIGHT);
    createReading(card, 20L, "일반 관련 조언입니다.UP2", ReadingCategory.GENERAL, ReadingType.UPRIGHT);
    createReading(card, 20L, "건강 관련 조언입니다.UP2", ReadingCategory.HEALTH, ReadingType.UPRIGHT);
    createReading(card, 20L, "직업 관련 조언입니다.UP2", ReadingCategory.CAREER, ReadingType.UPRIGHT);
    createReading(card, 20L, "재물 관련 조언입니다.UP2", ReadingCategory.WEALTH, ReadingType.UPRIGHT);
    createReading(card, 20L, "사랑 관련 조언입니다.UP2", ReadingCategory.LOVE, ReadingType.UPRIGHT);

    createReading(card, 20L, "일반 관련 조언입니다.RE", ReadingCategory.GENERAL, ReadingType.REVERSED);
    createReading(card, 20L, "건강 관련 조언입니다.RE", ReadingCategory.HEALTH, ReadingType.REVERSED);
    createReading(card, 20L, "직업 관련 조언입니다.RE", ReadingCategory.CAREER, ReadingType.REVERSED);
    createReading(card, 20L, "재물 관련 조언입니다.RE", ReadingCategory.WEALTH, ReadingType.REVERSED);
    createReading(card, 20L, "사랑 관련 조언입니다.RE", ReadingCategory.LOVE, ReadingType.REVERSED);
    createReading(card, 20L, "일반 관련 조언입니다.RE2", ReadingCategory.GENERAL, ReadingType.REVERSED);
    createReading(card, 20L, "건강 관련 조언입니다.RE2", ReadingCategory.HEALTH, ReadingType.REVERSED);
    createReading(card, 20L, "직업 관련 조언입니다.RE2", ReadingCategory.CAREER, ReadingType.REVERSED);
    createReading(card, 20L, "재물 관련 조언입니다.RE2", ReadingCategory.WEALTH, ReadingType.REVERSED);
    createReading(card, 20L, "사랑 관련 조언입니다.RE2", ReadingCategory.LOVE, ReadingType.REVERSED);

    createReading(card, 20L, "YES 관련 조언입니다.UP", ReadingCategory.YESNO, ReadingType.UPRIGHT);
    createReading(card, 20L, "NO 관련 조언입니다.RE", ReadingCategory.YESNO, ReadingType.REVERSED);
  }

  private void createReading(TarotCard card, Long cardNumber, String description, ReadingCategory category, ReadingType type) {
    CardReading reading = CardReading.createCardReading(new CardReadingRequestDto(
        cardNumber, description, category, type
    ));
    reading.setTarotCard(card);
    cardReadingService.save(reading);
  }
}