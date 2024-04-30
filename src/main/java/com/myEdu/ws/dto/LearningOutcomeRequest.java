package com.myEdu.ws.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LearningOutcomeRequest {

    private String description;
    private Long courseId;
    private double desiredTarget;

}
