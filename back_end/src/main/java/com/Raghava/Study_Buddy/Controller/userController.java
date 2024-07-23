package com.Raghava.Study_Buddy.Controller;

import com.Raghava.Study_Buddy.Models.loginUserRequest;
import com.Raghava.Study_Buddy.Service.jwtUtils;
import com.Raghava.Study_Buddy.Service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/user")

public class userController {

    @Autowired
    public userController(userService userService) {
        this.userService = userService;
    }

    private userService userService;

    private jwtUtils jwtUtils = new jwtUtils();

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String token, @PathVariable String username) {
        if (!jwtUtils.validateJwtToken(token.split(" ")[1])) {
            return ResponseEntity.status(401).body(null);
        }
        loginUserRequest loginUserRequestObj = jwtUtils.decodeJwtToken(token.split(" ")[1]);
        if (loginUserRequestObj == null) {
            return ResponseEntity.status(401).body(null);
        }
        if (loginUserRequestObj.getUsername().equals(username)) {
            return ResponseEntity.status(200).body(loginUserRequestObj);
        }
        return ResponseEntity.status(401).body(null);
    }

}
