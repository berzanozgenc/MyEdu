package com.myEdu.ws.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssessmentRequest {

    private String name;
    private Double contribution;
    private Double averageGrade;
    private Long generalAssessmentId;
}
