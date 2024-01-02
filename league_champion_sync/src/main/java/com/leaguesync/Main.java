package com.leaguesync;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

public class Main {
    public static void main(String[] args) {
        try {
            run();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void run() throws ParseException {
        RiotApi api = new RiotApi();
        LeagueFetcher lf = new LeagueFetcher(api);
        String summonerName = lf.getSummonerName();
        String accountID = lf.grabAccountId(summonerName);
        JSONArray matchData = lf.grabMatchParticipantsData(accountID);
        System.err.println(lf.findPlayerChamp(matchData, summonerName));
        lf.getChampName(lf.findPlayerChamp(matchData, summonerName));
    }
}