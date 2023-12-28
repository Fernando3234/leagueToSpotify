package com.leaguesync;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RiotApi {

    private final OkHttpClient client = new OkHttpClient();
    private final String apiKey = "RGAPI-d98bd254-8443-40dc-adba-c3f29e9d1444"; // Replace with your API key

    public String makeApiRequest(String url) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-Riot-Token", apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
