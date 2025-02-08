package com.cafe24.entertainment.Repository;

import com.cafe24.entertainment.Entity.TarotCard;
import com.cafe24.entertainment.Entity.dto.TarotCardRequestDto;
import com.cafe24.entertainment.Entity.dto.TarotCardResponseDto;
import jakarta.persistence.EntityManager;
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
@SpringBootTest
@Slf4j
class TarotCardRepositoryTest {

  @Autowired
  TarotCardRepository tarotCardRepository;
  @Autowired
  EntityManager em;

  /*
  save, findById
  TarotCard findByCardNumber(Long cardNumber);
  TarotCard findByNameEn(String nameEn);
  TarotCard findByNameKr(String nameKr);
   */
  String nameEn = "The Fool";
  String nameKr = "바보";
  Long number = 0L;
  String imageUrl = null;
  String keyword = "새로운 시작, 순수함, 자유로운 영혼, 모험";


  @Test
  @Order(1)
  @Transactional //동일한 영속성 컨텍스트 사용 + em 직접 쓸거면 당연히 필요
  @Rollback(value = false) //참고: 롤백하면 insert 쿼리문은 자동 생략 -> 롤백할거니까
  public void 타로카드_저장_조회() throws Exception {
    // given
    TarotCard tarotCard = TarotCard.createTarotCard(
        new TarotCardRequestDto(nameEn, nameKr, number, imageUrl, keyword));

    // when
    tarotCardRepository.save(tarotCard); //persist
    log.info("{}", tarotCard.getId());
    // @Transactional의 자동롤백 때문에 insert 쿼리 생략! 이때, 강제 flush를 추가하여 insert 쿼리 볼 수 있음
    // flush 사용 후 영속성 비워줘야(clear) 아래 findOne() select 문 전송 쿼리도 볼 수 있음
//    em.flush();
//    em.clear();
    TarotCard findTarotCard = tarotCardRepository.findById(tarotCard.getId()).orElse(null);
    log.info("{}", tarotCard.getId());

    // then
    Assertions.assertEquals(tarotCard.getId(), findTarotCard.getId());
    Assertions.assertEquals(tarotCard, findTarotCard);
    // 단, 위에서 em.clear를 한 경우 영속성이(캐시) 비어있으므로 findTarotCard가 새로운 주소!!
    // 따라서 아래 출력으로 틀리다는 결론이 나온다.
  }

  @Test
  public void 카드조회_카드번호() throws Exception {
    // given
    TarotCard findTarotCard;

    // when
    findTarotCard = tarotCardRepository.findByCardNumber(0L);
    TarotCard findTarotCard2 = tarotCardRepository.findByCardNumber(1L);

    // then
    Assertions.assertEquals(findTarotCard.getNameEn(), "The Fool");
    Assertions.assertEquals(findTarotCard2, null);
  }

  @Test
  public void 카드조회_카드이름() throws Exception {
    // given
    TarotCard findTarotCard;

    // when
    findTarotCard = tarotCardRepository.findByNameEn("The Fool");
    TarotCard findTarotCard2 = tarotCardRepository.findByNameKr("바보");

    // then
    Assertions.assertEquals(findTarotCard.getNameEn(), "The Fool");
    Assertions.assertEquals(findTarotCard.getNameKr(), "바보");
    Assertions.assertNotEquals(findTarotCard, findTarotCard2);
  }
}