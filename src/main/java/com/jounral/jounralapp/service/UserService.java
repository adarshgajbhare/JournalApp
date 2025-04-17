package com.jounral.jounralapp.service;

import com.jounral.jounralapp.model.User;
import com.jounral.jounralapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepo repo;

     public List<User> getAllUsers(){
         return repo.findAll();
     }

     public void saveUser(User user){
          repo.save(user);
     }

     public User findByUserName(String userName){
         return  repo.findByUserName(userName);
     }

}
