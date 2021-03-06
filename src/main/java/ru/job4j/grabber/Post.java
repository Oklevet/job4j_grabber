package ru.job4j.grabber;

import java.time.LocalDateTime;

public class Post {
    private int id;
    private String name;
    private String text;
    private String link;
    private LocalDateTime created;

    public Post(String name, String text, String link, LocalDateTime created) {
        this.name = name;
        this.text = text;
        this.link = link;
        this.created = created;
    }

    public Post(int id, String name, String text, String link, LocalDateTime created) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.link = link;
        this.created = created;
    }

    @Override
    public String toString() {
        return "Post{"
                + "id=" + id + System.lineSeparator()
                + ", name='" + name + System.lineSeparator()
                + ", text='" + text + System.lineSeparator()
                + ", link='" + link + System.lineSeparator()
                + ", created=" + created
                + '}';
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

    public void setCreated(LocalDateTime created) {
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

    public LocalDateTime getCreated() {
        return created;
    }
}
