package com.myEdu.ws.token;

import com.myEdu.ws.dto.AuthDto;
import com.myEdu.ws.model.User;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class BasicAuthTokenService implements TokenService {


    @Override
    public Token createToken(User user, AuthDto authDto) {
        String emailColonPassword = authDto.email() + ":" + authDto.password();
        String token = Base64.getEncoder().encodeToString(emailColonPassword.getBytes());
        return new Token("Basic",token);
    }

    @Override
    public User verifyToken(String authorizationHeader) {
        return null;
    }
}
