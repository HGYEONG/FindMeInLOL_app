package com.example.findmeinlol.model.data;

import com.google.gson.annotations.SerializedName;

public class LeagueEntryDTO {
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
