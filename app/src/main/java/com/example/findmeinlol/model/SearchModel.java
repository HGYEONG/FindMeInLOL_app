package com.example.findmeinlol.model;

import com.example.findmeinlol.model.data.ParticipantDto;
import com.example.findmeinlol.model.data.SummonerDto;

import java.util.ArrayList;

public class SearchModel {
    private static SearchModel mSearchModel;
    private ArrayList<SummonerDto> summonerDtoList = new ArrayList<>();


    private SummonerDto mSummonerDto = new SummonerDto();
    private ParticipantDto participantDto = new ParticipantDto();

    public static SearchModel getInstance() {
        if (mSearchModel == null) {
            mSearchModel = new SearchModel();
        }
        return mSearchModel;
    }

    public void setSummoner(SummonerDto summonerDto) {
        mSummonerDto = summonerDto;
    }

    public void addUser() {
        summonerDtoList.add(mSummonerDto);
    }

    public boolean isName(String name) {
        for (SummonerDto summonerDto : summonerDtoList) {
            if (summonerDto.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public SummonerDto getSummoner() { return mSummonerDto; }

    public SummonerDto getSummoner(int idx) {
        return summonerDtoList.get(idx);
    }

    public int getSize() {
        return summonerDtoList.size();
    }

    public String getPuuid() {
        return mSummonerDto.getPuuId();
    }

    public void clearList() {
        summonerDtoList.clear();
    }

    public ArrayList<SummonerDto> getUserList() {
        return summonerDtoList;
    }

    public void deleteUser(int idx) {
        summonerDtoList.remove(idx);
    }

    public SummonerDto getSummonerDto() { return mSummonerDto; }

    public void addParticipantDtoArrayList(ParticipantDto participantDto) {
        mSummonerDto.addParticipantDtoArrayList(participantDto);
    }

    public ParticipantDto getParticipantDto() {
        return this.participantDto;
    }

    public void setParticipantDto(ParticipantDto participantDto) {
        this.participantDto = participantDto;
    }

    public ArrayList<ParticipantDto> getParticipantDtoArrayList() {
        return mSummonerDto.getParticipantDtoArrayList();
    }
}
