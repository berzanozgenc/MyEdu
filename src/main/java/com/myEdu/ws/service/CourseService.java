package com.myEdu.ws.service;

import com.myEdu.ws.dto.CourseDto;
import com.myEdu.ws.model.*;
import com.myEdu.ws.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    GeneralAssessmentRepository generalAssessmentRepository;

    @Autowired
    GeneralAssessmentService generalAssessmentService;

    @Autowired
    LearningOutcomeRepository learningOutcomeRepository;

    @Autowired
    LearningOutcomeService learningOutcomeService;

    @Autowired
    ProgramOutcomeRepository programOutcomeRepository;

    @Autowired
    ProgramOutcomeService programOutcomeService;

    private CourseRepository courseRepository;

    public Long createCourse(CourseDto courseDto) {
        Course course = new Course();
        course.setCourseName(courseDto.getCourseName());
        course.setCode(courseDto.getCode());
        course.setEcts(courseDto.getEcts());
        course.setCredit(courseDto.getCredit());
        course.setSection(courseDto.getSection());
        course.setSemester(courseDto.getSemester());
        course.setDepartment(courseDto.getDepartment());

        GeneralAssessment generalAssessment = new GeneralAssessment();
        generalAssessment.setName("Quiz");
        generalAssessment.setTotalContribution(20d);
        generalAssessment.setCourse(course);
        List<GeneralAssessment> generalAssessmentList = new ArrayList<>();
        generalAssessmentList.add(generalAssessment);
        course.setGeneralAssessment(generalAssessmentList);

       return courseRepository.save(course).getCourseId();
    }

    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElse(null);
    }

    public Course updateCourse(Long courseId, Course updatedCourse) {
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + courseId));

        // Güncellenmiş verileri mevcut kursa kopyala
        BeanUtils.copyProperties(updatedCourse, existingCourse, "courseId");

        // Kursu güncelle ve kaydet
        return courseRepository.save(existingCourse);
    }

    @Transactional
    public void deleteCourse(Long courseId) {
        Optional<Course> optional = courseRepository.findById(courseId);
        List<GeneralAssessment> generalAssessments = generalAssessmentRepository.findByCourse(optional.get());
        for (GeneralAssessment generalAssessment : generalAssessments){
            generalAssessmentService.deleteGeneralAssesmentById(generalAssessment.getGeneralAssesmentId());
        }
        List<LearningOutcome> learningOutcomes = learningOutcomeRepository.findByCourseId(courseId);
        for (LearningOutcome learningOutcome : learningOutcomes){
            learningOutcomeService.deleteLearningOutcome(learningOutcome.getId());
        }
        List<ProgramOutcome> programOutcomes = programOutcomeRepository.findByCourseCourseId(courseId);
        for (ProgramOutcome programOutcome: programOutcomes){
            programOutcomeService.deleteProgramOutcome(programOutcome.getId());
        }
        optional.ifPresent(course -> courseRepository.delete(course));
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getDepartmentCourses(Long departmentId) {
        Optional<Department> departmentOptional = departmentRepository.findById(departmentId);
        if(departmentOptional.isPresent()){
            Department department = departmentOptional.get();
            return courseRepository.findAllByDepartment(department);
        }
        else
            return null;
    }
}
