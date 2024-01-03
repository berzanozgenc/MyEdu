package com.myEdu.ws.dto;

import com.myEdu.ws.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private int statusCode;

    public UserDto(User inDB) {
    }
}



