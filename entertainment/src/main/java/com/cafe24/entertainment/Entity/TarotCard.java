package com.cafe24.entertainment.Entity;

import com.cafe24.entertainment.Entity.dto.TarotCardRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Getter @Setter @Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TarotCard {
  @Id
  @GeneratedValue
  @Column(name = "tarot_card_id")
  private Long id;
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

  //생성 편의 메서드
  public static TarotCard createTarotCard(TarotCardRequestDto tarotCardRequestDto) {
    TarotCard tarotCard = new TarotCard();
    tarotCard.nameEn = tarotCardRequestDto.getNameEn();
    tarotCard.nameKr = tarotCardRequestDto.getNameKr();
    tarotCard.cardNumber = tarotCardRequestDto.getCardNumber();
    tarotCard.imageUrl = tarotCardRequestDto.getImageUrl();
    tarotCard.keyword = tarotCardRequestDto.getKeyword();
    tarotCard.createdAt = LocalDateTime.now();
    tarotCard.updatedAt = LocalDateTime.now();
    return tarotCard;
  }

}
