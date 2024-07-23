package com.Raghava.Study_Buddy.Service;

import com.Raghava.Study_Buddy.DAO.userDAO;
import com.Raghava.Study_Buddy.Models.loginUserResponse;
import com.Raghava.Study_Buddy.Models.users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class userServiceImpl implements userService {

    @Autowired
    public userServiceImpl(userDAO userDAO) {
        this.userDAO = userDAO;
    }

    private userDAO userDAO;


    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private jwtUtils jwtUtils = new jwtUtils();

    @Override
    public loginUserResponse loginUser(String username, String password, String user_email) {
        users tempUser = userDAO.getUser(username);

        if (tempUser == null) {
            return null;
        }

        if(bCryptPasswordEncoder.matches(password, tempUser.getPassword())){
            return new loginUserResponse("Bearer "+jwtUtils.generateToken(username, password, user_email));
        }

        return new loginUserResponse(null);
    }

    @Override
    public loginUserResponse registerUser(String username, String password, String fullName, String user_email) {
        users tempUser = userDAO.getUser(username);

        if (tempUser != null) {
            return null;
        }

        tempUser = new users(username, bCryptPasswordEncoder.encode(password), fullName, user_email);
        tempUser = userDAO.saveUser(tempUser);

        if(tempUser != null) return new loginUserResponse("Bearer "+jwtUtils.generateToken(username, password, user_email));

        return null;
    }
}
