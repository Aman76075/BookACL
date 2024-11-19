package com.springboot.Book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.Book.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
