package ru.job4j.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

public class Post {
    private int id;
    private String name;
    private String text;
    private String link;
    private Instant created;

    public Post(String name, String text, String link) {
        this.name = name;
        this.text = text;
        this.link = link;
    }

    public Post(String name, String text, String link, Instant created) {
        this.name = name;
        this.text = text;
        this.link = link;
        this.created = created;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id + System.lineSeparator() +
                ", name='" + name + System.lineSeparator() +
                ", text='" + text + System.lineSeparator() +
                ", link='" + link + System.lineSeparator() +
                ", created=" + created +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getLink() {
        return link;
    }

    public Instant getCreated() {
        return created;
    }
}
