package com.micromessage.communication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.micromessage.communication.exception.YodelrException;
import com.micromessage.communication.service.YodelrService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/yodelr/")
public class YodelrController {

	@Autowired
	YodelrService yodelrService;

	@Operation(summary = "Adds a user with the given username to the system.")
	@PostMapping("user")
	public ResponseEntity<String> addUser(@RequestParam String userName) {
		try {
			yodelrService.addUser(userName);
			return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
		} catch (YodelrException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@Operation(summary = "Adds a post for the given user with the given post text and timestamp")
	@PostMapping("post")
	public ResponseEntity<String> addPost(@RequestParam String userName, @RequestParam String postText,
			@RequestParam long timestamp) {
		try {
			yodelrService.addPost(userName, postText, timestamp);
			return new ResponseEntity<>("Post added successfully", HttpStatus.CREATED);
		} catch (YodelrException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@Operation(summary = "Deletes the given user and all posts made by the user from the system.")
	@DeleteMapping("user/{userName}")
	public ResponseEntity<String> deleteUser(@PathVariable String userName) throws YodelrException {
		try {
			yodelrService.deleteUser(userName);
		
		return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
		} catch (YodelrException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@Operation(summary = "Returns a list of all post texts made by the given user.")
	@GetMapping("user/{userName}/post")
	public ResponseEntity<Object> getPostsForUser(@PathVariable String userName) throws YodelrException {
		try {
		List<String> posts = yodelrService.getPostsForUser(userName);
		return new ResponseEntity<>(posts, HttpStatus.OK);
	} catch (YodelrException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	}
	
	@Operation(summary = "Returns a list of all post texts containing the given topic.")
	@GetMapping("topic/{topicName}/post")
	public ResponseEntity<List<String>> getPostsForTopic(@PathVariable String topicName) {
		List<String> posts = yodelrService.getPostsForTopic(topicName);
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	@Operation(summary = "Returns a list of all unique topics mentioned in posts made in the given time interval")
	@GetMapping("trending/topics")
	public ResponseEntity<List<String>> getTrendingTopics(@RequestParam long fromTimestamp,
			@RequestParam long toTimestamp) {
		List<String> trendingTopics = yodelrService.getTrendingTopics(fromTimestamp, toTimestamp);
		return new ResponseEntity<>(trendingTopics, HttpStatus.OK);
	}
}
