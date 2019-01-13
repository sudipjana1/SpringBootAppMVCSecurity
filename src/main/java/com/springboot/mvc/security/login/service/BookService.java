package com.springboot.mvc.security.login.service;



import java.util.List;

import com.springboot.mvc.security.login.model.Book;


public interface BookService {

	public void addBook(Book book);
	public Book searchBookbyId(long bookId);
	public boolean deleteBookbyId(long bookId) ;
	public List<Book> getAllBooks();
	public List<Book> getBookByTitle(String title);
}
