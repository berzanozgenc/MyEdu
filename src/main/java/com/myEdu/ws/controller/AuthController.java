package com.myEdu.ws.controller;

import com.myEdu.ws.dto.AuthDto;
import com.myEdu.ws.dto.AuthResponseDto;
import com.myEdu.ws.exception.AuthenticationException;
import com.myEdu.ws.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("authentication")
    AuthResponseDto handleAuthentication(@Valid @RequestBody AuthDto authDto){
        return authService.authenticate(authDto);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleAuthenticationException(AuthenticationException exception){
        return "Unauthorized: " + exception.getMessage();
    }
}
