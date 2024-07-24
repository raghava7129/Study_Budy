package com.Raghava.Study_Buddy.Service;

import com.Raghava.Study_Buddy.DAO.eventDAO;
import com.Raghava.Study_Buddy.Models.events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class eventServiceImpl implements eventService{

    @Autowired
    public eventServiceImpl(eventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    private final eventDAO eventDAO;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    @Override
    public events createEvent(String event_name, String description, String start_time, String end_time, String theme, String challenge, String goal, String recurrence) {
        events tempEvent = eventDAO.getEvent(event_name);

        if (tempEvent != null) {
            return null;
        }

        tempEvent = eventDAO.createEvent(event_name, description, start_time, end_time, theme, challenge, goal, recurrence);

        if(tempEvent != null){
            return tempEvent;
        }

        return null;
    }

    @Override
    public events getEvent(String event_name) {
        return eventDAO.getEvent(event_name);
    }

    @Override
    public List<events> getAllEvents() {
        return eventDAO.getAllEvents();
    }

    @Override
    public String updateEvent(String event_name, String description, String start_time, String end_time, String theme, String challenge, String goal) {
        eventDAO.updateEvent(event_name, description, start_time, end_time, theme, challenge, goal);
        return "success";
    }

    @Override
    public String deleteEvent(String event_name) {
        eventDAO.deleteEvent(event_name);
        return "success";
    }
}
