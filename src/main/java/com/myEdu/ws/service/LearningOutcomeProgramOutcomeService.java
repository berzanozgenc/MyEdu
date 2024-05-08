package com.myEdu.ws.service;

import com.myEdu.ws.model.LearningOutcome;
import com.myEdu.ws.model.LearningOutcomeProgramOutcome;
import com.myEdu.ws.model.ProgramOutcome;
import com.myEdu.ws.repository.LearningOutcomeProgramOutcomeRepository;
import com.myEdu.ws.repository.LearningOutcomeRepository;
import com.myEdu.ws.repository.ProgramOutcomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LearningOutcomeProgramOutcomeService {

    private final LearningOutcomeProgramOutcomeRepository learningOutcomeProgramOutcomeRepository;
    private final LearningOutcomeRepository learningOutcomeRepository;
    private final ProgramOutcomeRepository programOutcomeRepository;

    @Autowired
    public LearningOutcomeProgramOutcomeService(LearningOutcomeProgramOutcomeRepository learningOutcomeProgramOutcomeRepository,
                                                LearningOutcomeRepository learningOutcomeRepository,
                                                ProgramOutcomeRepository programOutcomeRepository) {
        this.learningOutcomeProgramOutcomeRepository = learningOutcomeProgramOutcomeRepository;
        this.learningOutcomeRepository = learningOutcomeRepository;
        this.programOutcomeRepository = programOutcomeRepository;
    }

    // İlişki oluştur
    public LearningOutcomeProgramOutcome createRelationship(Long learningOutcomeId, Long programOutcomeId, double contribution) {
        LearningOutcome learningOutcome = learningOutcomeRepository.findById(learningOutcomeId).orElse(null);
        ProgramOutcome programOutcome = programOutcomeRepository.findById(programOutcomeId).orElse(null);
        if (learningOutcome != null && programOutcome != null) {
            LearningOutcomeProgramOutcome relationship = new LearningOutcomeProgramOutcome();
            relationship.setLearningOutcome(learningOutcome);
            relationship.setProgramOutcome(programOutcome);
            relationship.setContribution(contribution); // Contribution değerini ayarla
            return learningOutcomeProgramOutcomeRepository.save(relationship);
        }
        return null;
    }

    // İlişki sil
    public void deleteRelationship(Long id) {
        learningOutcomeProgramOutcomeRepository.deleteById(id);
    }

    //İlişki güncelle
    public LearningOutcomeProgramOutcome updateContribution(LearningOutcomeProgramOutcome learningOutcomeProgramOutcome) {
      return learningOutcomeProgramOutcomeRepository.save(learningOutcomeProgramOutcome);
    }

    public LearningOutcomeProgramOutcome getLearningProgramOutcome(Long learningOutcomeId, Long programOutcomeId) {
        Optional<LearningOutcomeProgramOutcome> relationship = learningOutcomeProgramOutcomeRepository.findByLearningOutcomeIdAndProgramOutcomeId(learningOutcomeId, programOutcomeId);
        return relationship.orElse(null);
    }
}
