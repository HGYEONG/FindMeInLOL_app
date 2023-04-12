package com.example.findmeinlol.model

import com.example.findmeinlol.model.data.Participant
import com.example.findmeinlol.model.data.Summoner

object SearchModel {
    private var summonerList: ArrayList<Summoner> = ArrayList()
    private var summoner: Summoner = Summoner()
    private var participant: Participant = Participant()

    fun setSummoner(s: Summoner) {
        this.summoner = s
    }

    fun addSummoner() = summonerList.add(summoner)

    fun isName(name: String): Boolean =
        (summonerList.asSequence().filter { it.summonerDto!!.name == name }.firstOrNull() != null)

    fun getSummoner(): Summoner = summoner

    fun getSummoner(idx: Int): Summoner = summonerList[idx]

    fun getSummonerList(): ArrayList<Summoner> = summonerList

    fun getSize(): Int = summonerList.size

    fun clearList() = summonerList.clear()

    fun deleteSummoner(idx: Int) = summonerList.removeAt(idx)

    fun addParticipant(p: Participant) = summoner.participantList.add(p)

    fun setParticipant(p: Participant) {
        this.participant = p
    }

    fun getParticipant(): Participant = participant

    fun getParticipantList(): ArrayList<Participant> = summoner.participantList
}