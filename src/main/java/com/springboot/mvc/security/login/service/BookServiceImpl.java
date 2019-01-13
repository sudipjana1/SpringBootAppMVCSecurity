package com.springboot.mvc.security.login.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springboot.mvc.security.login.model.Book;
import com.springboot.mvc.security.login.repository.BookRepository;


@Component
public class BookServiceImpl implements BookService{
	@Autowired
	BookRepository bookdao;
	
	@Override
	public void addBook(Book book) {
		bookdao.save(book);
	}
	
	@Override
	public Book searchBookbyId(long bookId) {
		Optional<Book> book = null;
		book = bookdao.findById(bookId);
		if(book.isPresent()) {
			return book.get();
		}else
			return null;
	}
	
	@Override
	public boolean deleteBookbyId(long bookId) {
		//bookdao.deleteBookById(bookId);
		if( !bookdao.findById(bookId).isPresent()) {
			return false;
		}else {
			bookdao.deleteById(bookId);
			return true;
		}
		
	}

	@Override
	public List<Book> getAllBooks() {
		 List<Book>listbook = bookdao.findAll();
		return listbook;
	}


	@Override
	public List<Book> getBookByTitle(String title) {
		List<Book> listbook = bookdao.findByBookTitle(title);
		System.out.println(listbook.toString());
		
		return listbook;
	}
	
}
