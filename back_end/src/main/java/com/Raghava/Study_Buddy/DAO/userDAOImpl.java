package com.Raghava.Study_Buddy.DAO;

import com.Raghava.Study_Buddy.Models.users;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

@Component
public class userDAOImpl implements userDAO{
    private final String user;
    private final String password;
    private final String url;


    public userDAOImpl() {
        try {
            Resource resource = new ClassPathResource("/application.properties");
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);

            user = properties.getProperty("DB_USERNAME");
            password = properties.getProperty("DB_PASSWORD");
            url = properties.getProperty("DB_URL");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public users getUser(String username) {
        try{
            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                users user = new users();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setFullName(resultSet.getString("full_name"));
                user.setUser_email(resultSet.getString("email"));
                return user;
            }
            return null;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public users saveUser(users user) {
        try{
            Connection connection = DriverManager.getConnection(url,this.user,this.password);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Users (username, password, first_name, email, created_at) VALUES (?, ?, ?, ?, NOW())");
            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setString(3,user.getFullName());
            preparedStatement.setString(4,user.getUser_email());
            preparedStatement.executeUpdate();
            return user;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
