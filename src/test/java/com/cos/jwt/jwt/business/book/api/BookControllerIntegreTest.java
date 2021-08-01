package com.cos.jwt.jwt.business.book.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.cos.jwt.business.book.application.BookService;
import com.cos.jwt.business.book.entity.Book;
import com.cos.jwt.business.book.form.BookForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AutoConfigureMockMvc
@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class BookControllerIntegreTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private BookService bookService;

  @Test
  @DisplayName("book_save_test")
  public void saveTest() throws Exception {
    Book book = Book.builder().title("제목").author("저자").shortContent("설명").build();
    String content = new ObjectMapper().writeValueAsString(book);

    // when(테스트 실행)
    ResultActions resultAction = mockMvc.perform(post("/book")
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .content(content)
      .accept(MediaType.APPLICATION_JSON_UTF8));

    // then (검증)
    resultAction
      .andExpect(status().isCreated())
      .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("book_findAll_test")
  public void getBooksTest() throws Exception {

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
  }

}
