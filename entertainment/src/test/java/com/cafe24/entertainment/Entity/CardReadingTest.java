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
    CardReadingRequestDto dto = new CardReadingRequestDto("test", ReadingType.UPRIGHT);

    // when
    CardReading cardReading = CardReading.createCardReading(dto);

    // then
    Assertions.assertInstanceOf(CardReading.class, cardReading);
    Assertions.assertEquals("test", cardReading.getDescription());
    Assertions.assertEquals(ReadingType.UPRIGHT, cardReading.getReadingType());
    Assertions.assertNotNull(cardReading.getCreatedAt());
    Assertions.assertNotNull(cardReading.getUpdatedAt());
  }

  @Test
  public void 연관관계_편의_메서드() throws Exception {
    // given
    CardReading cardReading = CardReading.createCardReading(
        new CardReadingRequestDto("test2", ReadingType.REVERSED));
    TarotCard tarotCard = TarotCard.createTarotCard(
        new TarotCardRequestDto("abc", "에이비씨", 0L, null, "사랑,운면,장난"));
    ReadingCategory readingCategory = ReadingCategory.createReadingCategory("health");

    // when
    cardReading.setReadingCategory(readingCategory);
    cardReading.setTarotCard(tarotCard);

    // then
    Assertions.assertEquals(tarotCard, cardReading.getTarotCard());
    Assertions.assertEquals(readingCategory, cardReading.getReadingCategory());
  }
}