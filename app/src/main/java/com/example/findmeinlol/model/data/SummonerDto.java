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

    private LeagueEntryDTO leagueEntryDTO;

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

    public void setTierIconId(String tierIconId) { this.tierIconId = tierIconId; }

    public String getTierIconId() { return tierIconId; }

    public void addParticipantDtoArrayList(ParticipantDto participantDto) {
        participantDtoArrayList.add(participantDto);
    }

    public ArrayList<ParticipantDto> getParticipantDtoArrayList() {
        return this.participantDtoArrayList;
    }

    public void setLeagueEntryDTO(LeagueEntryDTO leagueEntryDTO) {
        this.leagueEntryDTO = leagueEntryDTO;
    }

    public LeagueEntryDTO getLeagueEntryDTO() {
        return this.leagueEntryDTO;
    }
}
