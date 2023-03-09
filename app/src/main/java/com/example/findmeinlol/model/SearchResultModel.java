package com.example.findmeinlol.model;

import com.example.findmeinlol.model.data.ParticipantDto;
import com.example.findmeinlol.model.data.SummonerDto;

import java.util.ArrayList;

public class SearchResultModel {
    private static SearchResultModel mSearchResultModel;

    private SummonerDto summonerDto;
    private ParticipantDto participantDto;
    private ArrayList<ParticipantDto> participantDtoArrayList;

    public static SearchResultModel getInstance() {
        if (mSearchResultModel == null) {
            mSearchResultModel = new SearchResultModel();
        }
        return mSearchResultModel;
    }

    public void setSummonerDto(SummonerDto summonerDto) {
        this.summonerDto = summonerDto;
    }

    public SummonerDto getSummonerDto() {
        return this.summonerDto;
    }

    public void setParticipantDtoArrayList(ArrayList<ParticipantDto> participantDtoArrayList) {
        this.participantDtoArrayList = participantDtoArrayList;
    }

    public ArrayList<ParticipantDto> getParticipantDtoArrayList() {
        return this.participantDtoArrayList;
    }
}
