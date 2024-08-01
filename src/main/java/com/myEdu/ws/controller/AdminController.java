package com.myEdu.ws.controller;

import com.myEdu.ws.model.Admin;
import com.myEdu.ws.model.Student;
import com.myEdu.ws.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        Admin createdAdmin = adminService.createAdmin(admin);
        return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins(){
        List<Admin> admins = adminService.getAllAdmins();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAdmin(@RequestBody Admin admin){
        adminService.deleteAdmin(admin);
        return new ResponseEntity<>("Basarili", HttpStatus.OK);
    }
}
