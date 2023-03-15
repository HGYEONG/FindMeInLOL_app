package com.example.findmeinlol.model.data;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ParticipantDto implements Comparable<ParticipantDto> {
    @SerializedName("win")
    boolean win;

    @SerializedName("deaths")
    int deaths;

    @SerializedName("kills")
    int kills;

    @SerializedName("assists")
    int assists;

    @SerializedName("championName")
    String championName;

    @SerializedName("summonerName")
    String summonerName;

    @SerializedName("item0")
    int item0;
    @SerializedName("item1")
    int item1;
    @SerializedName("item2")
    int item2;
    @SerializedName("item3")
    int item3;
    @SerializedName("item4")
    int item4;
    @SerializedName("item5")
    int item5;
    @SerializedName("item6")
    int item6;

    @SerializedName("summoner1Id")
    int summoner1Id;
    @SerializedName("summoner2Id")
    int summoner2Id;

    @SerializedName("perks")
    PerksDto perks;

    private String matchId;

    private Bitmap[] itemIcons = new Bitmap[7]; // item 번호, bitmap

    private Bitmap championIcon;

    private Bitmap[] spellIcons = new Bitmap[2];

    private Bitmap[] runeIcons = new Bitmap[2];

    private ArrayList<Integer> runeIds;

    public int getDeaths() { return this.deaths; }

    public int getKills() { return this.kills; }

    public int getAssists() { return this.assists; }

    public boolean getWin() { return this.win; }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

    public String getChampionName() {
        return this.championName;
    }

    public void setChampionIcon(Bitmap bitmap) {
        this.championIcon = bitmap;
    }

    public Bitmap getChampionIcon() {
        return this.championIcon;
    }

    public int getItem0() {
        return this.item0;
    }
    public int getItem1() {
        return this.item1;
    }
    public int getItem2() {
        return this.item2;
    }
    public int getItem3() {
        return this.item3;
    }
    public int getItem4() {
        return this.item4;
    }
    public int getItem5() {
        return this.item5;
    }
    public int getItem6() {
        return this.item6;
    }

    public Bitmap[] getItemIcons() {
        return this.itemIcons;
    }

    public String getSummonerName() {
        return this.summonerName;
    }

    public int getSummoner1Id() {
        return this.summoner1Id;
    }
    public int getSummoner2Id() {
        return this.summoner2Id;
    }

    public Bitmap[] getSpellIcons() { return this.spellIcons; }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getMatchId() {
        return this.matchId;
    }

    public PerksDto getPerks() { return this.perks; }

    public void setRuneIds(ArrayList<Integer> runeIds) {
        this.runeIds = runeIds;
    }

    public ArrayList<Integer> getRuneIds() {
        return this.runeIds;
    }

    public Bitmap[] getRuneIcons() { return this.runeIcons; }

    @Override
    public int compareTo(ParticipantDto o) {
        if(o.matchId.compareTo(matchId) > 0) {
            return 1;
        }
        else if (o.matchId.compareTo(matchId) < 0) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
