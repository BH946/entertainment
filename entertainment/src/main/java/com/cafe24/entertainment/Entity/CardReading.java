package com.cafe24.entertainment.Entity;

import com.cafe24.entertainment.Entity.dto.CardReadingRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Setter
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardReading {

  @Id
  @GeneratedValue
  @Column(name = "card_reading_id")
  private Long id;
  private String description;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tarot_card_id") //FK
  private TarotCard tarotCard;
  @Enumerated(EnumType.STRING)
  private ReadingCategory readingCategory;
  @Enumerated(EnumType.STRING)
  private ReadingType readingType;

  //연관관계 편의 메서드
  public void setTarotCard(TarotCard tarotCard) {
    this.tarotCard = tarotCard;
  }

  //생성 편의 메서드
  public static CardReading createCardReading(CardReadingRequestDto dto) {
    CardReading cardReading = new CardReading();
    cardReading.description = dto.getDescription();
    cardReading.readingCategory = dto.getReadingCategory();
    cardReading.readingType = dto.getReadingType();
    cardReading.createdAt = LocalDateTime.now();
    cardReading.updatedAt = LocalDateTime.now();
    return cardReading;
  }


}
