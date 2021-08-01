package com.cos.jwt.business.book.form;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BookForm {

  public static class Request{

    @Getter
    @NoArgsConstructor
    public static class Modify{
      private String title;
      private String shortContent;
      private String author;

      @Builder
      public Modify(String title, String shortContent, String author) {
        this.title = title;
        this.shortContent = shortContent;
        this.author = author;
      }
    }
  }
}
