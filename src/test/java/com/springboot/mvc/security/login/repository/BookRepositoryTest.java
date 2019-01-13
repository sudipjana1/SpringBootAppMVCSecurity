package com.springboot.mvc.security.login.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;


import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.springboot.mvc.security.login.LoginApplicationTests;
import com.springboot.mvc.security.login.model.Book;


@RunWith(SpringRunner.class)
@SpringBootTest

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class})
@DatabaseSetup("/bookData.xml")
public class BookRepositoryTest extends LoginApplicationTests{
	
	@Autowired 
	private BookRepository bookdao;
	
	@Test
	public void testFindAll() throws Exception{
	
		List<Book> fbook = bookdao.findAll();
        assertThat(fbook.size(), is(2));
	}
	
	@Test
	public void testFindById() throws Exception{
	
		Optional<Book> fbook = bookdao.findById(2L);
		Book book = fbook.get();
        assertThat(book.getBookId(), is(2L));
	}
	
	@Test
	public void testFindByTitle() throws Exception{
	
		List<Book> fbook = bookdao.findByBookTitle("TestBook1");
        assertThat(fbook.size(), is(1));
	}

}
