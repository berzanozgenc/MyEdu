package com.myEdu.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentAssessmentDTO {

    private Long user_id;
    private Long assessmentId;
    private double grade;

}
