package com.brilgo.meanbookandroidapp.api.response;

import java.util.Date;

public class Comment {
	public final String id;
	public final String author;
	public final Date timestamp;
	public final String text;
	public final Integer likes;
	public Comment(String id, String author, Date timestamp, String text, Integer likes) {
		super();
		this.id = id;
		this.author = author;
		this.timestamp = timestamp;
		this.text = text;
		this.likes = likes;
	}
}
