package com.Raghava.Study_Buddy.Service;

import com.Raghava.Study_Buddy.Models.loginUserRequest;
import com.Raghava.Study_Buddy.Models.loginUserResponse;
import org.springframework.stereotype.Service;

public interface userService {
    public loginUserResponse loginUser(String username, String password, String user_email);
    public loginUserResponse registerUser(String username, String password, String fullName, String user_email);

}
