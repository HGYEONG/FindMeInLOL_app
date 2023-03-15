package com.example.findmeinlol;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.findmeinlol.model.Item;
import com.example.findmeinlol.model.data.InfoDto;
import com.example.findmeinlol.model.data.LeagueEntryDTO;
import com.example.findmeinlol.model.data.MatchDto;
import com.example.findmeinlol.model.data.ParticipantDto;
import com.example.findmeinlol.model.data.PerksDto;
import com.example.findmeinlol.model.data.SummonerDto;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

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
    static final String RIOT_SPELL_IMAGE_URL =
            "https://ddragon.leagueoflegends.com/cdn/13.3.1/img/spell/";
    static final String RIOT_SPELL_JSON_URL =
            "https://ddragon.leagueoflegends.com/cdn/13.5.1/data/en_US/";
    static final String RIOT_RUNES_JSON_URL =
            "https://ddragon.leagueoflegends.com/cdn/13.5.1/data/en_US/";
    static final String RIOT_RUNES_IMAGE_URL =
            "https://ddragon.leagueoflegends.com/cdn/img/";

    public MutableLiveData<SummonerDto> summonerDtoMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ParticipantDto> participantDtoMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> integerMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Item> championIconMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Item> itemIconMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Item> spellIconMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Item> runeIconMutableLiveData = new MutableLiveData<>();
    private ArrayList<Integer> itemIds = new ArrayList<>();
    private ArrayList<Integer> spellIds = new ArrayList<>();
    private ArrayList<Integer> runeIds = new ArrayList<>();
    private HashMap<String, String> spellNames = new HashMap<>();
    private HashMap<Integer, String> runeNames = new HashMap<>();


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

    public RiotAPI mRiotSpellImgApi = new Retrofit.Builder()
            .baseUrl(RIOT_SPELL_IMAGE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RiotAPI.class);

    public RiotAPI mRiotSpellJSonApi = new Retrofit.Builder()
            .baseUrl(RIOT_SPELL_JSON_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RiotAPI.class);

    public RiotAPI mRiotRunesJsonApi = new Retrofit.Builder()
            .baseUrl(RIOT_RUNES_JSON_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RiotAPI.class);

    public RiotAPI mRiotRuneImgApi = new Retrofit.Builder()
            .baseUrl(RIOT_RUNES_IMAGE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RiotAPI.class);

    private interface ApiListener {
        void onUpdated();
    }

    private ApiListener apiListener = new ApiListener() {
        int cnt = 0;
        @Override
        public void onUpdated() {
            ParticipantDto participantDto = participantDtoMutableLiveData.getValue();

            getChampionImage(participantDto.getChampionName(), participantDto.getMatchId());
            for (int i = 0; i < 7 ; i++) {
                getItemImage(itemIds.get(i), participantDto.getMatchId(), i);
            }
            for (int i = 0; i < 2; i++) {
                getSpellImage(spellIds.get(i), participantDto.getMatchId(), i);
            }

            for (int i = 0; i < 2; i++) {
                getRuneImage(runeIds.get(i), participantDto.getMatchId(), i);
            }

            itemIds.clear();
            spellIds.clear();
            runeIds.clear();
            cnt++;

            if (cnt == integerMutableLiveData.getValue()) cnt = 0;
        }
    };

    public void getSummoner(String name) {
        mRiotApi.getUserInfo(name, BuildConfig.riot_api_key).enqueue(new Callback<SummonerDto>() {
            @Override
            public void onResponse(Call<SummonerDto> call, Response<SummonerDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    summonerDtoMutableLiveData.setValue(response.body());
                    getLeagueEntry();
                    getMatch(response.body().getPuuId());
                    getSpellJson();
                    getPerksJson();
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
        LeagueEntryDTO leagueEntryDTO = new LeagueEntryDTO();
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
                                leagueEntryDTO.setTier("UNRANK");
                                leagueEntryDTO.setQueueType("");
                                leagueEntryDTO.setRank("");
                            } else {
                                path += getResourceId(response.body().get(0).getTier());
                                leagueEntryDTO.setTier(response.body().get(0).getTier());
                                leagueEntryDTO.setQueueType(response.body().get(0).getQueueType());
                                leagueEntryDTO.setRank(response.body().get(0).getRank());
                            }
                            summonerDto.setTierIconId(path);
                            summonerDto.setLeagueEntryDTO(leagueEntryDTO);
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
                            integerMutableLiveData.setValue(response.body().size());
                            for (String matchId : response.body()) {
                                getMatchInfo(matchId);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private String timeFormat(Long milliseconds) {
        String result = "";

        long hours = TimeUnit.MILLISECONDS.toHours(milliseconds);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds);

        result = Long.toString(hours);
        result += Long.toString(minutes);
        result += Long.toString(seconds);

        return result;
    }

    public void getMatchInfo(String matchId) {
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
                    participantDto.setMatchId(matchId);

                    PerksDto perksDto = participantDto.getPerks();
                    PerksDto.PerkStyleDto mainPerkStyleDto = perksDto.getPerksStyleDto(0);
                    runeIds.add(mainPerkStyleDto.getSelections().get(0).getPerk());
                    PerksDto.PerkStyleDto subPerkStyleDto = perksDto.getPerksStyleDto(1);
                    runeIds.add(subPerkStyleDto.getStyle());
                    participantDto.setRuneIds(runeIds);

                    participantDtoMutableLiveData.setValue(participantDto);

                    itemIds.add(participantDto.getItem0());
                    itemIds.add(participantDto.getItem1());
                    itemIds.add(participantDto.getItem2());
                    itemIds.add(participantDto.getItem3());
                    itemIds.add(participantDto.getItem4());
                    itemIds.add(participantDto.getItem5());
                    itemIds.add(participantDto.getItem6());

                    spellIds.add(participantDto.getSummoner1Id());
                    spellIds.add(participantDto.getSummoner2Id());

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

    private void getChampionImage(String name, String matchId) {
        String path = name + ".png";

        mRiotChampionImgApi.getImage(path).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Item item = new Item();
                item.setMatchId(matchId);

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

    private void getItemImage(int imgId, String matchId, int itemNum) {
        String path = imgId + ".png";

        mRiotItemImgApi.getImage(path).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Item item = new Item();
                item.setMatchId(matchId);
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

    private void getSpellImage(int imgId, String matchId, int spellNum) {
        String spellName = spellNames.get(Integer.toString(imgId));
        String path = spellName + ".png";

        mRiotSpellImgApi.getImage(path).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Item item = new Item();
                item.setMatchId(matchId);
                item.setItemNum(spellNum);
                if (response.body() != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                    item.setItemBitmap(bitmap);
                }
                else {
                    item.setItemBitmap(null);
                }
                spellIconMutableLiveData.setValue(item);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getSpellJson() {
        ArrayList<String> nameKeys = new ArrayList<>();

        mRiotSpellJSonApi.getSpellJson().enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                String json = new Gson().toJson(response.body());
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    jsonObject = jsonObject.getJSONObject("data");
                    Iterator iterator = jsonObject.keys();

                    while(iterator.hasNext()) {
                        String name = iterator.next().toString();
                        nameKeys.add(name);
                    }

                    for (int i = 0; i < nameKeys.size(); i++) {
                        JSONObject spell = jsonObject.getJSONObject(nameKeys.get(i));
                        String key = spell.getString("key");
                        spellNames.put(key, nameKeys.get(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    private void getPerksJson() {
        mRiotRunesJsonApi.getRuneJson().enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                String json = new Gson().toJson(response.body());
                try {
                    JSONArray jsonArray = new JSONArray(json);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject mainRune = jsonArray.getJSONObject(i);
                        runeNames.put(mainRune.getInt("id"),
                                mainRune.getString("icon"));
                        JSONArray slots = mainRune.getJSONArray("slots");
                        for (int j = 0; j < slots.length(); j++) {
                            JSONObject runes = slots.getJSONObject(j);
                            JSONArray rune = runes.getJSONArray("runes");
                            for (int k = 0; k < rune.length(); k++) {
                                JSONObject jsonObject = rune.getJSONObject(k);
                                runeNames.put(jsonObject.getInt("id"),
                                        jsonObject.getString("icon"));
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    private void getRuneImage(int imgId, String matchId, int spellNum) {
        String path = runeNames.get(imgId);

        mRiotRuneImgApi.getImage(path).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Item item = new Item();
                item.setMatchId(matchId);
                item.setItemNum(spellNum);
                if (response.body() != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                    item.setItemBitmap(bitmap);
                }
                else {
                    item.setItemBitmap(null);
                }
                runeIconMutableLiveData.setValue(item);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
