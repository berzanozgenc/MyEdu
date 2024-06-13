package com.myEdu.ws.controller;

import com.myEdu.ws.model.Course;
import com.myEdu.ws.model.Department;
import com.myEdu.ws.model.LearningOutcome;
import com.myEdu.ws.model.ProgramOutcome;
import com.myEdu.ws.repository.*;
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

    private final DepartmentRepository departmentRepository;

    private final CourseRepository courseRepository;

    private final CourseProgramOutcomeResultsRepository courseProgramOutcomeResultsRepository;

    private final StudentProgramOutcomeRepository studentProgramOutcomeRepository;

    @Autowired
    public ProgramOutcomeController(ProgramOutcomeCalculationService programOutcomeCalculationService, ProgramOutcomeService programOutcomeService, ProgramOutcomeRepository programOutcomeRepository, LearningOutcomeProgramOutcomeRepository learningOutcomeProgramOutcomeRepository, LearningOutcomeService learningOutcomeService, DepartmentRepository departmentRepository, CourseRepository courseRepository, CourseProgramOutcomeResultsRepository courseProgramOutcomeResultsRepository, StudentProgramOutcomeRepository studentProgramOutcomeRepository) {
        this.programOutcomeCalculationService = programOutcomeCalculationService;
        this.programOutcomeService = programOutcomeService;
        this.programOutcomeRepository = programOutcomeRepository;
        this.learningOutcomeProgramOutcomeRepository = learningOutcomeProgramOutcomeRepository;
        this.learningOutcomeService = learningOutcomeService;
        this.departmentRepository = departmentRepository;
        this.courseRepository = courseRepository;
        this.courseProgramOutcomeResultsRepository = courseProgramOutcomeResultsRepository;
        this.studentProgramOutcomeRepository = studentProgramOutcomeRepository;
    }

    @GetMapping
    public ResponseEntity<List<ProgramOutcome>> getAllProgramOutcomes() {
        List<ProgramOutcome> programOutcomes = programOutcomeService.getAllProgramOutcomes();
        return ResponseEntity.ok(programOutcomes);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<ProgramOutcome>> getProgramOutcomesByDepartmentId(@PathVariable Long departmentId) {
        List<ProgramOutcome> programOutcomes = programOutcomeService.getByDepartmentId(departmentId);
        return ResponseEntity.ok().body(programOutcomes);
    }

    // Belirli bir program çıktısını ID'ye göre getir
    @GetMapping("/{id}")
    public ResponseEntity<ProgramOutcome> getProgramOutcomeById(@PathVariable Long id) {
        Optional<ProgramOutcome> programOutcome = programOutcomeService.getProgramOutcomeById(id);
        return programOutcome.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Yeni bir program çıktısı oluştur
    @PostMapping("/{departmentId}")
    public ResponseEntity<ProgramOutcome> createProgramOutcome(@PathVariable Long departmentId, @RequestBody ProgramOutcome programOutcome) {
        ProgramOutcome createdProgramOutcome = programOutcomeService.createProgramOutcome(programOutcome, departmentId);
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
        studentProgramOutcomeRepository.deleteAllByProgramOutcome(id);
        courseProgramOutcomeResultsRepository.deleteAllByProgramOutcome(id);
        programOutcomeService.deleteProgramOutcome(id);
        return ResponseEntity.noContent().build();
    }

}
