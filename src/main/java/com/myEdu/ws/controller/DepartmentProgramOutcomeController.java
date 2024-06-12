package com.myEdu.ws.controller;

import com.myEdu.ws.model.Department;
import com.myEdu.ws.model.DepartmentProgramOutcome;
import com.myEdu.ws.model.ProgramOutcome;
import com.myEdu.ws.service.DepartmentProgramOutcomeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department/program-outcomes")
public class DepartmentProgramOutcomeController {

    @Autowired
    DepartmentProgramOutcomeService departmentProgramOutcomeService;

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<DepartmentProgramOutcome>> getDepartmentProgramOutcomesByDepartmentId(@PathVariable Long departmentId) {
        List<DepartmentProgramOutcome> departmentProgramOutcomes = departmentProgramOutcomeService.getByDepartmentId(departmentId);
        return ResponseEntity.ok().body(departmentProgramOutcomes);
    }

    @PostMapping("/{departmentId}")
    public ResponseEntity<DepartmentProgramOutcome> createDepartmentProgramOutcome(@PathVariable Long departmentId, @RequestBody DepartmentProgramOutcome departmentProgramOutcome) {
        DepartmentProgramOutcome createdDepartmentProgramOutcome = departmentProgramOutcomeService.createDepartmentProgramOutcome(departmentProgramOutcome, departmentId);
        if (createdDepartmentProgramOutcome != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartmentProgramOutcome);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentProgramOutcome> updateDepartmentProgramOutcome(@PathVariable Long id,
                                                               @RequestBody DepartmentProgramOutcome updatedDepartmentProgramOutcome) {
        DepartmentProgramOutcome departmentProgramOutcome = departmentProgramOutcomeService.updateDepartmentProgramOutcome(id, updatedDepartmentProgramOutcome);
        return new ResponseEntity<>(departmentProgramOutcome, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteDepartmentProgramOutcome(@PathVariable Long id) {
        departmentProgramOutcomeService.deleteDepartmentProgramOutcome(id);
        return ResponseEntity.noContent().build();
    }

}
