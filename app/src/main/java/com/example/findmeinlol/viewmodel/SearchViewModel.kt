package com.example.findmeinlol.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.findmeinlol.model.SearchModel
import com.example.findmeinlol.model.data.Participant
import com.example.findmeinlol.model.data.Summoner

var summonerListLiveData: MutableLiveData<ArrayList<Summoner>> = MutableLiveData()

class SearchViewModel: ViewModel() {
    private val searchModel = SearchModel

    init {
        summonerListLiveData.value = searchModel.getSummonerList()
    }

    fun getSearchModel(): SearchModel  = searchModel

    fun addSummoner() = searchModel.addSummoner()

    fun clearList() {
        searchModel.clearList()
        summonerListLiveData.value = searchModel.getSummonerList()
    }

    fun getSummoner(): Summoner = searchModel.getSummoner()

    fun getSummoner(idx: Int): Summoner = searchModel.getSummoner(idx)

    fun getSummonerIndex(name: String): Int = searchModel.getSummonerIndex(name)

    fun deleteSummoner(idx: Int) {
        searchModel.deleteSummoner(idx)
        summonerListLiveData.value = searchModel.getSummonerList()
    }

    fun addParticipant(participant: Participant) = searchModel.addParticipant(participant)

    fun getParticipantList(): ArrayList<Participant> = searchModel.getParticipantList()
}