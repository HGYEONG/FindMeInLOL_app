package com.example.findmeinlol.model.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class SummonerDto {
    @SerializedName("id")
    private String id;

    @SerializedName("puuid")
    private String puuId;

    @SerializedName("name")
    private String name;

    @SerializedName("profileIconId")
    private int profileIconId;

    @SerializedName("summonerLevel")
    private long level;

    @SerializedName("tier")
    private String tier;

    @SerializedName("rank")
    private String rank;

    @SerializedName("queueType")
    private String queueType;

    private Bitmap profileIcon;

    private String tierIconId;

    private ArrayList<ParticipantDto> participantDtoArrayList = new ArrayList<>();

    public String getId() {
        return id;
    }

    public String getPuuId() {
        return puuId;
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

    public void setProfileIcon(Bitmap profileIcon) { this.profileIcon = profileIcon; }

    public Bitmap getProfileIcon() {
        return profileIcon;
    }

    public String getTier() { return tier; }

    public String getRank() { return rank; }

    public String getQueueType() { return queueType; }

    public void setTier(String tier) { this.tier = tier; }

    public void setRank(String rank) { this.rank = rank; }

    public void setQueueType(String queueType) { this.queueType = queueType; }

    public void setTierIconId(String tierIconId) { this.tierIconId = tierIconId; }

    public String getTierIconId() { return tierIconId; }

    public void addParticipantDtoArrayList(ParticipantDto participantDto) {
        participantDtoArrayList.add(participantDto);
    }

    public ArrayList<ParticipantDto> getParticipantDtoArrayList() {
        return this.participantDtoArrayList;
    }
}
