package com.myEdu.ws.service;

import com.myEdu.ws.dto.AuthDto;
import com.myEdu.ws.dto.AuthResponseDto;
import com.myEdu.ws.dto.UserDto;
import com.myEdu.ws.model.User;
import com.myEdu.ws.exception.AuthenticationException;
import com.myEdu.ws.token.Token;
import com.myEdu.ws.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserService userService;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    TokenService tokenService;

    public AuthResponseDto authenticate(AuthDto authDto){
        User inDB = userService.findByEmail(authDto.email());
        if(inDB == null) throw new AuthenticationException("Hatali Giriş");
        if(!passwordEncoder.matches(authDto.password(),inDB.getPassword())) throw new AuthenticationException("Hatali Giriş");

        Token token = tokenService.createToken(inDB,authDto);
        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setToken(token);
        authResponseDto.setUser(new UserDto(inDB));
        return authResponseDto;
    }

}
