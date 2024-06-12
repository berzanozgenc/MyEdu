package com.myEdu.ws.service;

import com.myEdu.ws.exception.NotFoundException;
import com.myEdu.ws.model.*;
import com.myEdu.ws.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramOutcomeService {

    private final ProgramOutcomeRepository programOutcomeRepository;
    private final ProgramOutcomeCalculationService programOutcomeCalculationService;
    private final LearningOutcomeProgramOutcomeRepository learningOutcomeProgramOutcomeRepository;

    private final DepartmentRepository departmentRepository;

    private  final StudentProgramOutcomeRepository studentProgramOutcomeRepository;


    @Autowired
    public ProgramOutcomeService(ProgramOutcomeRepository programOutcomeRepository, ProgramOutcomeCalculationService programOutcomeCalculationService, LearningOutcomeProgramOutcomeRepository learningOutcomeProgramOutcomeRepository, CourseRepository courseRepository, DepartmentRepository departmentRepository, StudentProgramOutcomeRepository studentProgramOutcomeRepository) {
        this.programOutcomeRepository = programOutcomeRepository;
        this.programOutcomeCalculationService = programOutcomeCalculationService;
        this.learningOutcomeProgramOutcomeRepository = learningOutcomeProgramOutcomeRepository;
        this.departmentRepository = departmentRepository;
        this.studentProgramOutcomeRepository = studentProgramOutcomeRepository;
    }

    public void calculateAndSetProgramOutcomeTarget(Long id) {
        List<ProgramOutcome> programOutcomes = programOutcomeRepository.findByDepartmentId(id);
        for (ProgramOutcome programOutcome : programOutcomes){
            double target = programOutcomeCalculationService.calculateProgramOutcomeTarget(programOutcome);
            programOutcome.setTarget(target);
            programOutcomeRepository.save(programOutcome);
        }
    }

    public List<ProgramOutcome> getByDepartmentId(Long departmentId) {
        return programOutcomeRepository.findByDepartmentId(departmentId);
    }


    // Tüm program çıktılarını getir
    public List<ProgramOutcome> getAllProgramOutcomes() {
        return programOutcomeRepository.findAll();
    }

    // Belirli bir program çıktısını ID'ye göre getir
    public Optional<ProgramOutcome> getProgramOutcomeById(Long id) {
        return programOutcomeRepository.findById(id);
    }

    // Yeni bir program çıktısı oluştur
    public ProgramOutcome createProgramOutcome(ProgramOutcome programOutcome, Long departmentId) {
        Department department = departmentRepository.findById(departmentId).orElse(null);
        if (department != null) {
            programOutcome.setDepartment(department);
            return programOutcomeRepository.save(programOutcome);
        } else {
            // Belirtilen courseId ile ders bulunamazsa veya null ise null dönebilir veya isteğe göre bir hata işleme stratejisi uygulanabilir
            return null;
        }
    }

    // Program çıktısını güncelle
    public ProgramOutcome updateProgramOutcome(Long id, ProgramOutcome updatedProgramOutcome) {
        ProgramOutcome existingProgramOutcome = programOutcomeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ProgramOutcome not found with id: " + id));

        existingProgramOutcome.setDescription(updatedProgramOutcome.getDescription());
        existingProgramOutcome.setNumber(updatedProgramOutcome.getNumber());

        return programOutcomeRepository.save(existingProgramOutcome);
    }


    public ProgramOutcome findById(Long id) {
        return programOutcomeRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteProgramOutcome(Long id) {
        studentProgramOutcomeRepository.deleteAllByProgramOutcome(id);
        learningOutcomeProgramOutcomeRepository.deleteAllByProgramOutcome(id);
        programOutcomeRepository.deleteById(id);
    }

    public void calculateAndSetAssessmentValueForProgramOutcome(Long id) {
        List<ProgramOutcome> programOutcomes = programOutcomeRepository.findByDepartmentId(id);
        for (ProgramOutcome programOutcome : programOutcomes){
            List<LearningOutcomeProgramOutcome> mappings = learningOutcomeProgramOutcomeRepository.findByProgramOutcome(programOutcome);
            double assessmentValue = 0.0;
            for (LearningOutcomeProgramOutcome mapping : mappings) {
                LearningOutcome learningOutcome = mapping.getLearningOutcome();
                double contribution = mapping.getContribution();
                double learningOutcomeAssessmentSum = learningOutcome.getAssessmentSum();
                double valueToAdd = (learningOutcomeAssessmentSum * contribution) / 100;
                assessmentValue += valueToAdd;
            }
            programOutcome.setAssessmentValue(assessmentValue);
            programOutcomeRepository.save(programOutcome);
        }
    }

    public void calculateAndSetScoreAndLevelOfProvisionForProgramOutcome(Long id) {
        List<ProgramOutcome> programOutcomes = programOutcomeRepository.findByDepartmentId(id);
        for (ProgramOutcome programOutcome : programOutcomes){
            List<LearningOutcomeProgramOutcome> mappings = learningOutcomeProgramOutcomeRepository.findByProgramOutcome(programOutcome);
            double score = 0.0;
            for (LearningOutcomeProgramOutcome mapping : mappings) {
                LearningOutcome learningOutcome = mapping.getLearningOutcome();
                double contribution = mapping.getContribution();
                double learningOutcomeScoreSum= learningOutcome.getScoreSum();
                double valueToAdd = (learningOutcomeScoreSum * contribution) / 100;
                score += valueToAdd;
            }
            double levelOfProvision = score / programOutcome.getAssessmentValue() * 100;
            programOutcome.setLevelOfProvision(levelOfProvision);
            programOutcome.setScore(score);
            programOutcomeRepository.save(programOutcome);
        }
    }
}
