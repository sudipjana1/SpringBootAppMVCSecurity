package com.springboot.mvc.security.login.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springboot.mvc.security.login.model.Subject;
import com.springboot.mvc.security.login.repository.SubjectRepository;

@Component
public class SubjectServiceImpl implements SubjectService{
	@Autowired
	SubjectRepository subjectdao;

	@Override
	public void addSubject(Subject subject) {
		subjectdao.save(subject);
	}

	@Override
	public Subject searchSubjectbyId(long subjectId) {
		Optional<Subject> subject = null;
		subject = subjectdao.findById(subjectId);
		if(subject.isPresent())
			return subject.get();
		else
			return null;
	}
	
	@Override	
	public boolean deleteSubjectbyId(long subjectId) {
		
		if(!subjectdao.findById(subjectId).isPresent()) {
			return false;
		}else {
			subjectdao.deleteById(subjectId);
			return true;
		}
		
	}
	
	@Override
	public List<Subject> getAllSubjects() {
		return subjectdao.findAll();
	}

	@Override
	public List<Subject> findSubjectByDuration(int durationInHours) {
		List<Subject> subjects = subjectdao.findSubjectByDuration(durationInHours);
		System.out.println(subjects.toString());
		return subjects;
	}

}

