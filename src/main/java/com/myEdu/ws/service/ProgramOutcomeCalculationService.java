package com.myEdu.ws.service;

import com.myEdu.ws.model.Course;
import com.myEdu.ws.model.LearningOutcome;
import com.myEdu.ws.model.LearningOutcomeProgramOutcome;
import com.myEdu.ws.model.ProgramOutcome;
import com.myEdu.ws.repository.LearningOutcomeProgramOutcomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramOutcomeCalculationService {

    private final LearningOutcomeProgramOutcomeRepository learningOutcomeProgramOutcomeRepository;

    @Transactional(readOnly = true)
    public double calculateProgramOutcomeTarget(ProgramOutcome programOutcome, Course course) {
        List<LearningOutcomeProgramOutcome> mappings = learningOutcomeProgramOutcomeRepository.findByProgramOutcomeAndCourse(programOutcome, course);
        double target = 0.0;
        for (LearningOutcomeProgramOutcome mapping : mappings) {
            LearningOutcome learningOutcome = mapping.getLearningOutcome();
            double contribution = mapping.getContribution();
            double desiredTarget = learningOutcome.getDesiredTarget();
            double contributionPercentage = contribution / 100;
            target += desiredTarget * contributionPercentage;
        }
        return target;
    }
}
