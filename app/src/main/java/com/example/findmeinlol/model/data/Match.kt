package com.example.findmeinlol.model.data

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp
import java.util.ArrayList

data class Match(
    @SerializedName("info")
    val info: Info
) {
    data class Info(
        @SerializedName("participants")
        val participants: ArrayList<Participant.ParticipantDto>,
        @SerializedName("gameDuration")
        val gameDuration: Long,
        @SerializedName("gameStartTimeStamp")
        val gameStartTimeStamp: Long,
        @SerializedName("gameEndTimeStamp")
        val gameEndTimestamp: Long,
        @SerializedName("queueId")
        val queueId: Int
    )
}