package com.myEdu.ws.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContributionRequest {

    private Long assessmentId;
    private Long learningOutcomeId;
    private Double contribution;

}
