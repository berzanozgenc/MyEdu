package com.myEdu.ws.repository;


import com.myEdu.ws.model.StudentLearningOutcome;
import com.myEdu.ws.model.StudentProgramOutcome;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentProgramOutcomeRepository extends JpaRepository<StudentProgramOutcome, Long> {

    StudentProgramOutcome findByStudentUserIdAndProgramOutcomeId(Long userId, Long programId);

    List<StudentProgramOutcome> findByStudentUserIdAndProgramOutcomeIdIn(Long userId, List<Long> programOutcomeIds);

}
