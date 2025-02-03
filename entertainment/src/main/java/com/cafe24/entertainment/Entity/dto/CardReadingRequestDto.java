package com.cafe24.entertainment.Entity.dto;

import com.cafe24.entertainment.Entity.ReadingType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
public class CardReadingRequestDto {
  private String description;
  @Enumerated(EnumType.STRING)
  private ReadingType readingType;


  public CardReadingRequestDto(String description, ReadingType readingType) {
    this.description = description;
    this.readingType = readingType;
  }
}
