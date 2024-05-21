package com.myEdu.ws.dto;

import com.myEdu.ws.model.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseDto {
    private String courseName;
    private String semester;
    private String code;
    private int ects;
    private int credit;
    private int section;
    private Department department;
}
