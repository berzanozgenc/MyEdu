package com.myEdu.ws.auth;

import com.myEdu.ws.model.User;
import com.myEdu.ws.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
@Primary
public class JwtTokenService implements TokenService {

    private final SecretKey secretKey = Keys.hmacShaKeyFor("secret-must-be-at-least-32-chars".getBytes());

    @Autowired
    private UserService userService;

    @Override
    public Token createToken(User user, AuthDto authDto) {
        String token = Jwts.builder().setSubject(Long.toString(user.getUserId())).signWith(secretKey).compact();
        return new Token("Bearer", token);
    }

    private String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    @Override
    public User verifyToken(String authorizationHeader) {
        String token = extractToken(authorizationHeader); // Token'ı çıkar
        if (token != null) {
            try {
                String subject = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
                Long userId = Long.parseLong(subject);
                return userService.findById(userId); // Kullanıcıyı id'ye göre bul ve döndür
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

}
