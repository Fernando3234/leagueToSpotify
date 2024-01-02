package com.leaguesync;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

public class Main {
    public static void main(String[] args) throws Exception {
        run();
    }

    public static void run() throws Exception {
        RiotApi api = new RiotApi();
        LeagueFetcher lf = new LeagueFetcher(api);
        String summonerName = lf.getSummonerName();
        String accountID = lf.grabAccountId(summonerName);
        JSONArray matchData = lf.grabMatchParticipantsData(accountID);
        System.err.println(lf.findPlayerChamp(matchData, summonerName));
        String champName = lf.getChampName(lf.findPlayerChamp(matchData, summonerName));
        Object playlistID = YoutubeFetcher.processChannelPlaylists(champName);
        System.out.println(YoutubeFetcher.processPlaylist(playlistID));
    }
}