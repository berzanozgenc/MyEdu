package com.myEdu.ws.service;

import com.myEdu.ws.dto.CourseDto;
import com.myEdu.ws.model.Course;
import com.myEdu.ws.model.GeneralAssessment;
import com.myEdu.ws.repository.CourseRepository;
import lombok.AllArgsConstructor;
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

    public void updateCourse(Long courseId, CourseDto courseDto) {
        Optional<Course> optional = courseRepository.findById(courseId);
        if(optional.isPresent()){
            Course course = optional.get();
            course.setSemester(courseDto.getSemester() != null ? courseDto.getSemester() : course.getSemester());
        }
    }

    public void deleteCourse(Long courseId) {
        Optional<Course> optional = courseRepository.findById(courseId);
        optional.ifPresent(course -> courseRepository.delete(course));
    }
}
