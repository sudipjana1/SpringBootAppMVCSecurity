package com.springboot.mvc.security.login.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name="SUBJECT_SEQ", sequenceName="subject_sequence",allocationSize=1)
@Table(name="subjects")
public class Subject  {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SUBJECT_SEQ")
	@Column(name="subjectid")
	long subjectId;
	@Column(name="subtitle")
	String subtitle;
	@Column(name="duration")
	int durationInHours;
	@ManyToMany(cascade=CascadeType.MERGE,fetch=FetchType.EAGER)
	@JoinTable(name="subjectsbooks",joinColumns= {@JoinColumn(name="subjectId")},inverseJoinColumns= {@JoinColumn(name="bookId")})
	List<Book> references;

	public Subject() {
		
	}
	public long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public int getDurationInHours() {
		return durationInHours;
	}
	public void setDurationInHours(int durationInHours) {
		this.durationInHours = durationInHours;
	}
	public List<Book> getReferences() {
		return references;
	}
	public void setReferences(List<Book> references) {
		this.references = references;
	}
	@Override
	public String toString() {
		return "Subject [subjectId=" + subjectId + ", subtitle=" + subtitle + ", durationInHours=" + durationInHours
				+ ", references=" + references + "]";
	}
	
	
	
	

}
