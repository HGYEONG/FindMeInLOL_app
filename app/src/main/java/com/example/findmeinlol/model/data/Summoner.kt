package com.example.findmeinlol.model.data

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName
import kotlin.collections.ArrayList

data class Summoner(
    var summonerDto: SummonerDto? = null,
    var profileIcon: Bitmap? = null,
    var tierIconId: String = "",
    var participantList: ArrayList<Participant> = ArrayList(),
    var leagueEntry: LeagueEntry? = null
) {
    data class SummonerDto(
        @SerializedName("id")
        val id: String,
        @SerializedName("puuid")
        val puuId: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("profileIconId")
        val profileIconId: Int,
        @SerializedName("level")
        val level: Long
    )

    data class LeagueEntry(
        @SerializedName("tier")
        var tier: String,
        @SerializedName("rank")
        var rank: String,
        @SerializedName("queueType")
        var queueType: String
    )
}
