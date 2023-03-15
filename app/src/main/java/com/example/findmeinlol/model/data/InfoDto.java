package com.example.findmeinlol.model.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class InfoDto {
    @SerializedName("participants")
    private ArrayList<ParticipantDto> participantDto;

    @SerializedName("gameStartTimeStamp")
    private long gameStartTimeStamp;

    @SerializedName("gameEndTimeStamp")
    private long gameEndTimeStamp;

    public ArrayList<ParticipantDto> getParticipantDto() { return this.participantDto; }

    public long getTimeStamp() {
        return this.gameEndTimeStamp - this.gameStartTimeStamp;
    }
}
