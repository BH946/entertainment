package com.cafe24.entertainment.Controller;

import com.cafe24.entertainment.Entity.TarotCard;
import com.cafe24.entertainment.Entity.dto.TarotCardRequestDto;
import com.cafe24.entertainment.Service.TarotCardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/tarotCard")
@RequiredArgsConstructor
public class TarotCardController {
  private final TarotCardService tarotCardService;

  @PostMapping
  public ResponseEntity<Long> save(@RequestBody @Valid TarotCardRequestDto dto) {
    TarotCard tarotCard = TarotCard.createTarotCard(dto);
    Long id = tarotCardService.save(tarotCard);
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TarotCard> findById(@PathVariable("id") Long id) {
    return ResponseEntity.status(HttpStatus.OK).body(tarotCardService.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("카드를 찾을 수 없습니다.")));
  }

  @GetMapping("/number/{cardNumber}")
  public ResponseEntity<TarotCard> findByCardNumber(@PathVariable("cardNumber") Long cardNumber) {
    return ResponseEntity.ok(tarotCardService.findByCardNumber(cardNumber));
  }

  @GetMapping("/nameEn/{name}")
  public ResponseEntity<TarotCard> findByNameEn(@PathVariable("name") String name) {
    return ResponseEntity.ok(tarotCardService.findByNameEn(name));
  }

  @GetMapping("/nameKr/{name}")
  public ResponseEntity<TarotCard> findByNameKr(@PathVariable("name") String name) {
    return ResponseEntity.ok(tarotCardService.findByNameKr(name));
  }
}
