package com.myEdu.ws.service;

import com.myEdu.ws.exception.NotFoundException;
import com.myEdu.ws.model.Assessment;
import com.myEdu.ws.model.AssessmentLearningOutcomeContribution;
import com.myEdu.ws.model.LearningOutcome;
import com.myEdu.ws.repository.AssessmentLearningOutcomeContributionRepository;
import com.myEdu.ws.repository.AssessmentRepository;
import com.myEdu.ws.repository.LearningOutcomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AssessmentLearningOutcomeContributionService {

    @Autowired
    private AssessmentLearningOutcomeContributionRepository contributionRepository;

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private LearningOutcomeRepository learningOutcomeRepository;

    public AssessmentLearningOutcomeContribution createAssessmentLearningOutcomeContribution(
            Long assessmentId, Long learningOutcomeId, Double contribution) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new NotFoundException("Assessment not found with id: " + assessmentId));

        LearningOutcome learningOutcome = learningOutcomeRepository.findById(learningOutcomeId)
                .orElseThrow(() -> new NotFoundException("LearningOutcome not found with id: " + learningOutcomeId));

        AssessmentLearningOutcomeContribution contributionEntry =
                contributionRepository.findByAssessmentAndLearningOutcome(assessment, learningOutcome)
                        .orElseGet(() -> new AssessmentLearningOutcomeContribution(assessment, learningOutcome, 0.0));

        contributionEntry.setContribution(contribution);

        return contributionRepository.save(contributionEntry);
    }

    public void deleteAssessmentLearningOutcomeContribution(Long contributionId) {
        contributionRepository.deleteById(contributionId);
    }

    public AssessmentLearningOutcomeContribution updateContributionValue(Long contributionId, Double newContribution) {
        AssessmentLearningOutcomeContribution contribution = contributionRepository.findById(contributionId)
                .orElseThrow(() -> new NotFoundException("Contribution not found with id: " + contributionId));

        contribution.setContribution(newContribution);

        return contributionRepository.save(contribution);
    }
}
