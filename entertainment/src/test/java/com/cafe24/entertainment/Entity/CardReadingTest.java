package com.cafe24.entertainment.Entity;

import com.cafe24.entertainment.Entity.dto.CardReadingRequestDto;
import com.cafe24.entertainment.Entity.dto.TarotCardRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class CardReadingTest {

  @Test
  void 생성_편의_메서드() {
    // given
    CardReadingRequestDto dto = new CardReadingRequestDto(0L, "test", ReadingCategory.LOVE, ReadingType.UPRIGHT);

    // when
    CardReading cardReading = CardReading.createCardReading(dto);

    // then
    Assertions.assertInstanceOf(CardReading.class, cardReading);
    Assertions.assertEquals("test", cardReading.getDescription());
    Assertions.assertEquals(ReadingCategory.LOVE, cardReading.getReadingCategory());
    Assertions.assertEquals(ReadingType.UPRIGHT, cardReading.getReadingType());
    Assertions.assertNotNull(cardReading.getCreatedAt());
    Assertions.assertNotNull(cardReading.getUpdatedAt());
  }

  @Test
  public void 연관관계_편의_메서드() throws Exception {
    // given
    CardReading cardReading = CardReading.createCardReading(
        new CardReadingRequestDto(0L, "test2", ReadingCategory.LOVE, ReadingType.REVERSED));
    TarotCard tarotCard = TarotCard.createTarotCard(
        new TarotCardRequestDto("abc", "에이비씨", 0L, null, "사랑,운면,장난"));

    // when
    cardReading.setTarotCard(tarotCard);

    // then
    Assertions.assertEquals(tarotCard, cardReading.getTarotCard());
  }
}