package com.myEdu.ws.controller;

import com.myEdu.ws.model.LearningOutcome;
import com.myEdu.ws.model.ProgramOutcome;
import com.myEdu.ws.repository.LearningOutcomeProgramOutcomeRepository;
import com.myEdu.ws.repository.ProgramOutcomeRepository;
import com.myEdu.ws.service.LearningOutcomeProgramOutcomeService;
import com.myEdu.ws.service.LearningOutcomeService;
import com.myEdu.ws.service.ProgramOutcomeCalculationService;
import com.myEdu.ws.service.ProgramOutcomeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/program-outcomes")
public class ProgramOutcomeController {

    private final ProgramOutcomeCalculationService programOutcomeCalculationService;
    private final ProgramOutcomeService programOutcomeService;
    private final LearningOutcomeService learningOutcomeService;

    private final LearningOutcomeProgramOutcomeRepository learningOutcomeProgramOutcomeRepository;

    private final ProgramOutcomeRepository programOutcomeRepository;

    @Autowired
    public ProgramOutcomeController(ProgramOutcomeCalculationService programOutcomeCalculationService, ProgramOutcomeService programOutcomeService, ProgramOutcomeRepository programOutcomeRepository, LearningOutcomeProgramOutcomeRepository learningOutcomeProgramOutcomeRepository, LearningOutcomeService learningOutcomeService) {
        this.programOutcomeCalculationService = programOutcomeCalculationService;
        this.programOutcomeService = programOutcomeService;
        this.programOutcomeRepository = programOutcomeRepository;
        this.learningOutcomeProgramOutcomeRepository = learningOutcomeProgramOutcomeRepository;
        this.learningOutcomeService = learningOutcomeService;
    }

    // Tüm program çıktılarını getir
    @GetMapping
    public ResponseEntity<List<ProgramOutcome>> getAllProgramOutcomes() {
        List<ProgramOutcome> programOutcomes = programOutcomeService.getAllProgramOutcomes();
        return ResponseEntity.ok(programOutcomes);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<ProgramOutcome>> getProgramOutcomesByCourseId(@PathVariable Long courseId) {
        List<ProgramOutcome> programOutcomes = programOutcomeService.getByCourseId(courseId);
        return ResponseEntity.ok().body(programOutcomes);
    }

    // Belirli bir program çıktısını ID'ye göre getir
    @GetMapping("/{id}")
    public ResponseEntity<ProgramOutcome> getProgramOutcomeById(@PathVariable Long id) {
        Optional<ProgramOutcome> programOutcome = programOutcomeService.getProgramOutcomeById(id);
        return programOutcome.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Yeni bir program çıktısı oluştur
    @PostMapping("/{courseId}")
    public ResponseEntity<ProgramOutcome> createProgramOutcome(@PathVariable Long courseId, @RequestBody ProgramOutcome programOutcome) {
        ProgramOutcome createdProgramOutcome = programOutcomeService.createProgramOutcome(programOutcome, courseId);
        if (createdProgramOutcome != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProgramOutcome);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // veya isteğe göre uygun bir hata durumu dönebilirsiniz
        }
    }

    // Program çıktısını güncelle
    @PutMapping("/{id}")
    public ResponseEntity<ProgramOutcome> updateProgramOutcome(@PathVariable Long id,
                                                                 @RequestBody ProgramOutcome updatedProgramOutcome) {
        ProgramOutcome programOutcome = programOutcomeService.updateProgramOutcome(id, updatedProgramOutcome);
        return new ResponseEntity<>(programOutcome, HttpStatus.OK);
    }

    // Program çıktısını sil
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteProgramOutcome(@PathVariable Long id) {
        learningOutcomeProgramOutcomeRepository.deleteAllByProgramOutcome(id);
        programOutcomeService.deleteProgramOutcome(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/calculate-and-set-target")
    public ResponseEntity<Double> calculateAndSetTarget(@PathVariable Long id) {
        ProgramOutcome programOutcome = programOutcomeService.findById(id);
        if (programOutcome == null) {
            return ResponseEntity.notFound().build();
        }
        programOutcomeService.calculateAndSetProgramOutcomeTarget(programOutcome);
        return ResponseEntity.ok(programOutcome.getTarget());
    }

    @PutMapping("/{id}/calculate-and-set-assessment-value")
    public ResponseEntity<String> calculateAndSetAssessmentValueForProgramOutcome(@PathVariable Long id) {
        ProgramOutcome programOutcome = programOutcomeRepository.findById(id).orElse(null);
        if (programOutcome == null) {
            return ResponseEntity.notFound().build();
        }
        programOutcomeService.calculateAndSetAssessmentValueForProgramOutcome(programOutcome);
        return ResponseEntity.ok("Assessment value calculated and set successfully for ProgramOutcome with ID: " + id);
    }

    @PostMapping("/{id}/calculate-and-set-score-and-level-of-provision")
    public ResponseEntity<String> calculateAndSetScoreAndLevelOfProvisionForProgramOutcome(@PathVariable Long id) {
        ProgramOutcome programOutcome = programOutcomeRepository.findById(id).orElse(null);
        if (programOutcome == null) {
            return ResponseEntity.notFound().build();
        }
        programOutcomeService.calculateAndSetScoreAndLevelOfProvisionForProgramOutcome(programOutcome);
        return ResponseEntity.ok("Score and level of provision calculated and set successfully for ProgramOutcome with ID: " + id);
    }

}
