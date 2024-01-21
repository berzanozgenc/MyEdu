package com.myEdu.ws.repository;

import com.myEdu.ws.model.Course;
import com.myEdu.ws.model.GeneralAssessment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeneralAssessmentRepository extends JpaRepository<GeneralAssessment, Long> {
    List<GeneralAssessment> findByCourse(Course course);
}
