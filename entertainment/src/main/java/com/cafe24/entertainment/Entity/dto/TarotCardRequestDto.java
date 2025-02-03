package com.cafe24.entertainment.Entity.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
public class TarotCardRequestDto {
  @NotNull
  private String nameEn;
  @NotNull
  private String nameKr;
  @NotNull @Column(unique = true)
  private Long cardNumber;
  private String imageUrl;
  private String keyword;

  public TarotCardRequestDto(String nameEn, String nameKr, Long cardNumber, String imageUrl,
      String keyword) {
    this.nameEn = nameEn;
    this.nameKr = nameKr;
    this.cardNumber = cardNumber;
    this.imageUrl = imageUrl;
    this.keyword = keyword;
  }
}
