package com.example.findmeinlol.model.data

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

data class Participant(
    var participantDto: ParticipantDto? = null,
    var matchId: String = "",
    var championIcon: Bitmap? = null,
    var itemIcons: Array<Bitmap?> = arrayOfNulls(7),
    var spellIcons: Array<Bitmap?> = arrayOfNulls(2),
    var runeIcons: Array<Bitmap?> = arrayOfNulls<Bitmap>(2),
    var gameDuration: String = "",
    var queueType: String = ""
): Comparable<Participant> {

    data class ParticipantDto(
        @SerializedName("win")
        val win: Boolean,
        @SerializedName("deaths")
        val deaths: Int,
        @SerializedName("kills")
        val kills: Int,
        @SerializedName("assists")
        val assists: Int,
        @SerializedName("championName")
        val championName: String,
        @SerializedName("summonerName")
        val summonerName: String,
        @SerializedName("item0")
        val item0: Int,
        @SerializedName("item1")
        val item1: Int,
        @SerializedName("item2")
        val item2: Int,
        @SerializedName("item3")
        val item3: Int,
        @SerializedName("item4")
        val item4: Int,
        @SerializedName("item5")
        val item5: Int,
        @SerializedName("item6")
        val item6: Int,
        @SerializedName("summoner1Id")
        val summoner1Id: Int,
        @SerializedName("summoner2Id")
        val summoner2Id: Int,
        @SerializedName("perks")
        val perks: Perks
    )
    override fun compareTo(other: Participant): Int {
        return if (other.matchId > matchId) { // if는 식이라는 것을 알 수 있음
            1;
        } else if (other.matchId < matchId) {
            -1;
        } else {
            0;
        }
    }
}
