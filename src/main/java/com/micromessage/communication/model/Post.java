package com.micromessage.communication.model;

import java.util.List;

public class Post {
	private User user;
	private String postText;
	private long timestamp;
	private List<String> topics;

	public Post(User user, String postText, long timestamp, List<String> topics) {
		super();
		this.user = user;
		this.postText = postText;
		this.timestamp = timestamp;
		this.topics = topics;
	}

	public User getUser() {
		return user;
	}

	public String getPostText() {
		return postText;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public List<String> getTopics() {
		return topics;
	}

}
