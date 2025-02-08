package com.cafe24.entertainment.Service;

import com.cafe24.entertainment.Entity.CardReading;
import com.cafe24.entertainment.Entity.TarotCard;
import com.cafe24.entertainment.Entity.dto.CardReadingRequestDto;
import com.cafe24.entertainment.Repository.CardReadingRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CardReadingService {
  private final CardReadingRepository cardReadingRepository;

  @Transactional
  public Long save(CardReading cardReading) {
    cardReadingRepository.save(cardReading);
    return cardReading.getId();
  }

  public Optional<CardReading> findById(Long id) {
    return cardReadingRepository.findById(id);
  }

  public List<CardReading> findByTarotCard(TarotCard tarotCard) {
    return cardReadingRepository.findByTarotCard(tarotCard);
  }

}
