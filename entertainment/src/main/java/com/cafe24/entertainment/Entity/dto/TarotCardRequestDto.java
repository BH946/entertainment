package com.cafe24.entertainment.Entity.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor //잭슨 직렬화엔 필요. 근데 @Valid 사용하면 기본생성자 있어도 문제없다.
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
