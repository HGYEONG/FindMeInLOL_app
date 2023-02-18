package com.example.findmeinlol.model.data;

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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getLevel() {
        return level;
    }
}
