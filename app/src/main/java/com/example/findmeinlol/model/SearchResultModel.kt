package com.example.findmeinlol.model

import com.example.findmeinlol.model.data.Participant
import com.example.findmeinlol.model.data.Summoner

object SearchResultModel {
    private lateinit var summoner: Summoner
    private lateinit var participant: Participant
    private lateinit var participantList: ArrayList<Participant>

    fun getSummoner(): Summoner = summoner

    fun getParticipantList(): ArrayList<Participant> = participantList
}