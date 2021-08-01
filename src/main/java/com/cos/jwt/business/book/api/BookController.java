package com.cos.jwt.business.book.api;

import com.cos.jwt.business.book.application.BookService;
import com.cos.jwt.business.book.entity.Book;
import com.cos.jwt.business.book.form.BookForm.*;
import com.cos.jwt.common.response.ApiResponse;
import com.cos.jwt.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
  private final BookService bookService;

  @PostMapping("")
  public ResponseEntity<ResponseDto> save(@RequestBody Book book) {
    bookService.save(book);
    return ApiResponse.set(new ResponseDto(HttpStatus.CREATED.value(), "정상적으로 처리되었습니다."));
  }

  @GetMapping("")
  public ResponseEntity<ResponseDto> getBooks(){
    return ApiResponse.set(new ResponseDto(HttpStatus.OK.value(), "", bookService.findAll()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto> getBook(@PathVariable Long id){
    return ApiResponse.set(new ResponseDto(HttpStatus.OK.value(), "", bookService.findById(id)));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ResponseDto> updateBook(@PathVariable Long id, @RequestBody Request.Modify modify){
    bookService.updateByid(id, modify);
    return ApiResponse.set(new ResponseDto(HttpStatus.OK.value(), "정상적으로 처리되었습니다."));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDto> deleteBook(@PathVariable Long id){
    bookService.deleteById(id);
    return ApiResponse.set(new ResponseDto(HttpStatus.OK.value(), "정상적으로 처리되었습니다."));
  }
}
