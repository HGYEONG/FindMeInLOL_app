package com.example.findmeinlol;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.findmeinlol.model.Item;
import com.example.findmeinlol.model.SearchModel;
import com.example.findmeinlol.model.data.InfoDto;
import com.example.findmeinlol.model.data.LeagueEntryDTO;
import com.example.findmeinlol.model.data.MatchDto;
import com.example.findmeinlol.model.data.ParticipantDto;
import com.example.findmeinlol.model.data.SummonerDto;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RiotAPIRepository {
    private static RiotAPIRepository mRetrofitRepository;

    static final String RIOT_URL = "https://kr.api.riotgames.com/lol/";
    static final String RIOT_MATCH_URL = "https://asia.api.riotgames.com/";
    static final String RIOT_PROFILE_IMAGE_URL =
            "https://ddragon.leagueoflegends.com/cdn/13.3.1/img/profileicon/";
    static final String RIOT_CHAMPION_IMAGE_URL =
            "https://ddragon.leagueoflegends.com/cdn/13.3.1/img/champion/";
    static final String RIOT_ITEM_IMAGE_URL =
            "https://ddragon.leagueoflegends.com/cdn/13.3.1/img/item/";

    public MutableLiveData<SummonerDto> summonerDtoMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ParticipantDto> participantDtoMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> integerMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Item> championIconMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Item> itemIconMutableLiveData = new MutableLiveData<>();
    private ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
    private ArrayList<Integer> itemIds = new ArrayList<>();

    public static RiotAPIRepository getInstance() {
        if (mRetrofitRepository == null) {
            mRetrofitRepository = new RiotAPIRepository();
        }
        return mRetrofitRepository;
    }

    public RiotAPI mRiotApi = new Retrofit.Builder()
            .baseUrl(RIOT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RiotAPI.class);

    public RiotAPI mRiotMatchApi = new Retrofit.Builder()
            .baseUrl(RIOT_MATCH_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RiotAPI.class);

    public RiotAPI mRiotProfileIconApi = new Retrofit.Builder()
            .baseUrl(RIOT_PROFILE_IMAGE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RiotAPI.class);

    public RiotAPI mRiotChampionImgApi = new Retrofit.Builder()
            .baseUrl(RIOT_CHAMPION_IMAGE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RiotAPI.class);

    public RiotAPI mRiotItemImgApi = new Retrofit.Builder()
            .baseUrl(RIOT_ITEM_IMAGE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RiotAPI.class);

    public void getSummoner(String name) {
        mRiotApi.getUserInfo(name, BuildConfig.riot_api_key).enqueue(new Callback<SummonerDto>() {
            @Override
            public void onResponse(Call<SummonerDto> call, Response<SummonerDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    summonerDtoMutableLiveData.setValue(response.body());
                    getLeagueEntry();
                    getMatch(response.body().getPuuId());
                } else {
                    summonerDtoMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<SummonerDto> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getLeagueEntry() {
        SummonerDto summonerDto = summonerDtoMutableLiveData.getValue();
        String id = summonerDto.getId();

        mRiotApi.getLeagueEntry(id, BuildConfig.riot_api_key).enqueue(
                new Callback<ArrayList<LeagueEntryDTO>>() {
                    @Override
                    public void onResponse(Call<ArrayList<LeagueEntryDTO>> call,
                                           Response<ArrayList<LeagueEntryDTO>> response) {
                        String path = "android.resource://" + R.class.getPackage().getName() + "/";
                        if (response.isSuccessful()) {
                            if (response.body().isEmpty()) {
                                path += getResourceId("UNRANK");
                                summonerDto.setTier("UNRANK");
                                summonerDto.setQueueType("");
                                summonerDto.setRank("");
                            } else {
                                path += getResourceId(response.body().get(0).getTier());
                                summonerDto.setTier(response.body().get(0).getTier());
                                summonerDto.setQueueType(response.body().get(0).getQueueType());
                                summonerDto.setRank(response.body().get(0).getRank());
                            }
                            summonerDto.setTierIconId(path);
                            summonerDtoMutableLiveData.setValue(summonerDto);
                            getProfileIconImage();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<LeagueEntryDTO>> call, Throwable t) {
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

    private void getMatch(String puuId) {
        mRiotMatchApi.getMatchesId(puuId, BuildConfig.riot_api_key, 0, 19).
                enqueue(new Callback<ArrayList<String>>() {
                    @Override
                    public void onResponse(Call<ArrayList<String>> call,
                                           Response<ArrayList<String>> response) {
                        if (response.isSuccessful()) {
                            int idx = 0;
                            integerMutableLiveData.setValue(response.body().size());
                            for (String matchId : response.body()) {
                                getMatchInfo(matchId, idx);
                                idx++;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public void getMatchInfo(String matchId, int idx) {
        mRiotMatchApi.getMatchInfo(matchId, BuildConfig.riot_api_key).enqueue(new Callback<MatchDto>() {
            @Override
            public void onResponse(Call<MatchDto> call, Response<MatchDto> response) {
                int pos = 0;
                if (response.isSuccessful()) {
                    MatchDto matchDto = response.body();
                    InfoDto infoDto = matchDto.getInfoDto();
                    ArrayList<ParticipantDto> arrayList = infoDto.getParticipantDto();
                    for (int i = 0; i < arrayList.size(); i++) {
                        if(arrayList.get(i).getSummonerName().equals(summonerDtoMutableLiveData.getValue().getName())) {
                            pos = i;
                            break;
                        }
                    }

                    ParticipantDto participantDto = infoDto.getParticipantDto().get(pos);
                    participantDtoMutableLiveData.setValue(participantDto);

                    itemIds.add(participantDto.getItem0());
                    itemIds.add(participantDto.getItem1());
                    itemIds.add(participantDto.getItem2());
                    itemIds.add(participantDto.getItem3());
                    itemIds.add(participantDto.getItem4());
                    itemIds.add(participantDto.getItem5());
                    itemIds.add(participantDto.getItem6());

                    apiListener.onUpdated();
                }
            }

            @Override
            public void onFailure(Call<MatchDto> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getProfileIconImage() {
        SummonerDto summonerDto = summonerDtoMutableLiveData.getValue();
        String path = summonerDto.getProfileIconId() + ".png";

        mRiotProfileIconApi.getImage(path).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                summonerDto.setProfileIcon(bitmap);
                summonerDtoMutableLiveData.setValue(summonerDto);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getChampionImage(String name, int idx) {
        String path = name + ".png";

        mRiotChampionImgApi.getImage(path).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Item item = new Item();
                item.setParticipantNum(idx);

                Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                item.setItemBitmap(bitmap);

                championIconMutableLiveData.setValue(item);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getItemImage(int imgId, int participantNum, int itemNum) {
        String path = imgId + ".png";

        mRiotItemImgApi.getImage(path).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Item item = new Item();
                item.setParticipantNum(participantNum);
                item.setItemNum(itemNum);

                if (response.body() != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                    item.setItemBitmap(bitmap);
                }
                else {
                    item.setItemBitmap(null);
                }
                itemIconMutableLiveData.setValue(item);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private interface ApiListener {
        void onUpdated();
    }

    private ApiListener apiListener = new ApiListener() {
        int cnt = 0;
        @Override
        public void onUpdated() {
            ParticipantDto participantDto = participantDtoMutableLiveData.getValue();

            getChampionImage(participantDto.getChampionName(), cnt);
            for(int j = 0; j < 7 ; j++) {
                getItemImage(itemIds.get(j), cnt, j);
            }
            itemIds.clear();
            cnt++;

            if (cnt == integerMutableLiveData.getValue()) cnt = 0;
        }
    };
}
