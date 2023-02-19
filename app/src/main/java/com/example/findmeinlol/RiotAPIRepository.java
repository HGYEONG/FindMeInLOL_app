package com.example.findmeinlol;

import android.util.Log;

import com.example.findmeinlol.model.data.User;
import com.example.findmeinlol.model.retrofit.RiotAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RiotAPIRepository {
    Retrofit retrofit;
    APIListener testListener;
    static final String RIOT_URL = "https://kr.api.riotgames.com/lol/";

    public RiotAPIRepository(APIListener testListener) {
        this.testListener = testListener;

        retrofit = new Retrofit.Builder()
                .baseUrl(RIOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void findUser(String name) {
        RiotAPI riotAPI = retrofit.create(RiotAPI.class);

        riotAPI.getData(name, BuildConfig.riot_api_key).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                testListener.setUser(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
