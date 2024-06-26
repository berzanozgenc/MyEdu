package com.myEdu.ws.service;

import com.myEdu.ws.dto.LearningOutcomeRequest;
import com.myEdu.ws.model.Course;
import com.myEdu.ws.model.LearningOutcome;
import com.myEdu.ws.model.StudentLearningOutcome;
import com.myEdu.ws.repository.CourseRepository;
import com.myEdu.ws.repository.LearningOutcomeProgramOutcomeRepository;
import com.myEdu.ws.repository.LearningOutcomeRepository;
import com.myEdu.ws.repository.StudentLearningOutcomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.myEdu.ws.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LearningOutcomeService {

    @Autowired
    private LearningOutcomeRepository learningOutcomeRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    LearningOutcomeProgramOutcomeRepository learningOutcomeProgramOutcomeRepository;

    @Autowired
    StudentLearningOutcomeRepository studentLearningOutcomeRepository;

    public List<LearningOutcome> getAllLearningOutcomes() {
        return learningOutcomeRepository.findAll();
    }

    public Optional<LearningOutcome> getLearningOutcomeById(Long id) {
        return learningOutcomeRepository.findById(id);
    }

    public LearningOutcome createLearningOutcome(LearningOutcomeRequest request) {
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new NotFoundException("Course not found with id: " + request.getCourseId()));

        LearningOutcome learningOutcome = new LearningOutcome();
        learningOutcome.setDescription(request.getDescription());
        learningOutcome.setDesiredTarget(request.getDesiredTarget());
        learningOutcome.setCourse(course);

        return learningOutcomeRepository.save(learningOutcome);
    }

    public List<LearningOutcome> getByCourseId(Long courseId) {
        return learningOutcomeRepository.findByCourseId(courseId);
    }

    public LearningOutcome updateLearningOutcome(Long id, LearningOutcome updatedLearningOutcome) {
        LearningOutcome existingLearningOutcome = learningOutcomeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("LearningOutcome not found with id: " + id));

        existingLearningOutcome.setDescription(updatedLearningOutcome.getDescription());
        existingLearningOutcome.setDesiredTarget(updatedLearningOutcome.getDesiredTarget());

        return learningOutcomeRepository.save(existingLearningOutcome);
    }

    @Transactional
    public void deleteLearningOutcome(Long id) {
        studentLearningOutcomeRepository.deleteAllByLearningOutcome(id);
        learningOutcomeProgramOutcomeRepository.deleteAllByLearningOutcome(id);
        learningOutcomeRepository.deleteById(id);
    }
}
