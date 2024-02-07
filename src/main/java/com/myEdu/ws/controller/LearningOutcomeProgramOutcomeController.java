package com.myEdu.ws.controller;

import com.myEdu.ws.dto.LearningOutcomeProgramOutcomeDTO;
import com.myEdu.ws.model.LearningOutcomeProgramOutcome;
import com.myEdu.ws.service.LearningOutcomeProgramOutcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        LearningOutcomeProgramOutcome relationship = learningOutcomeProgramOutcomeService.createRelationship(request.getLearningOutcomeId(), request.getProgramOutcomeId(), request.getContribution());
        if (relationship != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(relationship);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // İlişki Silme (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRelationship(@PathVariable Long id) {
        learningOutcomeProgramOutcomeService.deleteRelationship(id);
        return ResponseEntity.noContent().build();
    }

    // İlişki Güncelleme (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<LearningOutcomeProgramOutcome> updateRelationship(@PathVariable Long id, @RequestBody LearningOutcomeProgramOutcomeDTO request) {
        LearningOutcomeProgramOutcome updatedRelationship = learningOutcomeProgramOutcomeService.updateRelationship(id, request.getLearningOutcomeId(), request.getProgramOutcomeId(), request.getContribution());
        if (updatedRelationship != null) {
            return ResponseEntity.ok(updatedRelationship);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
