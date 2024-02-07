package com.myEdu.ws.repository;

import com.myEdu.ws.model.ProgramOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramOutcomeRepository extends JpaRepository<ProgramOutcome, Long> {
}
