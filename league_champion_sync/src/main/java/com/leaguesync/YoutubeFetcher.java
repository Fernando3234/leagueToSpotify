package com.leaguesync;

import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistListResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

//Input: name of champion
//Output: Array List of songs
public class YoutubeFetcher {


    // grab and parse through all the playlist from his channel
    public static Object processChannelPlaylists(String champName) throws Exception {
        String nextPageToken = null;

        do {
            PlaylistListResponse playlists = YoutubeApi.getChannelPlaylists("UC13HxCQQTd10Rk_OsHU749A", nextPageToken);
            nextPageToken = playlists.getNextPageToken(); // Get the nextPageToken for the next iteration

            JSONParser parser = new JSONParser();
            try {
                JSONObject rootObject = (JSONObject) parser.parse(String.valueOf(playlists));
                JSONArray items = (JSONArray) rootObject.get("items");

                for (Object itemObj : items) {
                    JSONObject item = (JSONObject) itemObj;
                    JSONObject snippet = (JSONObject) item.get("snippet");
                    String title = (String) snippet.get("title");

                    if (title.equals(champName)) {
                        System.out.println(title);
                        System.out.println("we made it");
                        return item.get("id"); // Assuming you want to return the ID of the item
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } while (nextPageToken != null);

        return null;
    }
    //grab the playlist items

    public static ArrayList<String> processPlaylist(Object playlistId) throws Exception {
        String nextPageToken = null;
        ArrayList<String> songTitles = new ArrayList<>();
        do {
            PlaylistItemListResponse playlistItems = YoutubeApi.getPlaylistItems((String) playlistId,nextPageToken);
            playlistItems.getNextPageToken();
            JSONParser parser = new JSONParser();
            try {
                JSONObject rootObject = (JSONObject) parser.parse(String.valueOf(playlistItems));
                JSONArray items = (JSONArray) rootObject.get("items");

                for (Object itemObj : items) {
                    JSONObject item = (JSONObject) itemObj;
                    JSONObject snippet = (JSONObject) item.get("snippet");
                    String title = (String) snippet.get("title");
                    songTitles.add(title);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } while (nextPageToken != null);
    return songTitles;
    }
    //list out all the songs in that playlist
}
