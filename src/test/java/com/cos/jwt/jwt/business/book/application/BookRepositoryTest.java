package com.cos.jwt.jwt.business.book.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cos.jwt.business.book.application.BookRepository;
import com.cos.jwt.business.book.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@DataJpaTest
@Transactional
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

  @Autowired
  private BookRepository bookRepository;

  @Test
  public void save(){
    Book book = Book.builder().title("제목").author("저자").shortContent("설명").build();

    Book entity = bookRepository.save(book);

    assertEquals(book.getTitle(), entity.getTitle());
  }

  @Test
  public void findById(){
    Long id = 1l;

    Book entity = bookRepository.findById(id).get();

    assertEquals(id, entity.getId());
  }




}
