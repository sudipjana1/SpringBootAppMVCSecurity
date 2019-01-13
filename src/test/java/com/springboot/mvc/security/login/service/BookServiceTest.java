package com.springboot.mvc.security.login.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.springboot.mvc.security.login.LoginApplicationTests;
import com.springboot.mvc.security.login.model.Book;
import com.springboot.mvc.security.login.repository.BookRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import java.util.List;
import java.util.Optional;

public class BookServiceTest extends LoginApplicationTests{
	
	@InjectMocks
    private BookServiceImpl bookServiceUnderTest;
	@Mock 
	private BookRepository bookdao;
    private Book book;
    Optional<Book> bookOpt;
	private long bookId = 1;


    @Before
    public void setUp() {
		MockitoAnnotations.initMocks(this);

        book = new Book();
        book.setTitle("TestBook");
        book.setBookId(bookId);
        book.setBookId(1);
        bookOpt = Optional.of(book);
        
        Mockito.when(bookdao.save(any()))
        .thenReturn(book);
        Mockito.when(bookdao.findById(any()))
        .thenReturn(bookOpt);
        
       

        bookServiceUnderTest.addBook(book);
        //bookId = bookServiceUnderTest.getBookByTitle("TestBook").get(0).getBookId();

    }

    @Test
    public void testSearchBookbyId() {
        // Run the test
        final Book result = bookServiceUnderTest.searchBookbyId(bookId);
        // Verify the results
        assertEquals(1,result.getBookId());
    }
    
    @Test
    public void testGetBookByTitle() {
        // Setup
        final String title = "TestBook";
        // Run the test
        final List<Book> result = bookServiceUnderTest.getBookByTitle(title);
        // Verify the results
        assertTrue(result != null);
    }
    
    @Test
    public void testGetAllBooks() {
    		final List<Book> books = bookServiceUnderTest.getAllBooks();
    		assertTrue(books != null);
    }
    
    @Test
    public void testDeleteBookbyId() {
		//bookServiceUnderTest.addBook(book);

    		assertTrue(bookServiceUnderTest.deleteBookbyId(bookId));
    }


}
