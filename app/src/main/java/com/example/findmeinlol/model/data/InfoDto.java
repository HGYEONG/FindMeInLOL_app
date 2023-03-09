package com.example.findmeinlol.model.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class InfoDto {
    @SerializedName("participants")
    ArrayList<ParticipantDto> participantDto;

    public ArrayList<ParticipantDto> getParticipantDto() { return this.participantDto; }
}
