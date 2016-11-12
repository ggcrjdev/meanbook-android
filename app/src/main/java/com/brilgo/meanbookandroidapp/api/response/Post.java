package com.brilgo.meanbookandroidapp.api.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Post {
	public final String id;
	public final String author;
	public final Date timestamp;
	public final String text;
	public final Integer likes;
	public final List<Comment> comments;
	public final boolean hasComments;
	public final int commentsCount;
	private Post(String id, String author, Date timestamp, String text, Integer likes, List<Comment> comments) {
		this.id = id;
		this.author = author;
		this.timestamp = timestamp;
		this.text = text;
		this.likes = likes;
		this.comments = Collections.unmodifiableList(comments);
		this.commentsCount = comments.size();
		this.hasComments = !comments.isEmpty();
	}
	public Post(String id, String author, Date timestamp, String text, Integer likes) {
		this(id, author, timestamp, text, likes, new ArrayList<Comment>(0));
	}
	
	public Post withCommets(List<Comment> comments) {
		return new Post(id, author, timestamp, text, likes, comments);
	}
}
