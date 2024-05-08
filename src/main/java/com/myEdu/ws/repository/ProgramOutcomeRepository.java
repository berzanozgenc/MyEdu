package com.myEdu.ws.repository;

import com.myEdu.ws.model.LearningOutcome;
import com.myEdu.ws.model.ProgramOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramOutcomeRepository extends JpaRepository<ProgramOutcome, Long> {
    public List<ProgramOutcome> findByCourseCourseId(Long courseId);
}
