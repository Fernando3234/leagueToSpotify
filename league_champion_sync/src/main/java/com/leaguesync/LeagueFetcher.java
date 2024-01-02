package com.leaguesync;
//Input:  League Of Legends API data 

import java.io.*;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.InputStream;

//Output: ouputs a champion name

public class LeagueFetcher {
    RiotApi api;

    public LeagueFetcher(RiotApi api) {
        this.api = api;
    }

    // purpose: grab Username from User via prompt
    public String getSummonerName() {
        Scanner userInput = new Scanner(System.in);
        System.out.println("What is your Riot Username?");
        String summonerName = userInput.nextLine();
        userInput.close();
        return summonerName;
    }

    // purpose: grab acccount ID via the summoner name provided in get Summoner
    // Name()
    public String grabAccountId(String summonerName) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        String accountId;

        String accountInformation = api.makeApiRequest(
                "https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summonerName
                        + "?api_key=RGAPI-d98bd254-8443-40dc-adba-c3f29e9d1444");

        jsonObject = (JSONObject) parser.parse(accountInformation);

        accountId = (String) jsonObject.get("id");

        return accountId;
    }

    // purpose: grab array holding all participants in a given match found by the
    // account ID provided in grabAccountId()
    public JSONArray grabMatchParticipantsData(String accountId) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject matchData;
        JSONArray participant;

        String rawMatchData = api.makeApiRequest(
                "https://na1.api.riotgames.com/lol/spectator/v4/active-games/by-summoner/" +
                        accountId + "?api_key=RGAPI-d98bd254-8443-40dc-adba-c3f29e9d1444");

        matchData = (JSONObject) parser.parse(rawMatchData);

        participant = (JSONArray) matchData.get("participants");

        return participant;
    }

    // purpose: Using the Array provideed in grabMatchParticipantsData() find the
    // users player and return the champion ID
    public Object findPlayerChamp(JSONArray participants, String summonerName) throws ParseException {
        Object player;
        for (Object x : participants) {
            player = ((JSONObject) x).get("summonerName");
            if (summonerName.equals(player)) {
                return ((JSONObject) x).get("championId");
            }
        }
        return null;
    }

    public void getChampName(Object championId){
        InputStream is = LeagueFetcher.class.getClassLoader().getResourceAsStream("modified_champ_data.json");

        if (is != null) {
            try (InputStreamReader isr = new InputStreamReader(is)) {
                // Parse the JSON file
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(isr);

                // Process the JSON Object as needed
                //if (championId == jsonObject.)
                for (Map.Entry<String, ?> entry : (Iterable<Map.Entry<String, ?>>) jsonObject.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    JSONObject nestedObject = (JSONObject) value;
                    String nestedKey =  (String) nestedObject.get("key");

                    if (nestedKey.equals(championId.toString())){
                        System.out.println(key);
                        return;
                    }

                }

            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not found");
        }
    }
}