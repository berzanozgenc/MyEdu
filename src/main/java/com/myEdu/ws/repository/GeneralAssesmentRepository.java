package com.myEdu.ws.repository;

import com.myEdu.ws.model.Course;
import com.myEdu.ws.model.GeneralAssesment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeneralAssesmentRepository extends JpaRepository<GeneralAssesment, Long> {
    List<GeneralAssesment> findByCourse(Course course);
}
