package com.myEdu.ws.service;

import com.myEdu.ws.dto.UserDto;
import com.myEdu.ws.repository.UserRepository;
import com.myEdu.ws.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    UserRepository userRepository;

    public Long save(UserDto userDto){
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setStatusCode(userDto.getStatusCode());
        user.setPassword(encodedPassword);
        return userRepository.save(user).getUserId();
    }

    public User findByEmail (String email) {
        return userRepository.findByEmail(email);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
