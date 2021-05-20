package ru.job4j.model;

public class Post {
    private String date;
    private String head;
    private String url;
    private String plot;

    public Post(String date, String head, String url, String plot) {
        this.date = date;
        this.head = head;
        this.url = url;
        this.plot = plot;
    }

    public Post(String date, String url, String plot) {
        this.date = date;
        this.url = url;
        this.plot = plot;
    }

    @Override
    public String toString() {
        return "Post{" +
                "date='" + date + System.lineSeparator() +
                ", head='" + head + System.lineSeparator() +
                ", url='" + url + System.lineSeparator() +
                ", plot='" + plot + System.lineSeparator() +
                '}';
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }
}
