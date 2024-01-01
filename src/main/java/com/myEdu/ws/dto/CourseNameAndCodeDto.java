package com.myEdu.ws.dto;

import com.myEdu.ws.model.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseNameAndCodeDto {

    private List<String> coursesNameAndCode;
}
