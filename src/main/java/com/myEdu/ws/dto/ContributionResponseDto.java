package com.myEdu.ws.dto;


import com.myEdu.ws.model.LearningOutcomeProgramOutcome;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ContributionResponseDto {

    private List<LearningOutcomeProgramOutcome> contributions;

}
