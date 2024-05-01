package com.myEdu.ws.controller;

import com.myEdu.ws.model.ProgramOutcome;
import com.myEdu.ws.repository.ProgramOutcomeRepository;
import com.myEdu.ws.service.ProgramOutcomeCalculationService;
import com.myEdu.ws.service.ProgramOutcomeService;
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

    private final ProgramOutcomeRepository programOutcomeRepository;

    @Autowired
    public ProgramOutcomeController(ProgramOutcomeCalculationService programOutcomeCalculationService, ProgramOutcomeService programOutcomeService, ProgramOutcomeRepository programOutcomeRepository) {
        this.programOutcomeCalculationService = programOutcomeCalculationService;
        this.programOutcomeService = programOutcomeService;
        this.programOutcomeRepository = programOutcomeRepository;
    }

    // Tüm program çıktılarını getir
    @GetMapping
    public ResponseEntity<List<ProgramOutcome>> getAllProgramOutcomes() {
        List<ProgramOutcome> programOutcomes = programOutcomeService.getAllProgramOutcomes();
        return ResponseEntity.ok(programOutcomes);
    }

    // Belirli bir program çıktısını ID'ye göre getir
    @GetMapping("/{id}")
    public ResponseEntity<ProgramOutcome> getProgramOutcomeById(@PathVariable Long id) {
        Optional<ProgramOutcome> programOutcome = programOutcomeService.getProgramOutcomeById(id);
        return programOutcome.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Yeni bir program çıktısı oluştur
    @PostMapping
    public ResponseEntity<ProgramOutcome> createProgramOutcome(@RequestBody ProgramOutcome programOutcome) {
        ProgramOutcome createdProgramOutcome = programOutcomeService.createProgramOutcome(programOutcome);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProgramOutcome);
    }

    // Program çıktısını güncelle
    @PutMapping("/{id}")
    public ResponseEntity<ProgramOutcome> updateProgramOutcome(@PathVariable Long id, @RequestBody ProgramOutcome programOutcome) {
        ProgramOutcome updatedProgramOutcome = programOutcomeService.updateProgramOutcome(id, programOutcome);
        if (updatedProgramOutcome != null) {
            return ResponseEntity.ok(updatedProgramOutcome);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Program çıktısını sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgramOutcome(@PathVariable Long id) {
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
