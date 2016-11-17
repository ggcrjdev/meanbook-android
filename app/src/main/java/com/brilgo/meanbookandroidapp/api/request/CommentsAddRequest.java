package com.brilgo.meanbookandroidapp.api.request;

public class CommentsAddRequest {

    private String postId;
    private String text;

    public String getPostId() {
        return postId;
    }
    public void setPostId(String postId) {
        this.postId = postId;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
