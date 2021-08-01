package com.cos.jwt.business.book.entity;

import com.cos.jwt.common.entity.BasicEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends BasicEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String author;
  private String shortContent;

    @Builder
  public Book(String title, String author, String shortContent) {
    this.title = title;
    this.author = author;
    this.shortContent = shortContent;
  }

  public void update(String title, String author, String shortContent) {
    this.title = title;
    this.author = author;
    this.shortContent = shortContent;
  }

}
