package com.myEdu.ws.service;

import com.myEdu.ws.model.Admin;
import com.myEdu.ws.model.Student;
import com.myEdu.ws.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private final AdminRepository adminRepository;

    public Admin createAdmin(Admin adminDto) {
        String encodedPassword = passwordEncoder.encode(adminDto.getPassword());
        Admin admin = new Admin();
        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setEmail(adminDto.getEmail());
        admin.setStatusCode(adminDto.getStatusCode());
        admin.setPassword(encodedPassword);
        admin.setDepartment(adminDto.getDepartment());
        return adminRepository.save(admin);
    }

}
