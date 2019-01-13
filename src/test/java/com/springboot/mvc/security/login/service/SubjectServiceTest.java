package com.springboot.mvc.security.login.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.springboot.mvc.security.login.LoginApplicationTests;
import com.springboot.mvc.security.login.model.Book;
import com.springboot.mvc.security.login.model.Subject;
import com.springboot.mvc.security.login.repository.SubjectRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubjectServiceTest extends LoginApplicationTests{
	
	@InjectMocks
    private SubjectServiceImpl subjectServiceUnderTest;
	@Mock
	private 	SubjectRepository subjectdao;

    private Book book;
    private Subject subject;
    private List<Book> books;
    private long subjectId;
    Optional<Subject> subjectopt;



    @Before
    public void setUp() {
        book = new Book();
        book.setTitle("TestBook1");
        book.setBookId(1);
		books = new ArrayList<Book>();
		books.add(book);
		subject = new Subject();
		subject.setSubjectId(1);
		subject.setSubtitle("TestSubject");
		subject.setDurationInHours(999);
		subject.setReferences(books);
		subjectopt = Optional.of(subject);
		Mockito.when(subjectdao.save(any()))
		.thenReturn(subject);
		Mockito.when(subjectdao.findById(any()))
		.thenReturn(subjectopt);
		
		subjectServiceUnderTest.addSubject(subject);

    }

    @Test
    public void testSearchSubjectbyId() {
        // Run the test
        final Subject result = subjectServiceUnderTest.searchSubjectbyId(subjectId);
        // Verify the results
        assertEquals(1,result.getSubjectId());
    }
    
    @Test
    public void testGetSubjectByDuration() {
        // Setup
        final int durationInHours = 999;
        // Run the test
        final List<Subject> result = subjectServiceUnderTest.findSubjectByDuration(durationInHours);
        // Verify the results
        assertTrue(result != null);

       // assertEquals(durationInHours, result.iterator().next().getDurationInHours());
    }
    
    @Test
    public void testGetAllSubjects() {
    		final List<Subject> subjects = subjectServiceUnderTest.getAllSubjects();
    		assertTrue(subjects != null);
    }
    
    @Test
    public void testDeleteSubjectbyId() {
    		assertTrue(subjectServiceUnderTest.deleteSubjectbyId(subjectId));
    }


}
