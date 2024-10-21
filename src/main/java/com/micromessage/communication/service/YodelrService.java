package com.micromessage.communication.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.micromessage.communication.exception.YodelrException;
import com.micromessage.communication.interfaces.YodelrInterface;
import com.micromessage.communication.model.Post;
import com.micromessage.communication.model.User;

@Service
public class YodelrService implements YodelrInterface {

	List<User> users = new ArrayList<User>();
	List<Post> posts = new ArrayList<Post>();

	public void addUser(String userName) throws YodelrException {

		if (!checkUserAlreadyExists(userName)) {
			users.add(new User(userName));
		} else {
			throw new YodelrException("User already exists. Please try to add new user!!!");
		}

	}

	private boolean checkUserAlreadyExists(String userName) {
		boolean userExists = users.stream().anyMatch(user -> user.getUserName().equalsIgnoreCase(userName));
		return userExists;
	}

	public void addPost(String userName, String postText, long timestamp) throws YodelrException {

		checkUserNotExistsAndThrowException(userName);

		if (postText.length() > 140) {
			throw new YodelrException("Post text should contain a maximum of 140 characters!!!");
		}

		List<String> topicsList = getTopicsFromPost(postText);

		Post post = new Post(new User(userName), postText, timestamp, topicsList);
		posts.add(post);

	}

	private void checkUserNotExistsAndThrowException(String userName) throws YodelrException {

		if (!checkUserAlreadyExists(userName)) {
			throw new YodelrException("User does not exist. Add user first and then try again!!!");
		}
	}

	private List<String> getTopicsFromPost(String postText) {
		List<String> topicsList = new ArrayList<String>();
		Set<String> topicSet = new HashSet<String>();
		String[] post = postText.split("\\s+");

		for (String text : post) {
			if (text.startsWith("#") && text.length() > 1) {
				String topicName = text.substring(1).replaceAll("[^0-9a-zA-Z_]", "");
				if (!topicName.isEmpty()) {
					topicSet.add(topicName);
				}
			}
		}
		if (topicSet.size() > 0) {
			topicsList.addAll(topicSet);
		}
		return topicsList;
	}

	public void deleteUser(String userName) throws YodelrException {

		boolean userExists = users.removeIf(user -> user.getUserName().equalsIgnoreCase(userName));
		posts.removeIf(post -> post.getUser().getUserName().equalsIgnoreCase(userName));

		if (!userExists) {
			throw new YodelrException("User does not exist. The user was neither added nor deleted previously");
		}

	}

	public List<String> getPostsForUser(String userName) throws YodelrException {
		List<String> postForUser = new ArrayList<String>();
		try {
			checkUserNotExistsAndThrowException(userName);

			List<Post> sortedPostList = posts.stream()
					.filter(post -> post.getUser().getUserName().equalsIgnoreCase(userName))
					.sorted((a, b) -> Long.compare(b.getTimestamp(), a.getTimestamp())).toList();
			sortedPostList.stream().forEach(post -> postForUser.add(post.getPostText()));

		} catch (Exception e) {
			throw new YodelrException(e.getMessage());
		}
		return postForUser;
	}

	public List<String> getPostsForTopic(String topic) {

		List<String> postForTopic = new ArrayList<String>();

		List<Post> sortedPostList = posts.stream().filter(post -> post.getTopics().contains(topic))
				.sorted((a, b) -> Long.compare(b.getTimestamp(), a.getTimestamp())).toList();

		sortedPostList.stream().forEach(post -> postForTopic.add(post.getPostText()));
		return postForTopic;
	}

	public List<String> getTrendingTopics(long fromTimestamp, long toTimestamp) {
		List<String> trendingTopics = new ArrayList<String>();
		Map<String, Integer> topicCount = new HashMap<String, Integer>();
		for (Post post : posts) {
			if (post.getTimestamp() >= fromTimestamp && post.getTimestamp() <= toTimestamp) {
				for (String topic : post.getTopics()) {
					topicCount.put(topic, topicCount.getOrDefault(topic, 0) + 1);
				}
			}
		}

		trendingTopics = topicCount.entrySet().stream().sorted((e1, e2) -> {
			int countCompare = Integer.compare(e2.getValue(), e1.getValue());
			return countCompare != 0 ? countCompare : e1.getKey().compareTo(e2.getKey());
		}).map(Map.Entry::getKey).toList();

		return trendingTopics;
	}

}
