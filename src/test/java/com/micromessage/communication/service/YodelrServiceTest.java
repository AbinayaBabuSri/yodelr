package com.micromessage.communication.service;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.micromessage.communication.exception.YodelrException;

public class YodelrServiceTest {
	@InjectMocks
	YodelrService yodelrService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testAllMethods() throws YodelrException {

		yodelrService.addUser("john");

		Assertions.assertThrows(YodelrException.class, () -> yodelrService.addUser("john"));

		yodelrService.addPost("john", "just #chilling today", 1);

		Assertions.assertThrows(YodelrException.class, () -> yodelrService.addPost("mark", "just #chilling today", 1));

		yodelrService.addPost("john", "eating #steak for #dinner", 2);

		yodelrService.addPost("john", "ugh! this #steak tasted # like dog food", 3);

		Assertions.assertEquals(Arrays.asList("ugh! this #steak tasted # like dog food", "eating #steak for #dinner",
				"just #chilling today"), yodelrService.getPostsForUser("john"));

		Assertions.assertThrows(YodelrException.class, () -> yodelrService.addPost("john",
				"Steak is a healthy choice for a balanced diet because it contains essential nutrients that support many areas of your health.When cooked, the combination of fat, sugar and protein produces thousands of unique volatile aromatic compounds, and also results in the brown color and rich flavors of the Maillard Reaction. Regardless of our age or background, we are drawn to the juiciness, tenderness, mouthfeel and fibrousness of meat.",
				4));

		Assertions.assertThrows(YodelrException.class, () -> yodelrService.getPostsForUser("mark"));

		yodelrService.addPost("john", "eating bread for lunch", 5);

		yodelrService.addPost("john", "Mary goes to that restaurant for #dinner every other day", 6);

		yodelrService.getPostsForTopic("steak");

		Assertions.assertEquals(Arrays.asList("steak", "chilling", "dinner"), yodelrService.getTrendingTopics(1, 3));

		yodelrService.getTrendingTopics(2, 3);

		yodelrService.getTrendingTopics(2, 6);
	
		yodelrService.deleteUser("john");
		Assertions.assertThrows(YodelrException.class, () -> yodelrService.deleteUser("john"));

	}
}
