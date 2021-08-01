package com.cos.jwt.jwt.business.book.api;

import com.cos.jwt.business.book.api.BookController;
import com.cos.jwt.business.book.application.BookService;
import com.cos.jwt.business.book.entity.Book;
import com.cos.jwt.business.book.form.BookForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;

/**
 *
 * given	객체를 생성 할 때 사용
 * when	  객체를 얻어 올 때 사용
 * then	  객체를 비교 할 때 사용
 * verify	확인 작업을 할 때 사용
 *
 * 참고 자료
 * - https://theheydaze.tistory.com/218
 */

@Slf4j
@SpringBootTest
public class BookControllerUnitTest {
  private MockMvc mockMvc;

  @MockBean
  private BookService bookService;


  Book book = new Book(1L, "제목", "저자", "설명");
  List<Book> books = Arrays.asList(
    new Book(1L, "제목", "저자", "설명"),
    new Book(2L, "제목2", "저자2", "설명2"),
    new Book(3L, "제목3", "저자3", "설명3"),
    new Book(4L, "제목4", "저자4", "설명4")
  );

  @BeforeEach
  void setUp(@Autowired BookController bookController) {
    // MockMvc
    mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
  }

  @Test
  @DisplayName("book_save_test")
  public void saveTest() throws Exception {
    Book book = Book.builder().title("제목").author("저자").shortContent("설명").build();
    String content = new ObjectMapper().writeValueAsString(book);

    ResultActions resultAction = mockMvc.perform(post("/book")
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .content(content)
      .accept(MediaType.APPLICATION_JSON_UTF8));

    resultAction
      .andExpect(status().isCreated())
      .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("book_findAll_test")
  public void getBooksTest() throws Exception {

    given(bookService.findAll()).willReturn(books);

    // when(테스트 실행)
    ResultActions resultAction = mockMvc.perform(get("/book")
      .accept(MediaType.APPLICATION_JSON_UTF8));

    // then (검증)
    resultAction
      .andExpect(status().isOk())
      .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("book_findById_test")
  public void getBookTest() throws Exception {
    Long id = 1L;

    given(bookService.findById(id)).willReturn(book);

    // when(테스트 실행)
    ResultActions resultAction = mockMvc.perform(get("/book/{id}", id)
      .accept(MediaType.APPLICATION_JSON_UTF8));

    // then (검증)
    resultAction
      .andExpect(status().isOk())
      .andDo(MockMvcResultHandlers.print());
  }


  @Test
  @DisplayName("book_updateById_test")
  public void updateBookTest() throws Exception {
    Long id = 1L;

    BookForm.Request.Modify modify = BookForm.Request.Modify.builder()
                                                            .title("제목2")
                                                            .author("저자")
                                                            .shortContent("설명2")
                                                            .build();

    String content = new ObjectMapper().writeValueAsString(modify);

    // when(테스트 실행)
    ResultActions resultAction = mockMvc.perform(patch("/book/{id}", id)
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .content(content)
      .accept(MediaType.APPLICATION_JSON_UTF8));

    // then (검증)
    resultAction
      .andExpect(status().isOk())
      .andDo(MockMvcResultHandlers.print());

    verify(bookService).updateByid(eq(1L), any());
  }

  @Test
  @DisplayName("book_deleteById_test")
  public void deleteBookTest() throws Exception {
    Long id = 1L;

    // when(테스트 실행)
    ResultActions resultAction = mockMvc.perform(delete("/book/{id}", id)
      .accept(MediaType.APPLICATION_JSON_UTF8));

    // then (검증)
    resultAction
      .andExpect(status().isOk())
      .andDo(MockMvcResultHandlers.print());

    verify(bookService).deleteById(eq(1L));
  }




}
