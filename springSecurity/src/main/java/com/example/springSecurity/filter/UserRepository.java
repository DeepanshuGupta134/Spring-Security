package com.example.springSecurity.filter;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    public User findUserByEmail(String email){
        User user = new User("email" , "password");
        user.setFirstName("Deepanshu");
        user.setLastName("Gupta");
        return user;
    }
}
