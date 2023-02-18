package com.example.findmeinlol.model.retrofit;

import com.example.findmeinlol.model.data.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @GET("summoner/v4/summoners/by-name/{summonerName}")
    Call<User> getData(@Path("summonerName") String name, @Query("api_key") String api);
}


