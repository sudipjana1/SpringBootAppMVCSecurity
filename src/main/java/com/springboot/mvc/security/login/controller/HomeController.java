package com.springboot.mvc.security.login.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.springboot.mvc.security.login.model.Book;
import com.springboot.mvc.security.login.model.Subject;
import com.springboot.mvc.security.login.model.User;
import com.springboot.mvc.security.login.service.BookService;
import com.springboot.mvc.security.login.service.SubjectService;
import com.springboot.mvc.security.login.service.UserService;





/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired BookService bookService;
	@Autowired SubjectService subjectService;
	@Autowired
	UserService userService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));

	}


	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByUsername(auth.getName());
		model.addAttribute("userName", "Welcome " + user.getName() + " " + user.getLastName());
		return "home";
	}

	@RequestMapping(value = "/user/searchbook", method = RequestMethod.GET)
	public String searchBook(@ModelAttribute Book book,Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		if(book.getBookId() != 0){
			Book searchbook = new Book();
			searchbook = bookService.searchBookbyId(book.getBookId());
			if (searchbook == null) {
				model.addAttribute("bookerror", "No Book with this ID" );
			}else {
				model.addAttribute("book", searchbook );
			}
		}
		model.addAttribute("serverTime", formattedDate );
		return "user/searchbook";
	}

	@RequestMapping(value = "/user/searchbookbytitle", method = RequestMethod.GET)
	public String searchBookByTitle(@ModelAttribute Book book,Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		if(book.getTitle() != null){
			List<Book> searchbook = bookService.getBookByTitle(book.getTitle());
			if (searchbook.isEmpty()) {
				model.addAttribute("bookerror", "No Book with this Title" );
			}else {
				model.addAttribute("books", searchbook );
			}
		}
		model.addAttribute("serverTime", formattedDate );
		return "user/searchbookbytitle";
	}

	@RequestMapping(value = "/user/deletebook", method = RequestMethod.GET)
	public String deleteBook(@ModelAttribute Book book,Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		if(book.getBookId() != 0){
			if (!bookService.deleteBookbyId(book.getBookId())) {
				model.addAttribute("bookerror", "No Book with this ID" );
			}else {
				model.addAttribute("bookerror", "Book Deleted" );
			}
		}
		model.addAttribute("serverTime", formattedDate );
		return "user/deletebook";
	}



	@RequestMapping(value = "/user/addbook", method = RequestMethod.POST)
	public String saveBook(@ModelAttribute Book book, Model model, Locale locale) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		System.out.println(book.toString());
		bookService.addBook(book);
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("successMessage", "Book added successfully");
		return "user/addbook";
	}

	@RequestMapping(value="/user/addbook")
	public String addBook(Model model, Locale locale) {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("book", new Book());
		return "user/addbook";
	}


	@RequestMapping(value="/admin/addsubject")
	public String addSubject(Model model, Locale locale) {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		Subject subject = new Subject();
		List<Book> booklist = bookService.getAllBooks();
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("subject", subject);
		model.addAttribute("booklist", booklist);
		return "admin/addsubject";
	}	


	@RequestMapping(value = "/admin/addsubject", method = RequestMethod.POST)
	public String saveSubject(@ModelAttribute Subject subject, Model model, Locale locale) {
		logger.info("Welcome home! The client locale is {}.", locale);
		logger.info("",subject.toString());
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		subjectService.addSubject(subject);
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("successMessage", "Subject Added" );
		List<Book> booklist = bookService.getAllBooks();
		model.addAttribute("booklist", booklist);
		return "admin/addsubject";
	}

	@RequestMapping(value = "/admin/searchsubject", method = RequestMethod.GET)
	public String searchSubject(@ModelAttribute Subject subject,Locale locale, Model model) {
		logger.info("Welcome Searchsubject! The client locale is {}.", locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		if(subject.getSubjectId() != 0){
			Subject searchsub = new Subject();
			searchsub = subjectService.searchSubjectbyId(subject.getSubjectId());
			if (searchsub == null) {
				logger.info("*********No Subject");
				model.addAttribute("suberror", "No Subject with this ID" );
			}else {
				model.addAttribute("subject", searchsub );
			}
		}
		model.addAttribute("serverTime", formattedDate );
		return "admin/searchsubject";
	}

	@RequestMapping(value = "/admin/searchsubjectbyduration", method = RequestMethod.GET)
	public String searchSubjectByDuration(@ModelAttribute Subject subject,Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		if(subject.getDurationInHours() != 0){
			List<Subject> searchsub = subjectService.findSubjectByDuration(subject.getDurationInHours());
			if (searchsub.isEmpty() ) {
				logger.info("***********No Subject");
				model.addAttribute("suberror", "No Subject with this Duration" );
			}else {
				model.addAttribute("subjects", searchsub );
			}
		}
		model.addAttribute("serverTime", formattedDate );
		return "admin/searchsubjectbyduration";
	}

	@RequestMapping(value = "/admin/deletesubject", method = RequestMethod.GET)
	public String deleteBook(@ModelAttribute Subject subject,Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		if(subject.getSubjectId()!= 0){
			if (!subjectService.deleteSubjectbyId(subject.getSubjectId())) {
				model.addAttribute("suberror", "No Subject with this ID" );
			}else {
				model.addAttribute("suberror", "Subject Deleted" );
			}
		}
		model.addAttribute("serverTime", formattedDate );
		return "admin/deletesubject";
	}
}
