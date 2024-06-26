package com.myEdu.ws.repository;


import com.myEdu.ws.model.StudentLearningOutcome;
import com.myEdu.ws.model.StudentProgramOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentProgramOutcomeRepository extends JpaRepository<StudentProgramOutcome, Long> {

    StudentProgramOutcome findByStudentUserIdAndProgramOutcomeIdAndCourseCourseId(Long userId, Long programId,Long courseId);

    List<StudentProgramOutcome> findByStudentUserIdAndCourseCourseIdAndProgramOutcomeIdIn(Long userId, Long courseId, List<Long> programOutcomeIds);

    @Modifying
    @Query("DELETE FROM StudentProgramOutcome WHERE course.courseId = :courseId")
    void deleteAllByCourse(@Param("courseId") long courseId);

    @Modifying
    @Query("DELETE FROM StudentProgramOutcome WHERE programOutcome.id = :programOutcomeId")
    void deleteAllByProgramOutcome(@Param("programOutcomeId") long programOutcomeId);

    List<StudentProgramOutcome> findByStudentUserIdAndProgramOutcomeId(Long studentId, Long programOutcomeId);

}
