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

    private long userId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private int statusCode;

    public UserDto(User inDB) {
        this.userId = inDB.getUserId();
        this.email = inDB.getEmail();
        this.password = inDB.getPassword();
        this.firstName = inDB.getFirstName();
        this.lastName = inDB.getLastName();
        this.statusCode = inDB.getStatusCode();
    }
}



