package com.springboot.mvc.security.login.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.springboot.mvc.security.login.model.Book;
import com.springboot.mvc.security.login.model.Role;
import com.springboot.mvc.security.login.model.Subject;
import com.springboot.mvc.security.login.model.User;
import com.springboot.mvc.security.login.service.BookServiceImpl;
import com.springboot.mvc.security.login.service.RoleService;
import com.springboot.mvc.security.login.service.SubjectServiceImpl;
import com.springboot.mvc.security.login.service.UserService;;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HomeController.class)
@AutoConfigureMockMvc(secure=false)
public class HomeControllerTest{
	

    @MockBean
    private UserService userService;
    @MockBean
    private BookServiceImpl bookService;
    @MockBean
    private SubjectServiceImpl subjectService;
    
    @MockBean
    private RoleService roleService;
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	private Role role;
	private User user;
	private Book book;
	private List<Book> books;

	private Subject subject;
	private List<Subject> subjects;

	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		role = new Role();
		role.setId(1);
		role.setRole("ADMIN");
		

        user = new User();
        user.setId(1);
        user.setActive(1);
        user.setName("testname");
        user.setLastName("testlname");
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRoles(new HashSet<Role>(Arrays.asList(role)));
        book = new Book();
        book.setBookId(1);
        book.setPrice(22);
        book.setTitle("TestBook");
        book.setVolume(1);
        
        bookService.addBook(book);
        books = new ArrayList<Book>();
        books.add(book);
        
        subject = new Subject();
        subject.setSubjectId(1);
        subject.setDurationInHours(200);
        subject.setSubtitle("TestSubject");
        subject.setReferences(books);
        subjectService.addSubject(subject);
        subjects = new ArrayList<Subject>();
        subjects.add(subject);

		Mockito.when(userService.findUserByUsername(any())).thenReturn(user);
		


	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void verifyHomePageLoad() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/home"))
		.andExpect(MockMvcResultMatchers.view().name("home"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(model().attribute("userName", "Welcome testname testlname"))
		.andDo(print());
	}
	
	@Test
	@WithMockUser(roles = "USER")
	public void verifySearchBookPageLoad() throws Exception{
		assertThat(this.bookService).isNotNull();
		Mockito.when(bookService.searchBookbyId(1)).thenReturn(book);
		mockMvc.perform(MockMvcRequestBuilders.get("/user/searchbook")
				.param("bookId", "1")
				.sessionAttr("book", new Book())
				)
		.andExpect(MockMvcResultMatchers.view().name("user/searchbook"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(model().attribute("book", hasProperty("title", equalTo("TestBook"))))
		.andExpect(model().attribute("book", hasProperty("price", equalTo(22.0))))
		.andDo(print());
	}
	
	@Test
	@WithMockUser(roles = "USER")
	public void verifySearchBookByTitlePageLoad() throws Exception{
		assertThat(this.bookService).isNotNull();
		Mockito.when(bookService.getBookByTitle("TestBook")).thenReturn(books);
		mockMvc.perform(MockMvcRequestBuilders.get("/user/searchbookbytitle")
				.param("title", "TestBook")
				.sessionAttr("books", new ArrayList<Book>())
				)
		.andExpect(MockMvcResultMatchers.view().name("user/searchbookbytitle"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(print());
	}
	
	@Test
	@WithMockUser(roles = "USER")
	public void verifyAddBookPageLoad() throws Exception{
		assertThat(this.bookService).isNotNull();
		mockMvc.perform(MockMvcRequestBuilders.get("/user/addbook")
				.param("title", "TestBook")
				.sessionAttr("book", new Book())
				)
		.andExpect(MockMvcResultMatchers.view().name("user/addbook"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(print());
	}
	
	@Test
	@WithMockUser(roles = "USER")
	public void verifyAddBookPagePost() throws Exception{
		assertThat(this.bookService).isNotNull();
		bookService.addBook(book);
		mockMvc.perform(MockMvcRequestBuilders.post("/user/addbook")
				.param("title", "TestBook")
				.sessionAttr("book", new Book())
				)
		.andExpect(MockMvcResultMatchers.view().name("user/addbook"))
		.andExpect(model().attribute("successMessage", "Book added successfully"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(print());
	}
	
	@Test
	@WithMockUser(roles = "USER")
	public void verifyDeleteBookPageLoad() throws Exception{
		assertThat(this.bookService).isNotNull();
		Mockito.when(bookService.deleteBookbyId(1)).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.get("/user/deletebook")
				.param("bookId", "1")
				.sessionAttr("book", new Book())
				)
		.andExpect(MockMvcResultMatchers.view().name("user/deletebook"))
		.andExpect(model().attribute("bookerror", "Book Deleted"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(print());
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void verifySearchSubjectPageLoad() throws Exception{
		assertThat(this.subjectService).isNotNull();
		Mockito.when(subjectService.searchSubjectbyId(1)).thenReturn(subject);
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/searchsubject")
				.param("subjectId", "1")
				.sessionAttr("subject", new Subject())
				)
		.andExpect(MockMvcResultMatchers.view().name("admin/searchsubject"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(model().attribute("subject", hasProperty("subtitle", equalTo("TestSubject"))))
		.andExpect(model().attribute("subject", hasProperty("durationInHours", equalTo(200))))
		.andDo(print());
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void verifySearchSubjectByDurationPageLoad() throws Exception{
		assertThat(this.subjectService).isNotNull();
		Mockito.when(subjectService.findSubjectByDuration(200)).thenReturn(subjects);
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/searchsubjectbyduration")
				.param("durationInHours", "200")
				.sessionAttr("subjects", new ArrayList<Subject>())
				)
		.andExpect(MockMvcResultMatchers.view().name("admin/searchsubjectbyduration"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(print());
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void verifyAddSubjectPageLoad() throws Exception{
		assertThat(this.subjectService).isNotNull();
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/addsubject")
				.param("subjectId", "1")
				.sessionAttr("subject", new Subject())
				)
		.andExpect(MockMvcResultMatchers.view().name("admin/addsubject"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(print());
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void verifyAddSubjectPagePost() throws Exception{
		assertThat(this.subjectService).isNotNull();
		subjectService.addSubject(subject);
		mockMvc.perform(MockMvcRequestBuilders.post("/admin/addsubject")
				.param("subjectId", "1")
				.param("subtitle", "TestSubject")
				.sessionAttr("subject", new Subject())
				)
		.andExpect(MockMvcResultMatchers.view().name("admin/addsubject"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(model().attribute("successMessage", "Subject Added"))
		.andDo(print());
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void verifyDeleteSubjectPageL() throws Exception{
		assertThat(this.subjectService).isNotNull();
		Mockito.when(subjectService.deleteSubjectbyId(1)).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/deletesubject")
				.param("subjectId", "1")
				.sessionAttr("subject", new Subject())
				)
		.andExpect(MockMvcResultMatchers.view().name("admin/deletesubject"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(model().attribute("suberror", "Subject Deleted"))
		.andDo(print());
	}
}
