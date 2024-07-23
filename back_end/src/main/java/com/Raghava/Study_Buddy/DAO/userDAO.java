package com.Raghava.Study_Buddy.DAO;

import com.Raghava.Study_Buddy.Models.users;

public interface userDAO {
    users getUser(String username);

    users saveUser(users user);

}
