package com.myEdu.ws.service;

import com.myEdu.ws.exception.NotFoundException;
import com.myEdu.ws.model.*;
import com.myEdu.ws.repository.AssessmentLearningOutcomeContributionRepository;
import com.myEdu.ws.repository.AssessmentRepository;
import com.myEdu.ws.repository.LearningOutcomeRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public AssessmentLearningOutcomeContribution createAssessmentLearningOutcomeContribution(Long assessmentId, Long learningOutcomeId, Double contribution) {
        LearningOutcome learningOutcome = learningOutcomeRepository.findById(learningOutcomeId).orElse(null);
        Assessment assessment = assessmentRepository.findById(assessmentId).orElse(null);
        if (learningOutcome != null && assessment != null) {
            AssessmentLearningOutcomeContribution relationship = new AssessmentLearningOutcomeContribution();
            relationship.setLearningOutcome(learningOutcome);
            relationship.setAssessment(assessment);
            relationship.setContribution(contribution); // Contribution değerini ayarla
            return contributionRepository.save(relationship);
        }
        return null;
    }

    public AssessmentLearningOutcomeContribution updateContribution(AssessmentLearningOutcomeContribution assessmentLearningOutcomeContribution) {
        return contributionRepository.save(assessmentLearningOutcomeContribution);
    }

    public void deleteAssessmentLearningOutcomeContribution(Long contributionId) {
        Optional<AssessmentLearningOutcomeContribution> contributionOptional = contributionRepository.findById(contributionId);
        if (contributionOptional.isPresent()) {
            contributionRepository.deleteById(contributionId);
        } else {
            // Handle case where contribution with given id is not found
            throw new EntityNotFoundException("Contribution with id " + contributionId + " not found");
        }
    }

    public AssessmentLearningOutcomeContribution updateContributionValue(Long contributionId, Double newContribution) {
        AssessmentLearningOutcomeContribution contribution = contributionRepository.findById(contributionId)
                .orElseThrow(() -> new NotFoundException("Contribution not found with id: " + contributionId));

        contribution.setContribution(newContribution);

        return contributionRepository.save(contribution);
    }


    public AssessmentLearningOutcomeContribution getAssessmentLearningOutcome(Long assessmentId, Long learningOutcomeId) {
        Optional<AssessmentLearningOutcomeContribution> relationship = contributionRepository.findByLearningOutcomeIdAndAssessmentAssessmentId(learningOutcomeId, assessmentId);
        return relationship.orElse(null);
    }
}
