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
    public ResponseEntity<String> createStudentAssessment(@RequestBody StudentAssessmentListDto requestListDto) {
        boolean created = false;
        boolean updated = false;
        for (StudentAssessmentDTO request: requestListDto.getStAsList()){
            StudentAssessment record = studentAssessmentService.getStudentAssessment(request.getAssessmentId(), request.getUser_id());
            if(record == null) {
                StudentAssessment relationship = studentAssessmentService.createStudentAssessment(request.getUser_id(), request.getAssessmentId(), request.getGrade());
                created = true;
                updated = false;
            }
            else {
                record.setGrade(request.getGrade());
                studentAssessmentService.updateGrade(record);
                studentAssessmentService.updateAverageGradesForAssessments();
                updated = true;
                created = false;
            }
        }
        if(created || updated)
            return new ResponseEntity<>("Changes are persisted",HttpStatus.OK);
        else
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
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
