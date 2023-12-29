package com.leaguesync;
//Input:  League Of Legends API data 

import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
}