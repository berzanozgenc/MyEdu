package com.myEdu.ws.service;

import com.myEdu.ws.model.*;
import com.myEdu.ws.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentProgramOutcomeService {

    @Autowired
    private final StudentRepository studentRepository;

    @Autowired
    private final ProgramOutcomeRepository programOutcomeRepository;

    @Autowired
    private final StudentProgramOutcomeRepository studentProgramOutcomeRepository;

    @Autowired
    private final LearningOutcomeProgramOutcomeRepository learningOutcomeProgramOutcomeRepository;

    @Autowired
    private final StudentLearningOutcomeRepository studentLearningOutcomeRepository;

    public String createStudentProgramOutcome(Long userId, Long programOutcomeId) {
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + userId));
        Optional<ProgramOutcome> programOutcomeOpt = programOutcomeRepository.findById(programOutcomeId);
        ProgramOutcome programOutcome = programOutcomeOpt.get();
        double levelOfProvision = 0;
        StudentProgramOutcome studentProgramOutcome = new StudentProgramOutcome();
        studentProgramOutcome.setStudent(student);
        studentProgramOutcome.setProgramOutcome(programOutcome);
        levelOfProvision = calculateLevelOfProvision(student,programOutcome);
        studentProgramOutcome.setLevelOfProvision(levelOfProvision);
        studentProgramOutcomeRepository.save(studentProgramOutcome);

        return ("Ok");

    }

    public String updateStudentProgramOutcome(Long userId, Long programOutcomeId) {
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + userId));
        ProgramOutcome programOutcome = programOutcomeRepository.findById(programOutcomeId)
                .orElseThrow(() -> new RuntimeException("Program Outcome not found with id: " + programOutcomeId));

        StudentProgramOutcome studentProgramOutcome = studentProgramOutcomeRepository.findByStudentUserIdAndProgramOutcomeId(userId,programOutcomeId);
        double levelOfProvision = calculateLevelOfProvision(student, programOutcome);
        studentProgramOutcome.setLevelOfProvision(levelOfProvision);
        studentProgramOutcomeRepository.save(studentProgramOutcome);

        return "Ok";
    }

    public double calculateLevelOfProvision(Student student, ProgramOutcome programOutcome) {
            List<LearningOutcomeProgramOutcome> mappings = learningOutcomeProgramOutcomeRepository.findByProgramOutcome(programOutcome);
            double score = 0.0;
            for (LearningOutcomeProgramOutcome mapping : mappings) {
                LearningOutcome learningOutcome = mapping.getLearningOutcome();
                double contribution = mapping.getContribution();
                StudentLearningOutcome studentLearningOutcome = studentLearningOutcomeRepository.findByStudentUserIdAndLearningOutcomeId(student.getUserId(),learningOutcome.getId());
                double learningOutcomeScoreSum= studentLearningOutcome.getScoreSum();
                double valueToAdd = (learningOutcomeScoreSum * contribution) / 100;
                score += valueToAdd;
        }
        return score / programOutcome.getAssessmentValue() * 100;

    }

    public List<StudentProgramOutcome> getByUserIdAndProgramOutcomeIds(Long userId, List<Long> programOutcomeIds) {
        return studentProgramOutcomeRepository.findByStudentUserIdAndProgramOutcomeIdIn(userId, programOutcomeIds);
    }

}
