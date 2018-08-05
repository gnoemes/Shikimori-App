package com.gnoemes.shikimoriapp.entity.forum.domain;

public class Forum {

    private int id;
    private String name;
    private ForumType forumType;
    private String url;

    public Forum(int id, String name, ForumType forumType, String url) {
        this.id = id;
        this.name = name;
        this.forumType = forumType;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ForumType getForumType() {
        return forumType;
    }

    public String getUrl() {
        return url;
    }
}
