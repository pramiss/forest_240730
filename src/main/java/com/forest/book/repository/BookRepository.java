package com.forest.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forest.book.entity.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, String> {

}
