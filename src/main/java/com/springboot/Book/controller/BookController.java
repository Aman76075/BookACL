package com.springboot.Book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.Book.dto.ResponseMessageDto;
import com.springboot.Book.exception.ResourceNotFoundException;
import com.springboot.Book.model.Book;
import com.springboot.Book.service.BookService;

@RestController
public class BookController {
	@Autowired
	private BookService bookService;

	@PostMapping("/book/add")
	public Book addBook(@RequestBody Book book) {
		int id = (int) (Math.random() * 100000);
		book.setIsbn(id);
		return bookService.addBook(book);

	}

	@GetMapping("/book/getAll")
	public List<Book> findAllBooks() {
		return bookService.getAllBooks();
	}

	@DeleteMapping("/deleteBook/{isbn}")
	public ResponseEntity<?> deleteBook(@PathVariable int isbn, ResponseMessageDto dto) {
		Book book = null;
		try {
			book = bookService.validate(isbn);
			bookService.delete(isbn);
			dto.setMsg("BOOK DELETED SUCCESSFULLY");
			return ResponseEntity.ok(dto);
		} catch (ResourceNotFoundException e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
	}

	@GetMapping("/book/getByIsbn/{isbn}")
	public ResponseEntity<?> getByIsbn(@PathVariable int isbn, ResponseMessageDto dto) {
		Book book = null;
		try {
			book = bookService.validate(isbn);
			return ResponseEntity.ok(book);
		} catch (ResourceNotFoundException e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
	}

	@PutMapping("/book/update/{isbn}")
	public ResponseEntity<?> updateBook(@RequestBody Book newBook, ResponseMessageDto dto, @PathVariable int isbn) {
		Book book = null;
		try {
			book = bookService.validate(isbn);
			if (newBook.getAuthor() != null) {
				book.setAuthor(newBook.getAuthor());
			}
			if (newBook.getTitle() != null) {
				book.setTitle(newBook.getTitle());
			}
			if (newBook.getPublicationYear() != book.getPublicationYear()) {
				book.setPublicationYear(newBook.getPublicationYear());
			}
			book = bookService.addBook(book);
			return ResponseEntity.ok(book);
		} catch (ResourceNotFoundException e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
	}

}
