package com.example.findmeinlol.viewmodel

import androidx.lifecycle.ViewModel
import com.example.findmeinlol.model.data.Summoner

class SearchResultViewModel: ViewModel() {
    private lateinit var summoner: Summoner
    lateinit var name: String
    lateinit var rank: String

    fun setSummoner(s: Summoner) {
        summoner = s
        setUi()
    }

    fun getSummoner(): Summoner = summoner

    private fun setUi() {
        name = summoner.summonerDto!!.name
        rank = summoner.leagueEntry!!.rank
    }
}