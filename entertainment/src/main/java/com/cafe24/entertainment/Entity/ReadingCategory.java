package com.cafe24.entertainment.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.NotNull;

@Entity @Getter @Setter @Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadingCategory {

  @Id
  @GeneratedValue
  @Column(name = "reading_category_id")
  private Long id;
  @NotNull
  @Column(unique = true)
  private String categoryName;

  //생성 편의 메서드
  public static ReadingCategory createReadingCategory(String categoryName) {
    ReadingCategory readingCategory = new ReadingCategory();
    readingCategory.categoryName = categoryName;
    return readingCategory;
  }
}
