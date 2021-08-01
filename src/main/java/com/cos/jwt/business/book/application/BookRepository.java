package com.cos.jwt.business.book.application;

import com.cos.jwt.business.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
