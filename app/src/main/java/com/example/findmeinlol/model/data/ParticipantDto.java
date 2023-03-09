package com.example.findmeinlol.model.data;

import android.graphics.Bitmap;

import com.example.findmeinlol.model.Item;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

public class ParticipantDto {
    @SerializedName("win")
    boolean win;

    @SerializedName("deaths")
    int deaths;

    @SerializedName("kills")
    int kills;

    @SerializedName("assists")
    int assists;

    @SerializedName("timePlayed")
    int timePlayed;

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

    private Bitmap[] itemIcons = new Bitmap[7]; // item 번호, bitmap

    private Bitmap championIcon;

    public int getDeaths() { return this.deaths; }

    public int getKills() { return this.kills; }

    public int getAssists() { return this.assists; }

    public int getTimePlayed() { return this.timePlayed; }

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

    public void addItemIcons(int idx, Bitmap bitmap) {
        itemIcons[idx] = bitmap;
    }

    public Bitmap[] getItemIcons() {
        return this.itemIcons;
    }

    public String getSummonerName() {
        return this.summonerName;
    }
}
