package com.myEdu.ws.controller;

import com.myEdu.ws.dto.ContributionRequest;
import com.myEdu.ws.dto.ContributionUpdateRequest;
import com.myEdu.ws.model.Assessment;
import com.myEdu.ws.model.AssessmentLearningOutcomeContribution;
import com.myEdu.ws.model.LearningOutcomeProgramOutcome;
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
    public ResponseEntity<AssessmentLearningOutcomeContribution> createAssessmentLearningOutcomeContribution(@RequestBody ContributionRequest request) {
        AssessmentLearningOutcomeContribution record = contributionService.getAssessmentLearningOutcome(request.getAssessmentId(), request.getLearningOutcomeId());
        if(record == null) {
            AssessmentLearningOutcomeContribution relationship = contributionService.createAssessmentLearningOutcomeContribution(request.getAssessmentId(), request.getLearningOutcomeId(), request.getContribution());
            if (relationship != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(relationship);
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        else {
            record.setContribution(request.getContribution());
            contributionService.updateContribution(record);
            return new ResponseEntity<>(record, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{contributionId}")
    public ResponseEntity<Void> deleteAssessmentLearningOutcomeContribution(@PathVariable Long contributionId) {
        if (contributionId == null) {
            // Handle null case, e.g., return a ResponseEntity with a proper status
            return ResponseEntity.badRequest().build();
        }
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
