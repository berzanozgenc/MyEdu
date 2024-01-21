package com.myEdu.ws.controller;

import com.myEdu.ws.dto.ContributionRequest;
import com.myEdu.ws.dto.ContributionUpdateRequest;
import com.myEdu.ws.model.AssessmentLearningOutcomeContribution;
import com.myEdu.ws.service.AssessmentLearningOutcomeContributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aloc")
public class AssessmentLearningOutcomeContributionController {

    @Autowired
    private AssessmentLearningOutcomeContributionService contributionService;

    @PostMapping
    public ResponseEntity<AssessmentLearningOutcomeContribution> createContribution(
            @RequestBody ContributionRequest request) {
        AssessmentLearningOutcomeContribution newContribution = contributionService.createAssessmentLearningOutcomeContribution(
                request.getAssessmentId(), request.getLearningOutcomeId(), request.getContribution());
        return new ResponseEntity<>(newContribution, HttpStatus.CREATED);
    }

    @DeleteMapping("/{contributionId}")
    public ResponseEntity<Void> deleteContribution(@PathVariable Long contributionId) {
        contributionService.deleteAssessmentLearningOutcomeContribution(contributionId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{contributionId}")
    public ResponseEntity<AssessmentLearningOutcomeContribution> updateContribution(
            @PathVariable Long contributionId,
            @RequestBody ContributionUpdateRequest request) {
        AssessmentLearningOutcomeContribution updatedContribution =
                contributionService.updateContributionValue(contributionId, request.getNewContribution());
        return ResponseEntity.ok(updatedContribution);
    }
}
