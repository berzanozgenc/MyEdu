package com.myEdu.ws.repository;

import com.myEdu.ws.model.DepartmentProgramOutcome;
import com.myEdu.ws.model.ProgramOutcome;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentProgramOutcomeRepository extends JpaRepository<DepartmentProgramOutcome, Long> {

    public List<DepartmentProgramOutcome> findByDepartmentId(Long departmentId);
}

