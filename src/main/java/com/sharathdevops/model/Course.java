package com.sharathdevops.model;

public class Course {
    private final int id;
    private final String title;
    private final String description;
    private final String duration;
    private final int fee;

    public Course(int id, String title, String description, String duration, int fee) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.fee = fee;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getDuration() { return duration; }
    public int getFee() { return fee; }
}
