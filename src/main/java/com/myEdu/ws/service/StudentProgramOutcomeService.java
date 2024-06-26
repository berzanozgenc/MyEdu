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
    private final CourseProgramOutcomeResultsRepository courseProgramOutcomeResultsRepository;

    @Autowired
    private final CourseRepository courseRepository;

    @Autowired
    private final LearningOutcomeProgramOutcomeRepository learningOutcomeProgramOutcomeRepository;

    @Autowired
    private final StudentLearningOutcomeRepository studentLearningOutcomeRepository;

    public String createStudentProgramOutcome(Long userId, Long programOutcomeId, Long courseId) {
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + userId));
        Optional<ProgramOutcome> programOutcomeOpt = programOutcomeRepository.findById(programOutcomeId);
        ProgramOutcome programOutcome = programOutcomeOpt.get();
        double levelOfProvision = 0;
        StudentProgramOutcome studentProgramOutcome = new StudentProgramOutcome();
        studentProgramOutcome.setStudent(student);
        Optional<Course> course = courseRepository.findById(courseId);
        studentProgramOutcome.setCourse(course.get());
        studentProgramOutcome.setProgramOutcome(programOutcome);
        levelOfProvision = calculateLevelOfProvision(student,programOutcome, courseId);
        studentProgramOutcome.setLevelOfProvision(levelOfProvision);
        studentProgramOutcomeRepository.save(studentProgramOutcome);
        return ("Ok");

    }

    public String updateStudentProgramOutcome(Long userId, Long programOutcomeId, Long courseId) {
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + userId));
        ProgramOutcome programOutcome = programOutcomeRepository.findById(programOutcomeId)
                .orElseThrow(() -> new RuntimeException("Program Outcome not found with id: " + programOutcomeId));

        StudentProgramOutcome studentProgramOutcome = studentProgramOutcomeRepository.findByStudentUserIdAndProgramOutcomeIdAndCourseCourseId(userId,programOutcomeId, courseId);
        double levelOfProvision = calculateLevelOfProvision(student, programOutcome, courseId);
        studentProgramOutcome.setLevelOfProvision(levelOfProvision);
        studentProgramOutcomeRepository.save(studentProgramOutcome);

        return "Ok";
    }

    public double calculateLevelOfProvision(Student student, ProgramOutcome programOutcome, Long courseId) {
        Course c = courseRepository.findById(courseId).orElse(null);
            List<LearningOutcomeProgramOutcome> mappings = learningOutcomeProgramOutcomeRepository.findByProgramOutcomeAndCourse(programOutcome,c);
            double score = 0.0;
            for (LearningOutcomeProgramOutcome mapping : mappings) {
                LearningOutcome learningOutcome = mapping.getLearningOutcome();
                double contribution = mapping.getContribution();
                StudentLearningOutcome studentLearningOutcome = studentLearningOutcomeRepository.findByStudentUserIdAndLearningOutcomeId(student.getUserId(),learningOutcome.getId());
                double learningOutcomeScoreSum = studentLearningOutcome.getScoreSum();
                double valueToAdd = (learningOutcomeScoreSum * contribution) / 100;
                score += valueToAdd;
        }
            Optional<Course> course = courseRepository.findById(courseId);
            Optional<CourseProgramOutcomeResults> courseProgramOutcomeResults = courseProgramOutcomeResultsRepository.findByCourseAndProgramOutcome(course.get(),programOutcome);

        return score / courseProgramOutcomeResults.get().getAssessmentValue() * 100;

    }

    public List<StudentProgramOutcome> getByUserIdAndProgramOutcomeIds(Long userId, Long courseId, List<Long> programOutcomeIds) {
        return studentProgramOutcomeRepository.findByStudentUserIdAndCourseCourseIdAndProgramOutcomeIdIn(userId, courseId, programOutcomeIds);
    }

    public List<StudentProgramOutcome> getOutcomesByStudentAndProgramOutcome(Long studentId, Long programOutcomeId) {
        return studentProgramOutcomeRepository.findByStudentUserIdAndProgramOutcomeId(studentId, programOutcomeId);
    }

}
