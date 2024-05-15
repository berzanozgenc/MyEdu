package com.myEdu.ws.dto;

import com.myEdu.ws.model.LearningOutcome;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentLearningOutcomeRequest {
    private Long userId;
    private List<LearningOutcome> learningOutcomeList;
}
