package com.cafe24.entertainment.Entity.dto;

import com.cafe24.entertainment.Entity.ReadingCategory;
import com.cafe24.entertainment.Entity.ReadingType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor //잭슨 직렬화 위해 필요
public class CardReadingRequestDto {
  @NotNull
  private Long cardNumber;
  private String description;
  @Enumerated(EnumType.STRING)
  private ReadingCategory readingCategory;
  @Enumerated(EnumType.STRING)
  private ReadingType readingType;

  public CardReadingRequestDto(Long cardNumber, String description, ReadingCategory readingCategory,
      ReadingType readingType) {
    this.cardNumber = cardNumber;
    this.description = description;
    this.readingCategory = readingCategory;
    this.readingType = readingType;
  }
}
