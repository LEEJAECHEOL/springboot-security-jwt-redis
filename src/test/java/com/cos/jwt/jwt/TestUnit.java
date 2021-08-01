package com.cos.jwt.jwt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestUnit {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void test() throws Exception {

    ResultActions resultAction = mockMvc.perform(get("/admin")
      .accept(MediaType.APPLICATION_JSON_UTF8));
    // then
    resultAction.andExpect(status().isOk())
      .andDo(MockMvcResultHandlers.print());
  }




}
