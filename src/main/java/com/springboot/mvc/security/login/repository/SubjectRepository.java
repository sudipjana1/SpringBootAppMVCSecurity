package com.springboot.mvc.security.login.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.mvc.security.login.model.Subject;


public interface SubjectRepository extends JpaRepository<Subject, Long> {
	@Query("SELECT s FROM Subject s WHERE " +
            "LOWER(s.durationInHours) = :durationInHours" )
    List<Subject> findSubjectByDuration(@Param("durationInHours") int durationInHours);

}
