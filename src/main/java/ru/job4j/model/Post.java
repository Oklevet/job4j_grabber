package ru.job4j.model;

import java.util.List;

public class Post {
    private Profile topicStarter;
    private List<Profile> commentators;
    private String headAd;
    private String textAd;
    private String textComment;
    private Button[] buttons;
    private Head header;

    public Post(Profile topicStarter, String headAd, String textAd, Button[] buttons, Head header) {
        this.topicStarter = topicStarter;
        this.headAd = headAd;
        this.textAd = textAd;
        this.buttons = buttons;
        this.header = header;
    }

    public void setCommentators(Profile commentator) {
        this.commentators = commentators.add(commentator);
    }

    public void setTextAd(String textAd) {
        this.textAd = textAd;
    }

    public void setTextComment(String textComment) {
        this.textComment = textComment;
    }

    public Profile getCommentato() {
        return commentator;
    }

    public String getTextAd() {
        return textAd;
    }

    public String getTextComment() {
        return textComment;
    }

    public Button pushButton(index i) {
        return button[i];
    }
}
