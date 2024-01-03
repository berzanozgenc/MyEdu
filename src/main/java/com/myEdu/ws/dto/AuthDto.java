package com.myEdu.ws.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthDto(@Email String email, @NotBlank String password) {


}
