package ru.job4j.model;

public class Profile {
    private String name;
    private String status;
    private String city;
    private long comments;

    public Profile(String name, String status, String city, long comments) {
        this.name = name;
        this.status = status;
        this.city = city;
        this.comments = comments;
    }
}
