package com.myEdu.ws.controller;
import com.myEdu.ws.dto.UserDto;
import com.myEdu.ws.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("create-user")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto){
        Long idOfCreatedUser = userService.save(userDto);
        return new ResponseEntity<>(idOfCreatedUser + " id' si ile user oluşturuldu", HttpStatus.CREATED);
    }
}
