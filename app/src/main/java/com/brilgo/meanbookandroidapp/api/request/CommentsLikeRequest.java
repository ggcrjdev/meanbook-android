package com.brilgo.meanbookandroidapp.api.request;

public class CommentsLikeRequest {

	private String postId;
	private String commentId;
	
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
}
