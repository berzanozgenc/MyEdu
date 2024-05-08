package com.myEdu.ws.dto;

import com.myEdu.ws.model.AssessmentLearningOutcomeContribution;
import com.myEdu.ws.model.LearningOutcomeProgramOutcome;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AlocContributionResponseDto {

    private List<AssessmentLearningOutcomeContribution> contributions;

}
