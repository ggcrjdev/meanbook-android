package com.brilgo.meanbookandroidapp.api.response;

public class PostsLikeResponse {

    public final String postId;
    public final Integer numLikes;

    public PostsLikeResponse(String postId, Integer numLikes) {
        this.postId = postId;
        this.numLikes = numLikes;
    }

    public static PostsLikeResponse nullObject() {
        return new PostsLikeResponse("", null);
    }
}
