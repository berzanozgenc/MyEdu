package com.myEdu.ws.repository;

import com.myEdu.ws.model.Course;
import com.myEdu.ws.model.LearningOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LearningOutcomeRepository extends JpaRepository<LearningOutcome, Long> {

    @Query("SELECT lo FROM LearningOutcome lo WHERE lo.course.courseId = :courseId")
    public List<LearningOutcome> findByCourseId(Long courseId);

}