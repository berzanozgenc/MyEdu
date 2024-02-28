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

    public boolean isTotalContributionUnderLimit(Course course, double newAssessmentContribution) {
        List<GeneralAssessment> assessmentsForCourse = generalAssesmentRepository.findByCourse(course);
        double totalContribution = assessmentsForCourse.stream()
                .mapToDouble(GeneralAssessment::getTotalContribution)
                .sum();

        System.out.println(totalContribution + newAssessmentContribution);

        if (totalContribution + newAssessmentContribution <= 100.0) {
            return true;
        } else {
            throw new IllegalArgumentException("Toplam değerlendirme katkısı limiti aşıldı!");
        }
    }

    public GeneralAssessment addGeneralAssesment(GeneralAssessment generalAssessment) {
        Course course = generalAssessment.getCourse();

        if (isTotalContributionUnderLimit(course, generalAssessment.getTotalContribution())) {
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

    public void updateTotalContributionForCourse(Course course, long generalAssesmentId, double newAssesmentContribution) {
        Optional<GeneralAssessment> existingAssessment = generalAssesmentRepository.findById(generalAssesmentId);

        if (existingAssessment.isPresent()) {
            GeneralAssessment assessmentToUpdate = existingAssessment.get();
            double currentContribution = assessmentToUpdate.getTotalContribution();

            if (isTotalContributionUnderLimit(course, currentContribution - assessmentToUpdate.getTotalContribution() + newAssesmentContribution)) {

                assessmentToUpdate.setTotalContribution(newAssesmentContribution);
                generalAssesmentRepository.save(assessmentToUpdate);
            } else {
                throw new IllegalArgumentException("Toplam değerlendirme katkısı limiti aşıldı!");
            }
        } else {
            throw new EntityNotFoundException("GeneralAssessment not found with id: " + generalAssesmentId);
        }
    }
}
