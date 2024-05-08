package com.myEdu.ws.controller;

import com.myEdu.ws.dto.*;
import com.myEdu.ws.model.StudentAssessment;
import com.myEdu.ws.service.StudentAssessmentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Transactional
@RequestMapping("/student-assessment")
public class StudentAssessmentController {

    @Autowired
    private StudentAssessmentService studentAssessmentService;

    @PostMapping("/create")
    public ResponseEntity<StudentAssessment> createStudentAssessment(@RequestBody StudentAssessmentDTO request) {
        StudentAssessment record = studentAssessmentService.getStudentAssessment(request.getAssessmentId(), request.getUser_id());
        if(record == null) {
            StudentAssessment relationship = studentAssessmentService.createStudentAssessment(request.getUser_id(), request.getAssessmentId(), request.getGrade());
            if (relationship != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(relationship);
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        else {
            record.setGrade(request.getGrade());
            studentAssessmentService.updateGrade(record);
            return new ResponseEntity<>(record, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteStudentAssessment(@RequestParam Long userId,
                                                          @RequestParam Long assessmentId) {
        return studentAssessmentService.deleteStudentAssessment(userId, assessmentId);
    }

    @GetMapping("/get-grade")
    public ResponseEntity<Double> getStudentGrade(@RequestParam Long userId,
                                                  @RequestParam Long assessmentId) {
        return studentAssessmentService.getStudentGrade(userId, assessmentId);
    }

    @PostMapping("/grade")
    public ResponseEntity<SAGradeResponseDto> getGradeByStudentAssessmentIds(@RequestBody StudentGradeListDto listDto) {
        List<StudentGradeDto> records = listDto.getStudentGradeDTOList();

        SAGradeResponseDto saGradeResponseDto = new SAGradeResponseDto();
        List<StudentAssessment> list = new ArrayList<>();
        for(StudentGradeDto dto: records){
            StudentAssessment studentAssessment = studentAssessmentService.getStudentAssessment(dto.getAssessmentId(), dto.getUser_id());
            if (studentAssessment != null) {
                list.add(studentAssessment);
            }
        }
        saGradeResponseDto.setGrades(list);
        return new ResponseEntity<>(saGradeResponseDto, HttpStatus.OK);
    }
}
