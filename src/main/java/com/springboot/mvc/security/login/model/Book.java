package com.springboot.mvc.security.login.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@SequenceGenerator(name="BOOK_SEQ", sequenceName="book_sequence",allocationSize=1)
@Table(name="books")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="BOOK_SEQ")
	@Column(name="bookid")
	long bookId;
	@Column(name="title")
	String title;
	@Column(name="price")
	double price;
	@Column(name="volume")
	int volume;
	@Column(name="publishdate")
	@Temporal(TemporalType.DATE)
	Date publishDate;
	/*@ManyToMany(cascade=CascadeType.MERGE)
	@JoinTable(name="subjectsbooks",joinColumns= {@JoinColumn(name="bookId")},inverseJoinColumns= {@JoinColumn(name="subjectId")})
	List<Subject> subjects;*/
	


	public Book() {
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", title=" + title + ", price=" + price + ", volume=" + volume
				+ ", publishDate=" + publishDate + "]";
	}



	
	
	

}
