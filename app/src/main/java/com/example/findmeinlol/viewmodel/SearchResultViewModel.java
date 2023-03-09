package com.example.findmeinlol.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.findmeinlol.model.SearchResultModel;
import com.example.findmeinlol.model.data.ParticipantDto;
import com.example.findmeinlol.model.data.SummonerDto;

import java.util.ArrayList;

public class SearchResultViewModel extends ViewModel {
    private SearchResultModel mSearchResultModel = SearchResultModel.getInstance();
    public String name;
    public String rank;

    private void setData() {
        this.name = mSearchResultModel.getSummonerDto().getName();
        this.rank = mSearchResultModel.getSummonerDto().getTier();
    }
    public void setSummonerDto(SummonerDto summonerDto) {
        mSearchResultModel.setSummonerDto(summonerDto);
        setData();
    }

    public SummonerDto getSummonerDto() {
        return mSearchResultModel.getSummonerDto();
    }

    public void setParticipantDtoArrayList(ArrayList<ParticipantDto> participantDtoArrayList) {
        mSearchResultModel.setParticipantDtoArrayList(participantDtoArrayList);
    }

    public ArrayList<ParticipantDto> getParticipantDtoArrayList() {
        return mSearchResultModel.getParticipantDtoArrayList();
    }
}
