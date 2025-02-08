package com.cafe24.entertainment.Repository;

import com.cafe24.entertainment.Entity.CardReading;
import com.cafe24.entertainment.Entity.TarotCard;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 단순 ver -> 필요시 JPA+QueryDSL 추가 (=서비스 단 수정)
 */
public interface CardReadingRepository extends JpaRepository<CardReading, Long> {
  List<CardReading> findByTarotCard(TarotCard tarotCard);
}
