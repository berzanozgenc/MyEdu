package com.myEdu.ws.service;

import com.myEdu.ws.model.Course;
import com.myEdu.ws.repository.GeneralAssesmentRepository;
import com.myEdu.ws.model.GeneralAssesment;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GeneralAssesmentService {

    private final GeneralAssesmentRepository generalAssesmentRepository;

    @Autowired
    public GeneralAssesmentService(GeneralAssesmentRepository generalAssesmentRepository) {
        this.generalAssesmentRepository = generalAssesmentRepository;
    }

    public boolean isTotalContributionUnderLimit(Course course, double newAssesmentContribution) {
        List<GeneralAssesment> assessmentsForCourse = generalAssesmentRepository.findByCourse(course);
        double totalContribution = assessmentsForCourse.stream()
                .mapToDouble(GeneralAssesment::getTotalContribution)
                .sum();

        System.out.println(totalContribution + newAssesmentContribution);
        return totalContribution + newAssesmentContribution <= 100.0;
    }
    public GeneralAssesment addGeneralAssesment(GeneralAssesment generalAssesment) {
        return generalAssesmentRepository.save(generalAssesment);
    }

    public void deleteGeneralAssesmentById(long generalAssesmentId) {
        generalAssesmentRepository.deleteById(generalAssesmentId);
    }

    public Optional<GeneralAssesment> findGeneralAssesmentById(long generalAssesmentId) {
        return generalAssesmentRepository.findById(generalAssesmentId);
    }

    public List<GeneralAssesment> getAllGeneralAssesments() {
        return generalAssesmentRepository.findAll();
    }

    public void updateTotalContributionForCourse(Course course, long generalAssesmentId, double newAssesmentContribution) {
        Optional<GeneralAssesment> existingAssessment = generalAssesmentRepository.findById(generalAssesmentId);

        if (existingAssessment.isPresent()) {
            GeneralAssesment assessmentToUpdate = existingAssessment.get();
            double currentContribution = assessmentToUpdate.getTotalContribution();

            // Güncellenen katkı oranını kontrol et
            if (isTotalContributionUnderLimit(course, currentContribution - assessmentToUpdate.getTotalContribution() + newAssesmentContribution)) {
                // Yeni katkı değerini setle
                assessmentToUpdate.setTotalContribution(newAssesmentContribution);
                generalAssesmentRepository.save(assessmentToUpdate);
            } else {
                // Eğer sınır aşılırsa bir hata işleme alınabilir
                throw new IllegalArgumentException("Toplam değerlendirme katkısı limiti aşıldı!");
            }
        } else {
            // Eğer belirtilen ID ile bir değerlendirme bulunamazsa bir hata işleme alınabilir
            throw new EntityNotFoundException("GeneralAssesment not found with id: " + generalAssesmentId);
        }
    }


}
