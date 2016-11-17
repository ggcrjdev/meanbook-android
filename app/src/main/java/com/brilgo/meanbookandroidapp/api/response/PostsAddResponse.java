package com.brilgo.meanbookandroidapp.api.response;

import java.util.Date;

public class PostsAddResponse {

    public final String id;
    public final String authorId;
    public final String author;
    public final Date timestamp;
    public final String text;

    public PostsAddResponse(String id, String authorId, String author, Date timestamp, String text) {
        this.id = id;
        this.authorId = authorId;
        this.author = author;
        this.timestamp = timestamp;
        this.text = text;
    }

    public static PostsAddResponse nullObject() {
        return new PostsAddResponse("", "", "", null, "");
    }
}
