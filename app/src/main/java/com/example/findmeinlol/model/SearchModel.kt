package com.example.findmeinlol.model

import com.example.findmeinlol.model.data.Participant
import com.example.findmeinlol.model.data.Summoner

object SearchModel {
    private var summonerList: ArrayList<Summoner> = ArrayList()
    private var summoner: Summoner = Summoner()

    fun setSummoner(s: Summoner) {
        this.summoner = s
    }

    fun addSummoner() = summonerList.add(summoner)

    fun getSummonerIndex(name: String): Int = summonerList.indexOfFirst { it.summonerDto!!.name == name }

    fun getSummoner(): Summoner = summoner

    fun getSummoner(idx: Int): Summoner = summonerList[idx]

    fun getSummonerList(): ArrayList<Summoner> = summonerList

    fun getSize(): Int = summonerList.size

    fun clearList() = summonerList.clear()

    fun deleteSummoner(idx: Int) = summonerList.removeAt(idx)

    fun addParticipant(p: Participant) = summoner.participantList.add(p)

    fun getParticipantList(): ArrayList<Participant> = summoner.participantList
}