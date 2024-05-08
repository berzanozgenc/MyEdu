package com.myEdu.ws.dto;

import com.myEdu.ws.model.AssessmentLearningOutcomeContribution;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AssessmentContributionListDto {
    private List<AssessmentContributionDto> assessmentContributionDTOList;
}
