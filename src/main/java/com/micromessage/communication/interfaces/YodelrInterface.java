package com.micromessage.communication.interfaces;

import java.util.List;

import com.micromessage.communication.exception.YodelrException;

/**
 * The Yodelr service interface.
 *
 * This allows adding and deleting users, adding and retrieving posts
 * and getting trending topics.
 */
public interface YodelrInterface {

    void addUser(String userName) throws YodelrException;

    void addPost(String userName, String postText, long timestamp) throws YodelrException;

    void deleteUser(String userName) throws YodelrException;

    List<String> getPostsForUser(String userName) throws YodelrException;

    List<String> getPostsForTopic(String topic);

    List<String> getTrendingTopics(long fromTimestamp, long toTimestamp);

}

