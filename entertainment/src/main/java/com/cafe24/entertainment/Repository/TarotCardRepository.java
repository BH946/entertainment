package com.cafe24.entertainment.Repository;

import com.cafe24.entertainment.Entity.TarotCard;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarotCardRepository extends JpaRepository<TarotCard, Long> {
  TarotCard findByCardNumber(Long cardNumber);
  TarotCard findByNameEn(String nameEn);
  TarotCard findByNameKr(String nameKr);
}
