package com.cafe24.entertainment.Entity.dto;

import com.cafe24.entertainment.Entity.ReadingCategory;
import com.cafe24.entertainment.Entity.ReadingType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
public class CardReadingResponseDto {
  private String description;
  @Enumerated(EnumType.STRING)
  private ReadingCategory readingCategory;
  @Enumerated(EnumType.STRING)
  private ReadingType readingType;

  public CardReadingResponseDto(String description, ReadingCategory readingCategory,
      ReadingType readingType) {
    this.description = description;
    this.readingCategory = readingCategory;
    this.readingType = readingType;
  }
}
