package com.Raghava.Study_Buddy.DAO;

import com.Raghava.Study_Buddy.Models.events;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class eventDAOImpl implements eventDAO{

    private final String user;
    private final String password;
    private final String url;

    public eventDAOImpl(){
        try{

            Resource resource = new ClassPathResource("/application.properties");
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);

            user = properties.getProperty("DB_USERNAME");
            password = properties.getProperty("DB_PASSWORD");
            url = properties.getProperty("DB_URL");

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public events createEvent(String event_name, String description, String start_time, String end_time, String theme, String challenge, String goal, String recurrence) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO events (event_name, " +
                    "description, start_time, end_time, theme, challenge, goal) VALUES (?,?,?,?,?,?,?)");

            preparedStatement1.setString(1, event_name);
            preparedStatement1.setString(2, description);
            preparedStatement1.setString(3, start_time);
            preparedStatement1.setString(4, end_time);
            preparedStatement1.setString(5, theme);
            preparedStatement1.setString(6, challenge);
            preparedStatement1.setString(7, goal);

            preparedStatement1.executeUpdate();

            System.out.println("Inserting into EventSchedules: event_name=" + event_name + ", recurrence=" + recurrence);

            if (recurrence.equals("none") || recurrence.equals("weekly") || recurrence.equals("bi-weekly") || recurrence.equals("monthly")) {
                PreparedStatement preparedStatement2 = connection.prepareStatement(
                        "INSERT INTO EventSchedules (event_name, recurrence) VALUES (?,?)");
                preparedStatement2.setString(1, event_name);
                preparedStatement2.setString(2, recurrence);

                preparedStatement2.executeUpdate();
            } else {
                throw new IllegalArgumentException("Invalid recurrence value");
            }


            return new events(event_name, description, start_time, end_time, theme, challenge, goal, recurrence);

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public events getEvent(String event_name) {
        try{
            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM events WHERE event_name = ?");
            preparedStatement.setString(1,event_name);

            ResultSet resultSet = preparedStatement.executeQuery();

            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM EventSchedules WHERE event_name = ?");
            preparedStatement1.setString(1,event_name);
            ResultSet resultSet1 = preparedStatement1.executeQuery();

            if(!resultSet.next() || !resultSet1.next()) return null;


            events event = new events();
            event.setEvent_name(resultSet.getString("event_name"));
            event.setDescription(resultSet.getString("description"));
            event.setStart_time(resultSet.getString("start_time"));
            event.setEnd_time(resultSet.getString("end_time"));
            event.setTheme(resultSet.getString("theme"));
            event.setChallenge(resultSet.getString("challenge"));
            event.setGoal(resultSet.getString("goal"));
            event.setRecurrence(resultSet1.getString("recurrence"));
            return event;

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateEvent(String event_name, String description, String start_time, String end_time, String theme, String challenge, String goal) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE events SET description = ?, start_time = ?, end_time = ?, theme = ?, challenge = ?, goal = ? WHERE event_name = ?");
            preparedStatement.setString(1, description);
            preparedStatement.setString(2, start_time);
            preparedStatement.setString(3, end_time);
            preparedStatement.setString(4, theme);
            preparedStatement.setString(5, challenge);
            preparedStatement.setString(6, goal);
            preparedStatement.setString(7, event_name);
            preparedStatement.executeUpdate();




        }catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void deleteEvent(String event_name) {
        try{

            Connection connection = DriverManager.getConnection(url,user,password);

            PreparedStatement preparedStatement1 = connection.prepareStatement("DELETE FROM EventSchedules WHERE event_name = ?");
            preparedStatement1.setString(1,event_name);
            preparedStatement1.executeUpdate();

            PreparedStatement preparedStatement2 = connection.prepareStatement("DELETE FROM events WHERE event_name = ?");
            preparedStatement2.setString(1,event_name);
            preparedStatement2.executeUpdate();

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<events> getAllEvents() {
        try{
            Connection connection = DriverManager.getConnection(url,user,password);

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM events");
            ResultSet resultSet = preparedStatement.executeQuery();

            List<events> eventList = new ArrayList<>();

            while(resultSet.next()){
                events event = new events();
                event.setEvent_name(resultSet.getString("event_name"));
                event.setDescription(resultSet.getString("description"));
                event.setStart_time(resultSet.getString("start_time"));
                event.setEnd_time(resultSet.getString("end_time"));
                event.setTheme(resultSet.getString("theme"));
                event.setChallenge(resultSet.getString("challenge"));
                event.setGoal(resultSet.getString("goal"));

                PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM EventSchedules WHERE event_name = ?");
                preparedStatement1.setString(1,event.getEvent_name());
                ResultSet resultSet1 = preparedStatement1.executeQuery();

                if (!resultSet1.next()) {
                    continue;
                }

                event.setRecurrence(resultSet1.getString("recurrence"));

                eventList.add(event);
            }

            return eventList;


        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<events> getEventsByRecurrence(String recurrence) {
        try{

            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM EventSchedules WHERE recurrence = ?");
            preparedStatement.setString(1,recurrence);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<events> eventList = new ArrayList<>();

            while(resultSet.next()){
                PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM events WHERE event_name = ?");
                preparedStatement1.setString(1,resultSet.getString("event_name"));
                ResultSet resultSet1 = preparedStatement1.executeQuery();

                if(!resultSet1.next()) continue;

                events event = new events();
                event.setEvent_name(resultSet1.getString("event_name"));
                event.setDescription(resultSet1.getString("description"));
                event.setStart_time(resultSet1.getString("start_time"));
                event.setEnd_time(resultSet1.getString("end_time"));
                event.setTheme(resultSet1.getString("theme"));
                event.setChallenge(resultSet1.getString("challenge"));
                event.setGoal(resultSet1.getString("goal"));
                event.setRecurrence(resultSet.getString("recurrence"));

                eventList.add(event);
            }

            return eventList;

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
