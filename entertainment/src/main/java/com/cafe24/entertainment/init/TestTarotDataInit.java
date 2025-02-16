package com.cafe24.entertainment.init;

import com.cafe24.entertainment.Entity.CardReading;
import com.cafe24.entertainment.Entity.ReadingCategory;
import com.cafe24.entertainment.Entity.ReadingType;
import com.cafe24.entertainment.Entity.TarotCard;
import com.cafe24.entertainment.Entity.dto.CardReadingRequestDto;
import com.cafe24.entertainment.Entity.dto.TarotCardRequestDto;
import com.cafe24.entertainment.Repository.CardReadingRepository;
import com.cafe24.entertainment.Repository.TarotCardRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 안 사용할 거면 스프링 빈 등록 안되게끔(스캔X) @Component 주석 ㄱㄱ + @PostConstruct 주석도 마찬가지
 * 이건 로컬에서 개발 테스트용임. 필요한 db데이터는 backup.sql 로 넣어랏.
 * */

@Slf4j
//@Component //빈 등록
@RequiredArgsConstructor //생성자 주입
public class TestTarotDataInit {
  private final InitService initService;

  // 해당 클래스 인스턴스 생성(construct)된 후 자동 실행
//  @PostConstruct
  public void init() {
    initService.initTarot();
  }

  @Service
  @RequiredArgsConstructor
  @Transactional
  public static class InitService {
    private final TarotCardRepository tarotCardRepository;
    private final CardReadingRepository cardReadingRepository;
    private final ObjectMapper objectMapper;

