package com.cafe24.entertainment.Service;

import com.cafe24.entertainment.Entity.TarotCard;
import com.cafe24.entertainment.Entity.dto.TarotCardRequestDto;
import com.cafe24.entertainment.Entity.dto.TarotCardResponseDto;
import com.cafe24.entertainment.Repository.TarotCardRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TarotCardService {
  private final TarotCardRepository tarotCardRepository;

  @Transactional
  public Long save(TarotCard tarotCard) {
    tarotCardRepository.save(tarotCard);
    return tarotCard.getId();
  }

  //기본 데이터 타입은 Optional<> 임
  public Optional<TarotCard> findById(Long cardId) {
    Optional<TarotCard> findCard = tarotCardRepository.findById(cardId);
    return findCard;
  }

  public TarotCard findByCardNumber(Long cardNumber) {
    TarotCard findCard = tarotCardRepository.findByCardNumber(cardNumber);
    return findCard;
  }

  public TarotCard findByNameEn(String name) {
    TarotCard findCard = tarotCardRepository.findByNameEn(name);
    return findCard;
  }

  public TarotCard findByNameKr(String name) {
    TarotCard findCard = tarotCardRepository.findByNameKr(name);
    return findCard;
  }
}
