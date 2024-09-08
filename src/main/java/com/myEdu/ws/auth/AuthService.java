package com.myEdu.ws.auth;

import com.myEdu.ws.dto.UserDto;
import com.myEdu.ws.exception.AuthenticationException;
import com.myEdu.ws.model.User;
import com.myEdu.ws.service.UserService;
import com.myEdu.ws.auth.LdapAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserService userService;

    @Autowired
    LdapAuthUtil ldapAuthUtil;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    TokenService tokenService;

    public AuthResponseDto authenticate(AuthDto authDto) {
        User inDB = userService.findByEmail(authDto.email());
        if (inDB != null && passwordEncoder.matches(authDto.password(), inDB.getPassword())) {
            return createAuthResponse(inDB, authDto);
        } else if (ldapAuthUtil.authenticate(authDto.email(), authDto.password())) {
            // LDAP doğrulama başarılıysa
            inDB = userService.findByEmail(authDto.email());
            if (inDB == null) {
                // LDAP kullanıcısı yerel veritabanında yoksa
                throw new AuthenticationException("LDAP ile doğrulandı ancak kullanıcı yerel veritabanında bulunamadı.");
            }
            return createAuthResponse(inDB, authDto);
        } else {
            throw new AuthenticationException("Geçersiz giriş");
        }
    }

    private AuthResponseDto createAuthResponse(User user, AuthDto authDto) {
        Token token = tokenService.createToken(user, authDto);
        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setToken(token);
        authResponseDto.setUser(new UserDto(user));
        return authResponseDto;
    }
}
