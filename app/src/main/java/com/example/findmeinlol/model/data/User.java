package com.example.findmeinlol.model.data;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("profileIconId")
    private int profileIconId;

    @SerializedName("summonerLevel")
    private long level;

    private Bitmap profileIcon;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getLevel() {
        return level;
    }

    public int getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIcon(Bitmap profileIcon) {
        this.profileIcon = profileIcon;
    }

    public Bitmap getProfileIcon() {
        return profileIcon;
    }
}
