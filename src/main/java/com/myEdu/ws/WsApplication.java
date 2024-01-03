package com.myEdu.ws;

import com.myEdu.ws.model.User;
import com.myEdu.ws.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class WsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsApplication.class, args);
	}

	@Bean
	@Profile("dev") // sadece development modunda user üretmek için
	CommandLineRunner userCreator(UserRepository userRepository){
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return (args) -> {
				for(var i = 1; i <= 25; i++){
					User user = new User();
					user.setFirstName("firstname"+i );
					user.setLastName("lastname"+i );
					user.setEmail("user"+i+"@mail.com" );
					user.setPassword(passwordEncoder.encode("p4ssword"));
					user.setStatusCode(1);
					userRepository.save(user);
				}
		};
	}
}
