package com.myEdu.ws.repository;

import com.myEdu.ws.model.CourseProgramOutcomeResults;
import org.springframework.data.jpa.repository.JpaRepository;
import com.myEdu.ws.model.Course;
import com.myEdu.ws.model.ProgramOutcome;

import java.util.List;
import java.util.Optional;

public interface CourseProgramOutcomeResultsRepository extends JpaRepository<CourseProgramOutcomeResults, Long> {
    Optional<CourseProgramOutcomeResults> findByCourseAndProgramOutcome(Course course, ProgramOutcome programOutcome);

    List<CourseProgramOutcomeResults> findByCourseCourseId(Long courseId);
}
