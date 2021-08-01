package com.cos.jwt.business.book.application;

import com.cos.jwt.business.book.entity.Book;
import com.cos.jwt.business.book.form.BookForm.*;
import com.cos.jwt.common.error.ErrorCode;
import com.cos.jwt.common.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {
  private final BookRepository bookRepository;

  @Transactional
  public void save(Book book){
    bookRepository.save(book);
  }

  public Book findById(Long id){
    return bookRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOOK));
  }

  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  @Transactional
  public void updateByid(Long id, Request.Modify modify){
    Book bookEntity = bookRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOOK));
    bookEntity.update(modify.getTitle(), modify.getAuthor(), modify.getShortContent());
  }

  @Transactional
  public void deleteById(Long id){
    bookRepository.deleteById(id);
  }

}
