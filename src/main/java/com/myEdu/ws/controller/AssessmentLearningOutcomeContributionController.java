package com.myEdu.ws.controller;

import com.myEdu.ws.dto.*;
import com.myEdu.ws.model.Assessment;
import com.myEdu.ws.model.AssessmentLearningOutcomeContribution;
import com.myEdu.ws.model.LearningOutcomeProgramOutcome;
import com.myEdu.ws.service.AssessmentLearningOutcomeContributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/aloc")
public class AssessmentLearningOutcomeContributionController {

    @Autowired
    private AssessmentLearningOutcomeContributionService contributionService;

    @PostMapping
    public ResponseEntity<String> createAssessmentLearningOutcomeContribution(@RequestBody ContributionRequestListDto requestListDto) {
        boolean created = false;
        boolean updated = false;
        for(ContributionRequest request: requestListDto.getAlocList()) {
            AssessmentLearningOutcomeContribution record = contributionService.getAssessmentLearningOutcome(request.getAssessmentId(), request.getLearningOutcomeId());
            if(record == null) {
                AssessmentLearningOutcomeContribution relationship = contributionService.createAssessmentLearningOutcomeContribution(request.getAssessmentId(), request.getLearningOutcomeId(), request.getContribution());
                created = true;
                updated = false;
            }
            else {
                record.setContribution(request.getContribution());
                contributionService.updateContribution(record);
                updated = true;
                created = false;
            }
        }
        if(created || updated)
            return new ResponseEntity<>("Changes are persisted",HttpStatus.OK);
        else
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);

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

    @PostMapping("/contribution")
    public ResponseEntity<AlocContributionResponseDto> getContributionByAssessmentLearningOutcomeIds(@RequestBody AssessmentContributionListDto listDto) {
        List<AssessmentContributionDto> records = listDto.getAssessmentContributionDTOList();

        AlocContributionResponseDto alocContributionResponseDto = new AlocContributionResponseDto();
        List<AssessmentLearningOutcomeContribution> list = new ArrayList<>();
        for(AssessmentContributionDto dto: records){
            AssessmentLearningOutcomeContribution assessmentLearningOutcomeContribution = contributionService.getAssessmentLearningOutcome(dto.getAssessmentId(), dto.getLearningId());
            if (assessmentLearningOutcomeContribution != null) {
                list.add(assessmentLearningOutcomeContribution);
            }
        }
        alocContributionResponseDto.setContributions(list);
        return new ResponseEntity<>(alocContributionResponseDto, HttpStatus.OK);
    }

}
