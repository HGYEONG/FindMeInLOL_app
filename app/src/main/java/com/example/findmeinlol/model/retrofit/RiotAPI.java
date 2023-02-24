package com.example.findmeinlol.model.retrofit;

import android.graphics.Bitmap;

import com.example.findmeinlol.model.data.LeagueEntry;
import com.example.findmeinlol.model.data.User;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface RiotAPI {
    @GET("summoner/v4/summoners/by-name/{summonerName}")
    Call<User> getUserInfo(@Path("summonerName") String name, @Query("api_key") String api);

    @GET("league/v4/entries/by-summoner/{encryptedSummonerId}")
    Call<ArrayList<LeagueEntry>> getLeagueEntry(@Path("encryptedSummonerId") String id, @Query("api_key") String api);
}