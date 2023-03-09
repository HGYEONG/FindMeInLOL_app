package com.example.findmeinlol;

import com.example.findmeinlol.model.data.LeagueEntryDTO;
import com.example.findmeinlol.model.data.MatchDto;
import com.example.findmeinlol.model.data.ParticipantDto;
import com.example.findmeinlol.model.data.SummonerDto;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RiotAPI {
    @GET("summoner/v4/summoners/by-name/{summonerName}")
    Call<SummonerDto> getUserInfo(@Path("summonerName") String name, @Query("api_key") String api);

    @GET("league/v4/entries/by-summoner/{encryptedSummonerId}")
    Call<ArrayList<LeagueEntryDTO>> getLeagueEntry(@Path("encryptedSummonerId") String id, @Query("api_key") String api);

    @GET("/lol/match/v5/matches/by-puuid/{puuid}/ids")
    Call<ArrayList<String>> getMatchesId(@Path("puuid") String puuId, @Query("api_key") String api,
                                         @Query("start") int start, @Query("count") int count);

    @GET("/lol/match/v5/matches/{matchId}")
    Call<MatchDto> getMatchInfo(@Path("matchId") String matchId, @Query("api_key") String api);

    @GET("{imagePath}")
    Call<ResponseBody> getImage(@Path("imagePath") String imagePath);
}