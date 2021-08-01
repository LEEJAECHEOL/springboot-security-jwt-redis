package com.cos.jwt.jwt.business.book.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cos.jwt.business.book.application.BookRepository;
import com.cos.jwt.business.book.application.BookService;
import com.cos.jwt.business.book.entity.Book;
import com.cos.jwt.business.book.form.BookForm.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.*;

/**
 *
 * given	객체를 생성 할 때 사용
 * when	  객체를 얻어 올 때 사용
 * then	  객체를 비교 할 때 사용
 * verify	확인 작업을 할 때 사용
 *
 */


@Slf4j
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

  @InjectMocks
  private BookService bookService;

  @Mock
  private BookRepository bookRepository;


  @Test
  @DisplayName("booService_save_test")
  public void saveTest() {
    // given
    Book book = Book.builder().title("제목").author("저자").shortContent("설명").build();

    given(bookRepository.save(any())).willReturn(book);

    bookService.save(book);

    verify(bookRepository).save(book);

    assertThat(book.getTitle()).isEqualTo("제목");
    assertThat(book.getAuthor()).isEqualTo("저자");
  }

  @Test
  @DisplayName("booService_findById_test")
  public void getBookTest() {
    Book book = new Book(1L, "제목", "저자", "설명");

    given(bookRepository.findById(anyLong()))
      .willReturn(Optional.of(book));

    Book bookEntity = bookService.findById(1L);

    verify(bookRepository).findById(eq(1L));

    assertEquals(1L, bookEntity.getId());
    assertEquals("제목", bookEntity.getTitle());
  }

  @Test
  @DisplayName("booService_findAll_test")
  public void getBooksTest() {
    // given
    List<Book> books = Arrays.asList(
      new Book(1L, "제목", "저자", "설명"),
      new Book(2L, "제목2", "저자2", "설명2"),
      new Book(3L, "제목3", "저자3", "설명3"),
      new Book(4L, "제목4", "저자4", "설명4")
    );

    given(bookRepository.findAll())
      .willReturn(books);

    List<Book> booksEntity = bookService.findAll();

    verify(bookRepository).findAll();

    assertEquals(books.size(), booksEntity.size());
  }

  @Test
  @DisplayName("booService_updateById_test")
  public void updateBookTest() {
    // given
    Book book = new Book(1L, "제목", "저자", "설명");
    Request.Modify modify = Request.Modify.builder().title("제목2").author("저자").shortContent("설명2").build();

    given(bookRepository.findById(1L))
      .willReturn(Optional.of(book));

    bookService.updateByid(1L, modify);

    verify(bookRepository).findById(eq(1L));

    assertThat(book.getTitle()).isEqualTo("제목2");
  }


  @Test
  @DisplayName("booService_delete_test")
  public void deleteTest() {
    bookService.deleteById(1L);

    verify(bookRepository).deleteById(eq(1L));
  }

}
