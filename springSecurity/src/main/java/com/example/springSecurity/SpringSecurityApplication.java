package com.example.springSecurity;

import com.example.springSecurity.filter.JwtUtils;
import com.example.springSecurity.filter.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.springSecurity")
public class SpringSecurityApplication {

	@Autowired
	private ApplicationContext appContext;

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
		JwtUtils jwtUtils = new JwtUtils();
		User user = new User("dg.gmail.com", "password");
		String token = jwtUtils.createJwt(user);
		System.out.println(jwtUtils.getFirstName(token));
	}

	public void run(String... args) throws Exception {

		String[] beans = appContext.getBeanDefinitionNames();
		for (String bean : beans) {
			System.out.println(bean);
		}

	}

}
