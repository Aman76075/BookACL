package com.springboot.Book.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.Book.exception.ResourceNotFoundException;
import com.springboot.Book.model.Book;
import com.springboot.Book.repository.BookRepository;

@Service
public class BookService {
	@Autowired
	private BookRepository bookRepository;

	public Book addBook(Book book) {
		return bookRepository.save(book);
	}

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	public Book delete(long isbn) {
		// TODO Auto-generated method stub
		return null;
	}

	public Book validate(int isbn) throws ResourceNotFoundException {
		Optional<Book> optional = bookRepository.findById(isbn);

		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("This book ISBN does not exist");
		}
		return optional.get();
	}

}
