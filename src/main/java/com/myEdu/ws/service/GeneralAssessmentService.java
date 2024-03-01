package com.myEdu.ws.service;

import com.myEdu.ws.model.Course;
import com.myEdu.ws.model.GeneralAssessment;
import com.myEdu.ws.repository.GeneralAssessmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GeneralAssessmentService {

    private final GeneralAssessmentRepository generalAssesmentRepository;

    @Autowired
    public GeneralAssessmentService(GeneralAssessmentRepository generalAssesmentRepository) {
        this.generalAssesmentRepository = generalAssesmentRepository;
    }

    private boolean isContributionUnderLimit(double totalContribution) {
        return totalContribution <= 100.0;
    }

    public GeneralAssessment addGeneralAssessment(GeneralAssessment generalAssessment) {
        Course course = generalAssessment.getCourse();
        List<GeneralAssessment> assessmentsForCourse = generalAssesmentRepository.findByCourse(course);
        double totalContribution = assessmentsForCourse.stream()
                .mapToDouble(GeneralAssessment::getTotalContribution)
                .sum();

        if (isContributionUnderLimit(totalContribution + generalAssessment.getTotalContribution())) {
            return generalAssesmentRepository.save(generalAssessment);
        } else {
            throw new IllegalArgumentException("Toplam değerlendirme katkısı limiti aşıldı!");
        }
    }



    public void deleteGeneralAssesmentById(long generalAssesmentId) {
        generalAssesmentRepository.deleteById(generalAssesmentId);
    }

    public Optional<GeneralAssessment> findGeneralAssesmentById(long generalAssesmentId) {
        return generalAssesmentRepository.findById(generalAssesmentId);
    }

    public List<GeneralAssessment> getGeneralAssessmentsByCourseId(Long courseId) {
        return generalAssesmentRepository.findByCourse_courseId(courseId);
    }

    public Optional<GeneralAssessment> findById(Long id) {
        return generalAssesmentRepository.findById(id);
    }

    public List<GeneralAssessment> getAllGeneralAssesments() {
        return generalAssesmentRepository.findAll();
    }

    public void updateTotalContributionForCourse(Course course, long generalAssessmentId, double newAssessmentContribution) {
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
                generalAssesmentRepository.save(assessmentToUpdate);
            } else {
                throw new IllegalArgumentException("Toplam değerlendirme katkısı limiti aşıldı!");
            }
        } else {
            throw new EntityNotFoundException("GeneralAssessment not found with id: " + generalAssessmentId);
        }
    }
}
