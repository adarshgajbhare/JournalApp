package com.jounral.jounralapp.service;

import com.jounral.jounralapp.model.User;
import com.jounral.jounralapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepo repo;

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

     public List<User> getAllUsers(){
         return repo.findAll();
     }

     public void saveUser(User user){
         user.setPassword( PASSWORD_ENCODER.encode(user.getPassword()));
         System.out.printf("came ehre !!!!!!!!!!!!!!!!!!!");
         user.setRoles(Arrays.asList("USER"));
         System.out.printf("came ehre !!!!!!!!!!!!!!!!!!!");
         repo.save(user);
         System.out.printf("came ehre !!!!!!!!!!!!!!!!!!!");
     }

    public void saveNewUser(User user){
       user.setPassword( PASSWORD_ENCODER.encode(user.getPassword()));
       user.setRoles(Arrays.asList("USER"));
         repo.save(user);
    }

     public User findByUserName(String userName){
         return  repo.findByUserName(userName);
     }

//     public void deleteUser(){
//
//     }
}
