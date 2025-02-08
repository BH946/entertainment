package com.cafe24.entertainment.Repository;

import static org.junit.jupiter.api.Assertions.*;

import com.cafe24.entertainment.Entity.CardReading;
import com.cafe24.entertainment.Entity.ReadingCategory;
import com.cafe24.entertainment.Entity.ReadingType;
import com.cafe24.entertainment.Entity.TarotCard;
import com.cafe24.entertainment.Entity.dto.CardReadingRequestDto;
import com.cafe24.entertainment.Entity.dto.CardReadingResponseDto;
import com.cafe24.entertainment.Entity.dto.TarotCardRequestDto;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Order;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest //@Autowired !!
@Slf4j
class CardReadingRepositoryTest {
  @Autowired
  CardReadingRepository cardReadingRepository;
  @Autowired
  TarotCardRepository tarotCardRepository;
  @Autowired
  EntityManager em;

  /*
  save, findById
  List<CardReading> findByTarotCard(TarotCard tarotCard);
   */
  String description = "당신의 삶에 새로운 시작과 기회가 찾아오고 있습니다. 순수한 마음과 열린 태도로 모험을 시작하세요. 걱정과 두려움을 내려놓고 자유롭게 도전하면 무한한 가능성이 열릴 것입니다.";


  @Test
  @Order(1)
  @Transactional //동일한 영속성 컨텍스트 사용 + em 직접 쓸거면 당연히 필요
  @Rollback(value = false) //참고: 롤백하면 insert 쿼리문은 자동 생략 -> 롤백할거니까
  public void 타로리딩_저장_조회() throws Exception {
    // given
    CardReading cardReading = CardReading.createCardReading(
        new CardReadingRequestDto(
            0L, description, ReadingCategory.GENERAL, ReadingType.UPRIGHT
        ));
    TarotCard tarotCard = TarotCard.createTarotCard(new TarotCardRequestDto(
        "test", "테스트", 0L, null, "힐"
    ));
//    em.persist(tarotCard); //FK 위해
    tarotCardRepository.save(tarotCard);
    cardReading.setTarotCard(tarotCard);

    // when
    cardReadingRepository.save(cardReading); //persist
    // @Transactional의 자동롤백 때문에 insert 쿼리 생략! 이때, 강제 flush를 추가하여 insert 쿼리 볼 수 있음
    // flush 사용 후 영속성 비워줘야(clear) 아래 findOne() select 문 전송 쿼리도 볼 수 있음
//    em.flush();
//    em.clear();
    CardReading findCardReading = cardReadingRepository.findById(cardReading.getId()).orElse(null);

    // then
    Assertions.assertEquals(cardReading.getId(), findCardReading.getId());
    Assertions.assertEquals(cardReading, findCardReading);
    // 단, 위에서 em.clear를 한 경우 영속성이(캐시) 비어있으므로 findCardReading가 새로운 주소!!
    // 따라서 아래 출력으로 틀리다는 결론이 나온다.
  }

  @Test
  public void 타로리딩조회_카드() throws Exception {
    // given
    List<CardReading> readings;
    TarotCard tarotCard = tarotCardRepository.findByCardNumber(0L);

    // when
    readings = cardReadingRepository.findByTarotCard(tarotCard);

    // then
    for (CardReading reading : readings) {
      log.info("대사: {}", reading.getDescription());
    }
    Assertions.assertEquals(readings.get(0).getDescription(), description);
  }

}