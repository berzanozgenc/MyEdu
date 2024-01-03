package com.myEdu.ws.dto;

import com.myEdu.ws.token.Token;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDto {

    UserDto user;
    Token token;

}
