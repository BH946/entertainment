package com.cafe24.entertainment.Service;

import static org.junit.jupiter.api.Assertions.*;

import com.cafe24.entertainment.Entity.TarotCard;
import com.cafe24.entertainment.Entity.dto.TarotCardRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest //@Autowired
@Slf4j
class TarotCardServiceTest {

  @Autowired
  TarotCardService tarotCardService;
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
  @Rollback(value = false)
  @Transactional //써야지 동일한 영속성 컨텍스트 범위!
  public void 타로카드_저장_조회() throws Exception {
    // given
    TarotCard tarotCard = TarotCard.createTarotCard(new TarotCardRequestDto(
        nameEn, nameKr, number, imageUrl, keyword
    ));

    // when
    Long id = tarotCardService.save(tarotCard);
    TarotCard findTarotCard = tarotCardService.findById(id).orElse(null);

    // then
    Assertions.assertEquals(id, findTarotCard.getId());
    Assertions.assertEquals(tarotCard, findTarotCard); //@Transactional 없으면 이 부분 객체 주소 다름
  }

  @Test
  @Order(2)
  public void 타로카드조회_번호_이름() throws Exception {
    // given

    // when
    TarotCard findTarotCard = tarotCardService.findByCardNumber(number);
    TarotCard findTarotCard2 = tarotCardService.findByNameEn("The Fool");
    TarotCard findTarotCard3 = tarotCardService.findByNameKr("바보");

    // then
    Assertions.assertEquals(findTarotCard.getCardNumber(), number);
    Assertions.assertEquals(findTarotCard2.getNameEn(), nameEn);
    Assertions.assertEquals(findTarotCard3.getNameKr(), nameKr);
  }
}