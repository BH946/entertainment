package com.cafe24.entertainment.Service;

import com.cafe24.entertainment.Entity.CardReading;
import com.cafe24.entertainment.Entity.ReadingCategory;
import com.cafe24.entertainment.Entity.TarotCard;
import com.cafe24.entertainment.Entity.dto.CardReadingRequestDto;
import com.cafe24.entertainment.Repository.CardReadingRepository;
import java.util.List;
import java.util.Optional;
import java.util.Random;
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

  //아래 두 함수가 실제 기능
  public CardReading getRandomReading(TarotCard tarotCard) {
    List<CardReading> readings = cardReadingRepository.findByTarotCardAndReadingCategoryNot(tarotCard, ReadingCategory.YESNO);
    return getRandomElement(readings);

  }

  public CardReading getRandomReadingByCategory(TarotCard tarotCard, String category) {
    ReadingCategory readingCategory = ReadingCategory.valueOf(category.toUpperCase()); //Enum 상수 보통 대문자니까
    List<CardReading> readings = cardReadingRepository.findByTarotCardAndReadingCategory(tarotCard, readingCategory);
    return getRandomElement(readings);
  }


  private CardReading getRandomElement(List<CardReading> readings) {
    if (readings.isEmpty()) {
      throw new IllegalStateException("No readings found");
    }
    int idx = new Random().nextInt(readings.size());
    return readings.get(idx);
  }

}
