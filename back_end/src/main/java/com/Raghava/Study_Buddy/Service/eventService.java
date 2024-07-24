package com.Raghava.Study_Buddy.Service;

import com.Raghava.Study_Buddy.Models.events;

import java.util.List;

public interface eventService {

    events createEvent(String event_name, String description, String start_time, String end_time, String theme, String challenge, String goal, String recurrence);

    public events getEvent(String event_name);

    public List<events> getAllEvents();

    public String updateEvent(String event_name, String description, String start_time, String end_time, String theme, String challenge, String goal);

    public String deleteEvent(String event_name);
}
