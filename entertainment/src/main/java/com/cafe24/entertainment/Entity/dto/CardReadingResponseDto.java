package com.cafe24.entertainment.Entity.dto;

import com.cafe24.entertainment.Entity.CardReading;
import com.cafe24.entertainment.Entity.ReadingCategory;
import com.cafe24.entertainment.Entity.ReadingType;
import com.cafe24.entertainment.Entity.TarotCard;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CardReadingResponseDto {
  private String description;
  @Enumerated(EnumType.STRING)
  private ReadingCategory readingCategory;
  @Enumerated(EnumType.STRING)
  private ReadingType readingType;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private TarotCard tarotCard; //딱히 사용안할 것 같아서 일부러 Lazy강제 초기화 안했음.

  public CardReadingResponseDto(CardReading cardReading) {
    this.description = cardReading.getDescription();
    this.readingCategory = cardReading.getReadingCategory();
    this.readingType = cardReading.getReadingType();
    this.createdAt = cardReading.getCreatedAt();
    this.updatedAt = cardReading.getUpdatedAt();
  }
}
