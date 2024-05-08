package com.myEdu.ws.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentGradeListDto {
    private List<StudentGradeDto> studentGradeDTOList;
}
