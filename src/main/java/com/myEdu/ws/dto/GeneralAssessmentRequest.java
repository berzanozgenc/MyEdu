package com.myEdu.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GeneralAssessmentRequest {

    private Long courseId;
    private String name;
    private Double totalContribution;

}
