package com.leaguesync;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistListResponse;


public class YoutubeApi {
    private static final String APPLICATION_NAME = "API code samples";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String API_KEY = "AIzaSyD5zAA1tY7YFn6wdLDYUNIwyzEJmJtfBYc"; // Replace this with your API key
    private static YouTube youtubeService;


    //grabs all the playlist associated with that channel
    public static PlaylistListResponse getChannelPlaylists(String channelId,String nextPageToken) throws Exception {
        youtubeService = getService();
        // Define and execute the API request
        YouTube.Playlists.List request = youtubeService.playlists()
                .list("snippet")
                .setMaxResults(50L)
                .setPageToken(nextPageToken)
                .setKey(API_KEY);
        return request.setChannelId(channelId).execute();
    }

    //gets the items inside the playlist
    public static PlaylistItemListResponse getPlaylistItems(String playlistId, String nextPageToken) throws Exception {
        youtubeService = getService();
        // Define and execute the API request
        YouTube.PlaylistItems.List request = youtubeService.playlistItems()
                .list("snippet,contentDetails")
                .setKey(API_KEY);
        return request.setMaxResults(25L)
                .setPlaylistId(playlistId)
                .execute();
    }

    //initializes YouTube object
    public static YouTube getService() throws Exception {
        final HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
