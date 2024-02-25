package com.myEdu.ws.auth;

import com.myEdu.ws.model.User;

public interface TokenService {

    public Token createToken(User user, AuthDto authDto);

    public User verifyToken(String authorizationHeader);


}
