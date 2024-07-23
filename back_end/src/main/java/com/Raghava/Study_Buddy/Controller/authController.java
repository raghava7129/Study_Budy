package com.Raghava.Study_Buddy.Controller;

import com.Raghava.Study_Buddy.Models.loginUserRequest;
import com.Raghava.Study_Buddy.Models.loginUserResponse;
import com.Raghava.Study_Buddy.Models.registerUserRequest;
import com.Raghava.Study_Buddy.Service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")

public class authController {



    @Autowired
    public authController(userService userService) {
        this.userService = userService;
    }

    private final userService userService;


// login user
   @PostMapping("/login")
    public ResponseEntity<loginUserResponse> loginUser(@RequestBody loginUserRequest loginUserRequest) {
       loginUserResponse loginUserResponseObj= userService.loginUser(loginUserRequest.getUsername(), loginUserRequest.getPassword(),loginUserRequest.getEmail());
        if(loginUserResponseObj==null){
           return ResponseEntity.status(404).body(null);
       }
       if(loginUserResponseObj.getToken()==null){
           return ResponseEntity.status(400).body(null);
       }

       return ResponseEntity.status(200).body(loginUserResponseObj);
    }

// register user
    @PostMapping("/register")
    public ResponseEntity<loginUserResponse> registerUser(@RequestBody registerUserRequest registerUserRequestObj){
        loginUserResponse loginUserResponseObj= userService.registerUser(registerUserRequestObj.getUsername(), registerUserRequestObj.getPassword(),registerUserRequestObj.getFull_name(),registerUserRequestObj.getEmail());
        if(loginUserResponseObj==null){
            return ResponseEntity.status(400).body(null);
        }
        return ResponseEntity.status(201).body(loginUserResponseObj);
    }





}
