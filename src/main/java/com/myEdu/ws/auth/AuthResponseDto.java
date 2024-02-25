package com.myEdu.ws.auth;

import com.myEdu.ws.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDto {

    UserDto user;
    Token token;

}
