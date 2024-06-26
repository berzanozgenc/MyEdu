package com.myEdu.ws.repository;

import com.myEdu.ws.model.Course;
import com.myEdu.ws.model.LearningOutcome;
import com.myEdu.ws.model.LearningOutcomeProgramOutcome;
import com.myEdu.ws.model.ProgramOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LearningOutcomeProgramOutcomeRepository extends JpaRepository  <LearningOutcomeProgramOutcome, Long> {
    Optional<LearningOutcomeProgramOutcome> findByLearningOutcomeIdAndProgramOutcomeId(Long learningOutcomeId, Long programOutcomeId);
    List<LearningOutcomeProgramOutcome> findByProgramOutcome(ProgramOutcome programOutcome);
    List<LearningOutcomeProgramOutcome> findByProgramOutcomeAndCourse(ProgramOutcome programOutcome, Course course);

    @Modifying
    @Query("DELETE FROM CourseProgramOutcomeResults WHERE course.courseId = :courseId")
    void deleteAllByCourse(@Param("courseId") long courseId);

    @Modifying
    @Query("DELETE FROM LearningOutcomeProgramOutcome WHERE programOutcome.id = :programOutcomeId")
    void deleteAllByProgramOutcome(@Param("programOutcomeId") long programOutcomeId);

    @Modifying
    @Query("DELETE FROM LearningOutcomeProgramOutcome WHERE learningOutcome.id = :learningOutcomeId")
    void deleteAllByLearningOutcome(@Param("learningOutcomeId") long learningOutcomeId);
}
