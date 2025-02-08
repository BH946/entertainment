package com.cafe24.entertainment.Entity.dto;

import com.cafe24.entertainment.Entity.TarotCard;
import jakarta.persistence.Column;
import java.time.LocalDateTime;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
public class TarotCardResponseDto {
  @NotNull
  private String nameEn;
  @NotNull
  private String nameKr;
  @NotNull @Column(unique = true)
  private Long cardNumber;
  private String imageUrl;
  private String keyword;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public TarotCardResponseDto(TarotCard card) {
    this.nameEn = card.getNameEn();
    this.nameKr = card.getNameKr();
    this.cardNumber = card.getCardNumber();
    this.imageUrl = card.getImageUrl();
    this.keyword = card.getKeyword();
    this.createdAt = card.getCreatedAt();
    this.updatedAt = card.getUpdatedAt();
  }
}
