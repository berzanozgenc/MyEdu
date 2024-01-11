package com.myEdu.ws.controller;

import com.myEdu.ws.model.Course;
import com.myEdu.ws.model.GeneralAssesment;
import com.myEdu.ws.repository.CourseRepository;
import com.myEdu.ws.service.GeneralAssesmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/generalAssesment")
public class GeneralAssesmentController {

    private final GeneralAssesmentService generalAssesmentService;
    private final CourseRepository courseRepository;

    @Autowired
    public GeneralAssesmentController(GeneralAssesmentService generalAssesmentService, CourseRepository courseRepository) {
        this.generalAssesmentService = generalAssesmentService;
        this.courseRepository = courseRepository;
    }

    @PostMapping("/create-generalAssesment")
    public GeneralAssesment addGeneralAssesment(@RequestBody GeneralAssesment generalAssesment) {
        Course course = generalAssesment.getCourse();
        if (generalAssesmentService.isTotalContributionUnderLimit(course, generalAssesment.getTotalContribution())) {
            return generalAssesmentService.addGeneralAssesment(generalAssesment);
        } else {
            // Toplam değerlendirme katkısının limiti aşıldı
            // Burada yapılacak işlemi belirleyebilirsiniz, örneğin hata mesajı döndürebilir veya istenilen işlemi yapabilirsiniz.
            // Örnek olarak:
            throw new IllegalArgumentException("Toplam değerlendirme katkısı limiti aşıldı!");
        }
    }

    @DeleteMapping("/delete-generalAssesment/{id}")
    public void deleteGeneralAssesment(@PathVariable("id") long generalAssesmentId) {
        generalAssesmentService.deleteGeneralAssesmentById(generalAssesmentId);
    }

    @GetMapping("/get-generalAssesmentById")
    public GeneralAssesment getGeneralAssesmentById(@PathVariable("id") long generalAssesmentId) {
        return generalAssesmentService.findGeneralAssesmentById(generalAssesmentId)
                .orElseThrow(() -> new EntityNotFoundException("GeneralAssesment not found with id: " + generalAssesmentId));
    }

    @PutMapping("/updateTotalContributionForCourse/{courseId}")
    public ResponseEntity<String> updateTotalContributionForCourse(@PathVariable Long courseId, @RequestParam long generalAssesmentId, @RequestParam double newAssesmentContribution) {
        Optional<Course> course = courseRepository.findById(courseId);

        if (course.isPresent()) {
            generalAssesmentService.updateTotalContributionForCourse(course.get(), generalAssesmentId, newAssesmentContribution);
            return ResponseEntity.ok("Total contribution updated successfully for Course ID: " + courseId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course with ID: " + courseId + " not found");
        }
    }

}
