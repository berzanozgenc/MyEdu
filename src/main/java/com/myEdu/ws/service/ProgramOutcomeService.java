package com.myEdu.ws.service;

import com.myEdu.ws.model.LearningOutcome;
import com.myEdu.ws.model.LearningOutcomeProgramOutcome;
import com.myEdu.ws.model.ProgramOutcome;
import com.myEdu.ws.repository.LearningOutcomeProgramOutcomeRepository;
import com.myEdu.ws.repository.ProgramOutcomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramOutcomeService {

    private final ProgramOutcomeRepository programOutcomeRepository;
    private final ProgramOutcomeCalculationService programOutcomeCalculationService;
    private final LearningOutcomeProgramOutcomeRepository learningOutcomeProgramOutcomeRepository;

    @Autowired
    public ProgramOutcomeService(ProgramOutcomeRepository programOutcomeRepository, ProgramOutcomeCalculationService programOutcomeCalculationService, LearningOutcomeProgramOutcomeRepository learningOutcomeProgramOutcomeRepository) {
        this.programOutcomeRepository = programOutcomeRepository;
        this.programOutcomeCalculationService = programOutcomeCalculationService;
        this.learningOutcomeProgramOutcomeRepository = learningOutcomeProgramOutcomeRepository;
    }

    public void calculateAndSetProgramOutcomeTarget(ProgramOutcome programOutcome) {
        double target = programOutcomeCalculationService.calculateProgramOutcomeTarget(programOutcome);
        programOutcome.setTarget(target);
        programOutcomeRepository.save(programOutcome);
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
    public ProgramOutcome createProgramOutcome(ProgramOutcome programOutcome) {
        return programOutcomeRepository.save(programOutcome);
    }

    // Program çıktısını güncelle
    public ProgramOutcome updateProgramOutcome(Long id, ProgramOutcome newProgramOutcome) {
        if (programOutcomeRepository.existsById(id)) {
            newProgramOutcome.setId(id);
            return programOutcomeRepository.save(newProgramOutcome);
        } else {
            // Belirli bir ID ile program çıktısı bulunamazsa null dönebilir veya isteğe göre bir hata işleme stratejisi uygulanabilir
            return null;
        }
    }

    public ProgramOutcome findById(Long id) {
        return programOutcomeRepository.findById(id).orElse(null);
    }

    // Program çıktısını sil
    public void deleteProgramOutcome(Long id) {
        programOutcomeRepository.deleteById(id);
    }

    public void calculateAndSetAssessmentValueForProgramOutcome(ProgramOutcome programOutcome) {
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
