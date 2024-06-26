package com.myEdu.ws.controller;

import com.myEdu.ws.dto.StudentProgramOutcomeRequest;
import com.myEdu.ws.model.ProgramOutcome;
import com.myEdu.ws.model.StudentProgramOutcome;
import com.myEdu.ws.repository.StudentProgramOutcomeRepository;
import com.myEdu.ws.service.StudentProgramOutcomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student-program-outcome")
@RequiredArgsConstructor
public class StudentProgramOutcomeController {

    @Autowired
    private final StudentProgramOutcomeRepository studentProgramOutcomeRepository;

   @Autowired
   private final StudentProgramOutcomeService studentProgramOutcomeService;

    @PostMapping("/{courseId}")
    public ResponseEntity<String> createStudentProgramOutcome(
            @RequestBody StudentProgramOutcomeRequest request,
            @PathVariable Long courseId) {
        boolean updated = false;
        boolean created = false;
        List<ProgramOutcome> programOutcomeList = request.getProgramOutcomeList();
        Long student_id = request.getUserId();
        for (ProgramOutcome programOutcome : programOutcomeList){
            StudentProgramOutcome record = studentProgramOutcomeRepository.findByStudentUserIdAndProgramOutcomeIdAndCourseCourseId(student_id,programOutcome.getId(), courseId);
            if(record == null){
                String temp = studentProgramOutcomeService.createStudentProgramOutcome(student_id,programOutcome.getId(), courseId);
                created = true;
                updated = false;
            }
            else{
                String temp = studentProgramOutcomeService.updateStudentProgramOutcome(student_id, programOutcome.getId(), courseId);
                updated = true;
                created = false;
            }
        }
        if(created || updated)
            return new ResponseEntity<>("Changes are persisted", HttpStatus.OK);
        else
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/user/{userId}/course/{courseId}/program-outcome")
    public List<StudentProgramOutcome> getProgramOutcomesForUser(@PathVariable Long userId, @PathVariable Long courseId, @RequestBody List<Long> programOutcomeIds) {
        return studentProgramOutcomeService.getByUserIdAndProgramOutcomeIds(userId, courseId, programOutcomeIds);
    }

    @GetMapping("/getByStudentAndProgram/student/{studentId}/program/{programOutcomeId}")
    public List<StudentProgramOutcome> getOutcomesByStudentAndProgramOutcome(
            @PathVariable Long studentId,
            @PathVariable Long programOutcomeId) {
        return studentProgramOutcomeService.getOutcomesByStudentAndProgramOutcome(studentId, programOutcomeId);
    }
}
