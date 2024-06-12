package com.myEdu.ws.service;

import com.myEdu.ws.exception.NotFoundException;
import com.myEdu.ws.model.Course;
import com.myEdu.ws.model.Department;
import com.myEdu.ws.model.DepartmentProgramOutcome;
import com.myEdu.ws.model.ProgramOutcome;
import com.myEdu.ws.repository.DepartmentProgramOutcomeRepository;
import com.myEdu.ws.repository.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentProgramOutcomeService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    DepartmentProgramOutcomeRepository departmentProgramOutcomeRepository;

    public DepartmentProgramOutcome createDepartmentProgramOutcome(DepartmentProgramOutcome departmentProgramOutcome, Long departmentId) {
        Department department = departmentRepository.findById(departmentId).orElse(null);
        if (department != null) {
            departmentProgramOutcome.setDepartment(department);
            return departmentProgramOutcomeRepository.save(departmentProgramOutcome);
        } else {
            return null;
        }
    }

    public DepartmentProgramOutcome updateDepartmentProgramOutcome(Long id, DepartmentProgramOutcome updatedDepartmentProgramOutcome) {
        DepartmentProgramOutcome existingProgramOutcome = departmentProgramOutcomeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ProgramOutcome not found with id: " + id));

        existingProgramOutcome.setDescription(updatedDepartmentProgramOutcome.getDescription());
        existingProgramOutcome.setNumber(updatedDepartmentProgramOutcome.getNumber());

        return departmentProgramOutcomeRepository.save(existingProgramOutcome);
    }

    @Transactional
    public void deleteDepartmentProgramOutcome(Long id) {
        departmentProgramOutcomeRepository.deleteById(id);
    }

    public List<DepartmentProgramOutcome> getByDepartmentId(Long departmentId) {
        return departmentProgramOutcomeRepository.findByDepartmentId(departmentId);
    }


}
