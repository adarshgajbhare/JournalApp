package com.jounral.jounralapp.controller;


import com.jounral.jounralapp.model.User;
import com.jounral.jounralapp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }


    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        service.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deleteEntry(@PathVariable String id) {
//        if (!ObjectId.isValid(id)) {
//            return new ResponseEntity<>("Invalid ID format", HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(service.deleteUser(new ObjectId(id)), HttpStatus.OK);
//    }


    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName) {
        User userInDb = service.findByUserName(userName);
        if (userInDb != null) {
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            service.saveUser(userInDb);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