    public void initTarot() {
      try {
        // JSON 파일 읽기
        ClassPathResource dailyResource = new ClassPathResource("tarot_daily.json"); //resource 경로 사용
        ClassPathResource yesnoResource = new ClassPathResource("tarot_yesno.json");
        JsonNode dailyJson = objectMapper.readTree(dailyResource.getFile());
        JsonNode yesnoJson = objectMapper.readTree(yesnoResource.getFile());

        // TarotCard 데이터 저장 => 카드는 1번만 넣으면 됨. daily,yesno 통틀어서
        for (int i = 1; i <= 1; i++) {
          String dailyKey = "daily" + i;
          JsonNode dailyCards = dailyJson.get("daily").get(i-1).get(dailyKey);
          for (JsonNode card : dailyCards) {
            TarotCardRequestDto dto = new TarotCardRequestDto(
                card.get("name_en").asText(), card.get("name_kr").asText(), card.get("number").asLong(),
                card.get("image_url").asText(), card.get("keyword").asText()
            );
            tarotCardRepository.save(TarotCard.createTarotCard(dto));
          }
        }
        // CardReading 데이터 저장
        for (int i = 1; i <= 3; i++) {
          String dailyKey = "daily" + i;
          JsonNode dailyCards = dailyJson.get("daily").get(i - 1).get(dailyKey);
          for (JsonNode card : dailyCards) {
            //upright
            CardReadingRequestDto dtoG = new CardReadingRequestDto(
                card.get("number").asLong(), card.get("upright").get("general").asText(), ReadingCategory.GENERAL, ReadingType.UPRIGHT
            );
            CardReadingRequestDto dtoH = new CardReadingRequestDto(
                card.get("number").asLong(), card.get("upright").get("health").asText(), ReadingCategory.HEALTH, ReadingType.UPRIGHT
            );
            CardReadingRequestDto dtoC = new CardReadingRequestDto(
                card.get("number").asLong(), card.get("upright").get("career").asText(), ReadingCategory.CAREER, ReadingType.UPRIGHT
            );
            CardReadingRequestDto dtoW = new CardReadingRequestDto(
                card.get("number").asLong(), card.get("upright").get("wealth").asText(), ReadingCategory.WEALTH, ReadingType.UPRIGHT
            );
            CardReadingRequestDto dtoL = new CardReadingRequestDto(
                card.get("number").asLong(), card.get("upright").get("love").asText(), ReadingCategory.LOVE, ReadingType.UPRIGHT
            );
            log.info("tarotCardNumber: {}", dtoG.getCardNumber());
            TarotCard tarotCard = tarotCardRepository.findByCardNumber(dtoG.getCardNumber());
            log.info("tarotCard: {}", tarotCard);
            CardReading cardReadingG = CardReading.createCardReading(dtoG);
            cardReadingG.setTarotCard(tarotCard);
            cardReadingRepository.save(cardReadingG);
            CardReading cardReadingH = CardReading.createCardReading(dtoH);
            cardReadingH.setTarotCard(tarotCard);
            cardReadingRepository.save(cardReadingH);
            CardReading cardReadingC = CardReading.createCardReading(dtoC);
            cardReadingC.setTarotCard(tarotCard);
            cardReadingRepository.save(cardReadingC);
            CardReading cardReadingW = CardReading.createCardReading(dtoW);
            cardReadingW.setTarotCard(tarotCard);
            cardReadingRepository.save(cardReadingW);
            CardReading cardReadingL = CardReading.createCardReading(dtoL);
            cardReadingL.setTarotCard(tarotCard);
            cardReadingRepository.save(cardReadingL);
            //reversed
            dtoG = new CardReadingRequestDto(
                card.get("number").asLong(), card.get("reversed").get("general").asText(), ReadingCategory.GENERAL, ReadingType.REVERSED
            );
            dtoH = new CardReadingRequestDto(
                card.get("number").asLong(), card.get("reversed").get("health").asText(), ReadingCategory.HEALTH, ReadingType.REVERSED
            );
            dtoC = new CardReadingRequestDto(
                card.get("number").asLong(), card.get("reversed").get("career").asText(), ReadingCategory.CAREER, ReadingType.REVERSED
            );
            dtoW = new CardReadingRequestDto(
                card.get("number").asLong(), card.get("reversed").get("wealth").asText(), ReadingCategory.WEALTH, ReadingType.REVERSED
            );
            dtoL = new CardReadingRequestDto(
                card.get("number").asLong(), card.get("reversed").get("love").asText(), ReadingCategory.LOVE, ReadingType.REVERSED
            );
            tarotCard = tarotCardRepository.findByCardNumber(dtoG.getCardNumber());
            cardReadingG = CardReading.createCardReading(dtoG);
            cardReadingG.setTarotCard(tarotCard);
            cardReadingRepository.save(cardReadingG);
            cardReadingH = CardReading.createCardReading(dtoH);
            cardReadingH.setTarotCard(tarotCard);
            cardReadingRepository.save(cardReadingH);
            cardReadingC = CardReading.createCardReading(dtoC);
            cardReadingC.setTarotCard(tarotCard);
            cardReadingRepository.save(cardReadingC);
            cardReadingW = CardReading.createCardReading(dtoW);
            cardReadingW.setTarotCard(tarotCard);
            cardReadingRepository.save(cardReadingW);
            cardReadingL = CardReading.createCardReading(dtoL);
            cardReadingL.setTarotCard(tarotCard);
            cardReadingRepository.save(cardReadingL);
          }
        }
        for (int i = 1; i <= 1; i++) {
          String YesNoKey = "YesNo" + i;
          JsonNode YesNoCards = yesnoJson.get("YesNo").get(i - 1).get(YesNoKey);
          for (JsonNode card : YesNoCards) {
            //upright (yes)
            CardReadingRequestDto dtoG = new CardReadingRequestDto(
                card.get("number").asLong(), card.get("upright").get("yesno").asText(), ReadingCategory.YESNO, ReadingType.UPRIGHT
            );
            TarotCard tarotCard = tarotCardRepository.findByCardNumber(dtoG.getCardNumber());
            CardReading cardReadingG = CardReading.createCardReading(dtoG);
            cardReadingG.setTarotCard(tarotCard);
            cardReadingRepository.save(cardReadingG);
            //reversed (no)
            dtoG = new CardReadingRequestDto(
                card.get("number").asLong(), card.get("reversed").get("yesno").asText(), ReadingCategory.YESNO, ReadingType.REVERSED
            );
            tarotCard = tarotCardRepository.findByCardNumber(dtoG.getCardNumber());
            cardReadingG = CardReading.createCardReading(dtoG);
            cardReadingG.setTarotCard(tarotCard);
            cardReadingRepository.save(cardReadingG);
          }
        }
      } catch (Exception e) {
        throw new RuntimeException("데이터 초기화 실패", e);
      }
    }

  }
}
