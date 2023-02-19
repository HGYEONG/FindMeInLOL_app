package com.example.findmeinlol;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.findmeinlol.model.retrofit.RiotImageAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RiotImageAPIRepository {
    Retrofit retrofit;
    APIListener testListener;
    static final String RIOT_IMAGE_URL = "https://ddragon.leagueoflegends.com/cdn/13.3.1/img/profileicon/";

    public RiotImageAPIRepository(APIListener testListener) {
        this.testListener = testListener;

        retrofit = new Retrofit.Builder()
                .baseUrl(RIOT_IMAGE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void getProfileIconImage(int profileIconId) {
        String path = profileIconId + ".png";
        RiotImageAPI riotAPI = retrofit.create(RiotImageAPI.class);

        Log.d("RIAP", path);

        riotAPI.getImage(path).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                testListener.setProfileIcon(bitmap);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
