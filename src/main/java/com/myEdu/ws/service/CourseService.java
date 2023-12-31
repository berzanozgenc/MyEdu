package com.myEdu.ws.service;

import com.myEdu.ws.dto.CourseDto;
import com.myEdu.ws.model.Course;
import com.myEdu.ws.model.GeneralAssesment;
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

        GeneralAssesment generalAssesment = new GeneralAssesment();
        generalAssesment.setName("quiz");
        generalAssesment.setTotalContribution(20d);
        generalAssesment.setCourse(course);
        List<GeneralAssesment> generalAssesmentList = new ArrayList<>();
        generalAssesmentList.add(generalAssesment);
        course.setGeneralAssesment(generalAssesmentList);

       return courseRepository.save(course).getCourseId();
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
