package com.myEdu.ws.dto;

import com.myEdu.ws.model.LearningOutcome;
import com.myEdu.ws.model.ProgramOutcome;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentProgramOutcomeRequest {
    private Long userId;
    private List<ProgramOutcome> programOutcomeList;
}
