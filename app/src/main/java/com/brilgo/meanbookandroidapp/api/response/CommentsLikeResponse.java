package com.brilgo.meanbookandroidapp.api.response;

public class CommentsLikeResponse {
	public final String commentId;
	public final Integer likes;
	public CommentsLikeResponse(String commentId, Integer likes) {
		this.commentId = commentId;
		this.likes = likes;
	}
	
	public static CommentsLikeResponse nullObject() {
		return new CommentsLikeResponse("", null);
	}
}
