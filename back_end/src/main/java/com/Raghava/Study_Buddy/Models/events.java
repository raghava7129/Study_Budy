package com.Raghava.Study_Buddy.Models;

public class events {
    private String event_name;
    private String description;
    private String start_time;
    private String end_time;
    private String theme;
    private String challenge;
    private String goal;
    private String recurrence;

    public events() {
    }

    public events(String event_name, String description, String start_time, String end_time, String theme, String challenge, String goal, String recurrence) {
        this.event_name = event_name;
        this.description = description;
        this.start_time = start_time;
        this.end_time = end_time;
        this.theme = theme;
        this.challenge = challenge;
        this.goal = goal;
        this.recurrence = recurrence;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    @Override
    public String toString() {
        return "events{" +
                "event_name='" + event_name + '\'' +
                ", description='" + description + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", theme='" + theme + '\'' +
                ", challenge='" + challenge + '\'' +
                ", goal='" + goal + '\'' +
                ", recurrence='" + recurrence + '\'' +
                '}';
    }
}
