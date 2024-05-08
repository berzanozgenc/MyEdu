package com.myEdu.ws.controller;

import com.myEdu.ws.dto.ContributionResponseDto;
import com.myEdu.ws.dto.LearningOutcomeContributionDTO;
import com.myEdu.ws.dto.LearningOutcomeContributionListDTO;
import com.myEdu.ws.dto.LearningOutcomeProgramOutcomeDTO;
import com.myEdu.ws.model.LearningOutcomeProgramOutcome;
import com.myEdu.ws.service.LearningOutcomeProgramOutcomeService;
import org.apache.coyote.ContinueResponseTiming;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/learning-outcome-program-outcome")
public class LearningOutcomeProgramOutcomeController {

    private final LearningOutcomeProgramOutcomeService learningOutcomeProgramOutcomeService;

    @Autowired
    public LearningOutcomeProgramOutcomeController(LearningOutcomeProgramOutcomeService learningOutcomeProgramOutcomeService) {
        this.learningOutcomeProgramOutcomeService = learningOutcomeProgramOutcomeService;
    }

    // İlişki Oluşturma (POST)
    @PostMapping
    public ResponseEntity<LearningOutcomeProgramOutcome> createRelationship(@RequestBody LearningOutcomeProgramOutcomeDTO request) {

        LearningOutcomeProgramOutcome record = learningOutcomeProgramOutcomeService.getLearningProgramOutcome(request.getLearningOutcomeId(), request.getProgramOutcomeId());
        if(record == null) {
            LearningOutcomeProgramOutcome relationship = learningOutcomeProgramOutcomeService.createRelationship(request.getLearningOutcomeId(), request.getProgramOutcomeId(), request.getContribution());
            if (relationship != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(relationship);
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        else {
            record.setContribution(request.getContribution());
            learningOutcomeProgramOutcomeService.updateContribution(record);
            return new ResponseEntity<>(record, HttpStatus.OK);
        }
    }

    // İlişki Silme (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRelationship(@PathVariable Long id) {
        learningOutcomeProgramOutcomeService.deleteRelationship(id);
        return ResponseEntity.noContent().build();
    }


    // Belirli bir learning outcome ve program outcome ID'sine göre contribution değerini getirme (GET)
    @PostMapping("/contribution")
    public ResponseEntity<ContributionResponseDto> getContributionByOutcomeIds(@RequestBody LearningOutcomeContributionListDTO listDto) {
        List<LearningOutcomeContributionDTO> records = listDto.getLearningOutcomeContributionDTOList();

        ContributionResponseDto contributionResponseDto = new ContributionResponseDto();
        List<LearningOutcomeProgramOutcome> list = new ArrayList<>();
        for(LearningOutcomeContributionDTO dto: records){
            LearningOutcomeProgramOutcome learningOutcomeProgramOutcome = learningOutcomeProgramOutcomeService.getLearningProgramOutcome(dto.getLearningId(), dto.getProgramId());
            if (learningOutcomeProgramOutcome != null) {
               list.add(learningOutcomeProgramOutcome);
            }
        }
       contributionResponseDto.setContributions(list);
        return new ResponseEntity<>(contributionResponseDto, HttpStatus.OK);
    }
}
