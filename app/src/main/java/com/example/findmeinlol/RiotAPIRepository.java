package com.example.findmeinlol;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;

import com.example.findmeinlol.model.data.LeagueEntry;
import com.example.findmeinlol.model.data.User;
import com.example.findmeinlol.model.retrofit.RiotAPI;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RiotAPIRepository {
    Retrofit retrofit;
    APIListener apiListener;
    static final String RIOT_URL = "https://kr.api.riotgames.com/lol/";

    public RiotAPIRepository(APIListener testListener) {
        this.apiListener = testListener;

        retrofit = new Retrofit.Builder()
                .baseUrl(RIOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void getUserInfo(String name) {
        RiotAPI riotAPI = retrofit.create(RiotAPI.class);

        riotAPI.getUserInfo(name, BuildConfig.riot_api_key).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    getLeagueEntry(response.body());
                }
                else {
                    apiListener.setUser(response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getLeagueEntry(User user) {
        String id = user.getId();
        RiotAPI riotAPI = retrofit.create(RiotAPI.class);

        riotAPI.getLeagueEntry(id, BuildConfig.riot_api_key).enqueue(new Callback<ArrayList<LeagueEntry>>() {
            @Override
            public void onResponse(Call<ArrayList<LeagueEntry>> call, Response<ArrayList<LeagueEntry>> response) {
                String path = "android.resource://" + R.class.getPackage().getName() + "/";
                if (response.isSuccessful()) {
                    if (response.body().isEmpty()) {
                        path+=getResourceId("UNRANK");
                        user.setTier("UNRANK");
                        user.setQueueType("");
                        user.setRank("");
                    }
                    else {
                        path += getResourceId(response.body().get(0).getTier());
                        user.setTier(response.body().get(0).getTier());
                        user.setQueueType(response.body().get(0).getQueueType());
                        user.setRank(response.body().get(0).getRank());
                    }
                    user.setTierIconId(path);
                    apiListener.setUser(user);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<LeagueEntry>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private int getResourceId(String resource) {
        switch (resource) {
            case "BRONZE":
                return R.drawable.emblem_bronze;
            case "IRON":
                return R.drawable.emblem_iron;
            case "SILVER":
                return R.drawable.emblem_silver;
            case "GOLD":
                return R.drawable.emblem_gold;
            case "PLATINUM":
                return R.drawable.emblem_platinum;
            case "DIAMOND":
                return R.drawable.emblem_diamond;
            case "MASTER":
                return R.drawable.emblem_master;
            case "GRANDMASTER":
                return R.drawable.emblem_grandmaster;
            case "CHALLENGER":
                return R.drawable.emblem_challenger;
            default:
                return R.drawable.emblem_unrank;
        }
    }
}
