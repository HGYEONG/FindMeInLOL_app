package com.example.findmeinlol.model.data;

import com.google.gson.annotations.SerializedName;

public class LeagueEntry {
    @SerializedName("tier")
    private String tier;

    @SerializedName("rank")
    private String rank;

    @SerializedName("queueType")
    private String queueType;

    public String getTier() { return tier; }

    public String getRank() { return rank; }

    public String getQueueType() { return queueType; }
}
