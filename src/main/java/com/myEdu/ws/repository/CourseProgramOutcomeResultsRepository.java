package com.myEdu.ws.repository;

import com.myEdu.ws.model.CourseProgramOutcomeResults;
import org.springframework.data.jpa.repository.JpaRepository;
import com.myEdu.ws.model.Course;
import com.myEdu.ws.model.ProgramOutcome;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseProgramOutcomeResultsRepository extends JpaRepository<CourseProgramOutcomeResults, Long> {
    Optional<CourseProgramOutcomeResults> findByCourseAndProgramOutcome(Course course, ProgramOutcome programOutcome);

    List<CourseProgramOutcomeResults> findByCourseCourseId(Long courseId);

    List<CourseProgramOutcomeResults> findByCourseCourseIdAndProgramOutcomeId(Long courseId, Long programOutcomeId);

    @Modifying
    @Query("DELETE FROM CourseProgramOutcomeResults WHERE programOutcome.id = :programOutcomeId")
    void deleteAllByProgramOutcome(@Param("programOutcomeId") long programOutcomeId);

    @Modifying
    @Query("DELETE FROM CourseProgramOutcomeResults WHERE course.courseId = :courseId")
    void deleteAllByCourse(@Param("courseId") long courseId);
}
