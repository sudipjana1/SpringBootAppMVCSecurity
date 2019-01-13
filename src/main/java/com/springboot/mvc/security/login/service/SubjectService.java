package com.springboot.mvc.security.login.service;


import java.util.List;

import com.springboot.mvc.security.login.model.Subject;

public interface SubjectService {

	public void addSubject(Subject book);
	public Subject searchSubjectbyId(long subjectId);
	public boolean deleteSubjectbyId(long subjectId) ;
	public List<Subject> getAllSubjects();
    List<Subject> findSubjectByDuration(int durationInHours);

}

