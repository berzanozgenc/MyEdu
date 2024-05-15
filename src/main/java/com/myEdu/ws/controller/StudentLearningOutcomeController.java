package com.myEdu.ws.controller;

import com.myEdu.ws.dto.StudentLearningOutcomeRequest;
import com.myEdu.ws.model.LearningOutcome;
import com.myEdu.ws.model.StudentLearningOutcome;
import com.myEdu.ws.repository.StudentLearningOutcomeRepository;
import com.myEdu.ws.service.StudentLearningOutcomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student-learning-outcome")
@RequiredArgsConstructor
public class StudentLearningOutcomeController {

    private final StudentLearningOutcomeService studentLearningOutcomeService;

    @Autowired
    private final StudentLearningOutcomeRepository studentLearningOutcomeRepository;

    @PostMapping
    public ResponseEntity<String> createStudentLearningOutcome(
            @RequestBody StudentLearningOutcomeRequest request) {
        boolean updated = false;
        boolean created = false;
        List<LearningOutcome> learningOutcomeList = request.getLearningOutcomeList();
        Long student_id = request.getUserId();
        for (LearningOutcome learningOutcome : learningOutcomeList){
            StudentLearningOutcome record = studentLearningOutcomeRepository.findByStudentUserIdAndLearningOutcomeId(student_id,learningOutcome.getId());
            if(record == null){
                String temp = studentLearningOutcomeService.createStudentLearningOutcome(student_id, learningOutcome.getId());
                created = true;
                updated = false;
            }
            else{
                String temp = studentLearningOutcomeService.updateStudentLearningOutcome(student_id,learningOutcome.getId());
                updated = true;
                created = false;
            }
        }
        if(created || updated)
            return new ResponseEntity<>("Changes are persisted", HttpStatus.OK);
        else
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }
}
