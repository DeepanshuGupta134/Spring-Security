package com.example.springSecurity.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/getToken")
    public String getToken(@RequestParam String email){
        return jwtUtils.createJwt(new User(email, "password"));
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }
}
