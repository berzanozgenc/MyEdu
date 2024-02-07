package com.myEdu.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LearningOutcomeProgramOutcomeDTO {
    private Long learningOutcomeId;
    private Long programOutcomeId;
    private double contribution;

}
