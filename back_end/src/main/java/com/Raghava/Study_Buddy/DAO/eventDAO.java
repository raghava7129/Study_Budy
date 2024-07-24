package com.Raghava.Study_Buddy.DAO;

import com.Raghava.Study_Buddy.Models.events;

import java.util.List;

public interface eventDAO {
    public events createEvent(String event_name, String description, String start_time, String end_time, String theme, String challenge, String goal, String recurrence);
    public events getEvent(String event_name);
    public void updateEvent(String event_name, String description, String start_time, String end_time, String theme, String challenge, String goal);
    public void deleteEvent(String event_name);

    public List<events> getAllEvents();
}
