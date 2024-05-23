package com.myEdu.ws.service;

import com.myEdu.ws.model.Assessment;
import com.myEdu.ws.model.Course;
import com.myEdu.ws.model.GeneralAssessment;
import com.myEdu.ws.repository.AssessmentRepository;
import com.myEdu.ws.repository.CourseRepository;
import com.myEdu.ws.repository.GeneralAssessmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GeneralAssessmentService {

    private final GeneralAssessmentRepository generalAssesmentRepository;
    private final CourseRepository courseRepository;

    private final AssessmentRepository assessmentRepository;

    private final AssessmentService assessmentService;

    @Autowired
    public GeneralAssessmentService(GeneralAssessmentRepository generalAssesmentRepository, CourseRepository courseRepository, AssessmentRepository assessmentRepository, AssessmentService assessmentService) {
        this.generalAssesmentRepository = generalAssesmentRepository;
        this.courseRepository = courseRepository;
        this.assessmentRepository = assessmentRepository;
        this.assessmentService = assessmentService;
    }

    private boolean isContributionUnderLimit(double totalContribution) {
        return totalContribution <= 100.0;
    }

    public GeneralAssessment addGeneralAssessment(Long courseId, String name, double totalContribution) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));

        List<GeneralAssessment> assessmentsForCourse = generalAssesmentRepository.findByCourse(course);
        double totalContributionForCourse = assessmentsForCourse.stream()
                .mapToDouble(GeneralAssessment::getTotalContribution)
                .sum();

        if (isContributionUnderLimit(totalContributionForCourse + totalContribution)) {
            GeneralAssessment generalAssessment = new GeneralAssessment();
            generalAssessment.setCourse(course);
            generalAssessment.setName(name);
            generalAssessment.setTotalContribution(totalContribution);
            return generalAssesmentRepository.save(generalAssessment);
        } else {
            throw new IllegalArgumentException("Toplam değerlendirme katkısı limiti aşıldı!");
        }
    }

    public void deleteGeneralAssesmentById(long generalAssesmentId) {
        try {
            Optional<GeneralAssessment> generalAssessment = generalAssesmentRepository.findById(generalAssesmentId);
            List<Assessment> assessments = assessmentRepository.findByGeneralAssessment(generalAssessment.get());
            for (Assessment assessment: assessments){
                assessmentService.deleteAssessmentById(assessment.getAssessmentId());
            }
            generalAssesmentRepository.deleteById(generalAssesmentId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("GeneralAssessment not found with id: " + generalAssesmentId);
        }
    }

    public Optional<GeneralAssessment> findGeneralAssesmentById(long generalAssesmentId) {
        return generalAssesmentRepository.findById(generalAssesmentId);
    }

    public List<GeneralAssessment> getGeneralAssessmentsByCourseId(Long courseId) {
        return generalAssesmentRepository.findByCourse_courseId(courseId);
    }

    public boolean isGeneralAssessmentQuestionBased(Long generalAssessmentId){
        GeneralAssessment generalAssessment = generalAssesmentRepository.findById(generalAssessmentId).orElse(null);
        if(generalAssessment != null){
            return generalAssessment.isQuestionBased();
        }
        return false;
    }

    public void toggleQuestionBased(Long generalAssessmentId) {
        GeneralAssessment generalAssessment = generalAssesmentRepository.findById(generalAssessmentId).orElse(null);
        if (generalAssessment != null) {
            generalAssessment.setQuestionBased(!generalAssessment.isQuestionBased());
            generalAssesmentRepository.save(generalAssessment);
        }
    }

    public Optional<GeneralAssessment> findById(Long id) {
        return generalAssesmentRepository.findById(id);
    }

    public List<GeneralAssessment> getAllGeneralAssesments() {
        return generalAssesmentRepository.findAll();
    }

    public void updateTotalContributionForCourse(Course course, long generalAssessmentId, double newAssessmentContribution, String newAssessmentName ) {
        Optional<GeneralAssessment> existingAssessment = generalAssesmentRepository.findById(generalAssessmentId);

        if (existingAssessment.isPresent()) {
            GeneralAssessment assessmentToUpdate = existingAssessment.get();
            List<GeneralAssessment> assessmentsForCourse = generalAssesmentRepository.findByCourse(course);
            double totalContribution = assessmentsForCourse.stream()
                    .mapToDouble(GeneralAssessment::getTotalContribution)
                    .sum();

            // Eski değerlendirmenin katkısını toplamdan çıkar
            totalContribution -= assessmentToUpdate.getTotalContribution();

            if (isContributionUnderLimit(totalContribution + newAssessmentContribution)) {
                assessmentToUpdate.setTotalContribution(newAssessmentContribution);
                assessmentToUpdate.setName(newAssessmentName);
                generalAssesmentRepository.save(assessmentToUpdate);
            } else {
                throw new IllegalArgumentException("Toplam değerlendirme katkısı limiti aşıldı!");
            }
        } else {
            throw new EntityNotFoundException("GeneralAssessment not found with id: " + generalAssessmentId);
        }
    }
}
