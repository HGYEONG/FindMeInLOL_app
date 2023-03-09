package com.example.findmeinlol.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.findmeinlol.RiotAPIRepository;
import com.example.findmeinlol.model.Item;
import com.example.findmeinlol.model.data.ParticipantDto;
import com.example.findmeinlol.model.data.SummonerDto;
import com.example.findmeinlol.model.SearchModel;
import com.example.findmeinlol.viewmodel.adapter.CallBackListener;

import java.util.ArrayList;

public class SearchViewModel extends ViewModel {
    private SearchModel mSearchModel = SearchModel.getInstance();
    private CallBackListener callBackListener;

    public MutableLiveData<ArrayList<SummonerDto>> liveData = new MutableLiveData<>();
    public MutableLiveData<ParticipantDto> participantDtoMutableLiveData;
    public MutableLiveData<Integer> integerMutableLiveData;
    public MutableLiveData<SummonerDto> summonerDtoMutableLiveData;
    public MutableLiveData<Item> itemIconMutableLiveData;
    public MutableLiveData<Item> championIconMutableLiveData;

    private RiotAPIRepository mRiotApiRepository = RiotAPIRepository.getInstance();

    public SearchViewModel() {
        liveData.setValue(mSearchModel.getUserList());
        this.participantDtoMutableLiveData = mRiotApiRepository.participantDtoMutableLiveData;
        this.integerMutableLiveData = mRiotApiRepository.integerMutableLiveData;
        this.summonerDtoMutableLiveData = mRiotApiRepository.summonerDtoMutableLiveData;
        this.itemIconMutableLiveData = mRiotApiRepository.itemIconMutableLiveData;
        this.championIconMutableLiveData = mRiotApiRepository.championIconMutableLiveData;
    }

    public void setCallBackListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    public void addUser() {
        mSearchModel.addUser();
    }

    public SearchModel getSearchModel() {
        return mSearchModel;
    }

    public void clearList() {
        mSearchModel.clearList();
        liveData.setValue(mSearchModel.getUserList());
    }

    public void deleteUser(int idx) {
        mSearchModel.deleteUser(idx);
        liveData.setValue(mSearchModel.getUserList());
    }

    public void getSummoner(String name) {
        mRiotApiRepository.getSummoner(name);
    }

    public void addParticipantDto(ParticipantDto participantDto) {
        mSearchModel.addParticipantDtoArrayList(participantDto);
    }

    public void setParticipantDto(ParticipantDto participantDto) {
        mSearchModel.setParticipantDto(participantDto);
    }

    public ParticipantDto getParticipantDto() {
        return mSearchModel.getParticipantDto();
    }

    public ArrayList<ParticipantDto> getParticipantArrayList() {
        return mSearchModel.getParticipantDtoArrayList();
    }
}
