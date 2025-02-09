package com.cafe24.entertainment.Controller;


import com.cafe24.entertainment.Entity.CardReading;
import com.cafe24.entertainment.Entity.ReadingCategory;
import com.cafe24.entertainment.Entity.ReadingType;
import com.cafe24.entertainment.Entity.TarotCard;
import com.cafe24.entertainment.Entity.dto.CardReadingRequestDto;
import com.cafe24.entertainment.Entity.dto.CardReadingResponseDto;
import com.cafe24.entertainment.Service.CardReadingService;
import com.cafe24.entertainment.Service.TarotCardService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/cardReading")
public class CardReadingController {

  private final CardReadingService cardReadingService;
  private final TarotCardService tarotCardService;

  @PostMapping
  public ResponseEntity<Long> save(@RequestBody @Valid CardReadingRequestDto dto) {
    TarotCard tarotCard = tarotCardService.findByCardNumber(dto.getCardNumber());
    CardReading cardReading = CardReading.createCardReading(dto);
    cardReading.setTarotCard(tarotCard); //FK 오류 방지 (순서 꼭)
    Long id = cardReadingService.save(cardReading);
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CardReadingResponseDto> findById(@PathVariable("id") Long id) {
    CardReading cardReading = cardReadingService.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("리딩을 찾을 수 없습니다."));
    CardReadingResponseDto res = new CardReadingResponseDto(cardReading);
    return ResponseEntity.status(HttpStatus.OK).body(res);
  }

  //오늘의 타로
  @GetMapping("/today/{cardNumber}")
  public ResponseEntity<ReadingRes> getTodayReading(@PathVariable("cardNumber") Long cardNumber) {
    TarotCard tarotCard = tarotCardService.findByCardNumber(cardNumber);
    CardReading reading = cardReadingService.getRandomReading(tarotCard);
    return ResponseEntity.ok(new ReadingRes(reading));
  }

  //카테고리별 타로 => 카테고리 별이니까 Yes or NO 도 당연히 포함
  @GetMapping("/category/{cardNumber}/{category}")
  public ResponseEntity<ReadingRes> getCategoryReading(@PathVariable("cardNumber") Long cardNumber,
      @PathVariable("category") String category) {
    TarotCard tarotCard = tarotCardService.findByCardNumber(cardNumber);
    CardReading reading = cardReadingService.getRandomReadingByCategory(tarotCard, category);
    return ResponseEntity.ok(new ReadingRes(reading));
  }

  @Getter
  @NoArgsConstructor
  static class ReadingRes {
    private String nameEn;
    private String nameKr;
    private Long cardNumber;
    private String imageUrl;
    private String keyword;
    private String description;
    private ReadingCategory readingCategory;
    private ReadingType readingType;

    ReadingRes(CardReading reading) {
      this.description = reading.getDescription();
      this.readingCategory = reading.getReadingCategory();
      this.readingType = reading.getReadingType();
      //Lazy 강제 초기화
      TarotCard tarotCard = reading.getTarotCard();
      this.nameEn = tarotCard.getNameEn();
      this.nameKr = tarotCard.getNameKr();
      this.cardNumber = tarotCard.getCardNumber();
      this.imageUrl = tarotCard.getImageUrl();
      this.keyword = tarotCard.getKeyword();
    }
  }
}
