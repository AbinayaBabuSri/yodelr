package com.micromessage.communication.controller;

import static org.mockito.Mockito.doThrow;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.micromessage.communication.exception.YodelrException;
import com.micromessage.communication.service.YodelrService;

public class YodelrControllerTest {
	@InjectMocks
	YodelrController yodelrController;
	
	@Mock
	YodelrService yodelrService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}


	@Test
	public void testAddUserMethod() throws YodelrException {
		ResponseEntity<String> response = yodelrController.addUser("john");
		Assertions.assertEquals(201,response.getStatusCodeValue() );
		
		doThrow(new YodelrException("User already exists")).when(yodelrService).addUser("john");
		response =  yodelrController.addUser("john");
		
		 Assertions.assertEquals(400, response.getStatusCodeValue());
	}
	
	@Test
	public void testAddPostMethod() throws YodelrException {
		ResponseEntity<String> response = yodelrController.addPost("john", "just #chilling today", 1);
		Assertions.assertEquals(201,response.getStatusCodeValue() );
				
		doThrow(new YodelrException("Exception occured")).when(yodelrService).addPost("john", "just #chilling today", 2);
		response = yodelrController.addPost("john", "just #chilling today", 2);
		
		 Assertions.assertEquals(400, response.getStatusCodeValue());
	}
	
	@Test
	public void testDeleteUserMethod() throws YodelrException {
		ResponseEntity<String> response = yodelrController.deleteUser("john");
		Assertions.assertEquals(200,response.getStatusCodeValue());
		
		doThrow(new YodelrException("User does not exist. The user was neither added nor deleted previously")).when(yodelrService).deleteUser("max");
		response = yodelrController.deleteUser("max");
		Assertions.assertEquals(400,response.getStatusCodeValue());
	}
	
	@Test
	public void testGetPostsForUser() throws YodelrException {
		ResponseEntity<Object> response = yodelrController.getPostsForUser("john");
		Assertions.assertEquals(200,response.getStatusCodeValue());
		
		doThrow(new YodelrException("User does not exist. Add user first and then try again!!!")).when(yodelrService).getPostsForUser("max");
		response = yodelrController.getPostsForUser("max");
		Assertions.assertEquals(400,response.getStatusCodeValue());

	}
	
	@Test
	public void testGetPostsForTopic() throws YodelrException {
		ResponseEntity<List<String>> response = yodelrController.getPostsForTopic("steak");
		Assertions.assertEquals(200,response.getStatusCodeValue());
	}
	
	@Test
	public void testGetTrendingTopics() throws YodelrException {
		ResponseEntity<List<String>> response = yodelrController.getTrendingTopics(1,3);
		Assertions.assertEquals(200,response.getStatusCodeValue());
	}
}
