package com.myEdu.ws.service;

import com.myEdu.ws.dto.CourseDto;
import com.myEdu.ws.model.Course;
import com.myEdu.ws.model.GeneralAssessment;
import com.myEdu.ws.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseService {

    private CourseRepository courseRepository;

    public List<String> getAllCourseNameAndCode() {
        List<Course> courses = courseRepository.findAll();
        List<String> names = new ArrayList<>();

        for(Course c: courses) {
            names.add(c.getCourseName() + "-" + c.getCode());
        }

        return names;
    }

    public Long createCourse(CourseDto courseDto) {
        Course course = new Course();
        course.setCourseName(courseDto.getCourseName());
        course.setCode(courseDto.getCode());
        course.setEcts(courseDto.getEcts());
        course.setCredit(courseDto.getCredit());
        course.setSection(courseDto.getSection());
        course.setSemester(courseDto.getSemester());

        GeneralAssessment generalAssessment = new GeneralAssessment();
        generalAssessment.setName("quiz");
        generalAssessment.setTotalContribution(20d);
        generalAssessment.setCourse(course);
        List<GeneralAssessment> generalAssessmentList = new ArrayList<>();
        generalAssessmentList.add(generalAssessment);
        course.setGeneralAssessment(generalAssessmentList);

       return courseRepository.save(course).getCourseId();
    }

    public String getCourseNameAndCodeById(Long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        return (course != null) ? course.getCourseName() + " - " + course.getCode() : null;
    }


    public Course updateCourse(Long courseId, Course updatedCourse) {
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + courseId));

        // Güncellenmiş verileri mevcut kursa kopyala
        BeanUtils.copyProperties(updatedCourse, existingCourse, "courseId");

        // Kursu güncelle ve kaydet
        return courseRepository.save(existingCourse);
    }

    public void deleteCourse(Long courseId) {
        Optional<Course> optional = courseRepository.findById(courseId);
        optional.ifPresent(course -> courseRepository.delete(course));
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}
