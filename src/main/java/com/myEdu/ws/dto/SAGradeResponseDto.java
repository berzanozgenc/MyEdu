package com.myEdu.ws.dto;

import com.myEdu.ws.model.StudentAssessment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SAGradeResponseDto {

    private List<StudentAssessment> grades;

}
