package com.example.findmeinlol

import com.example.findmeinlol.model.data.Match
import com.example.findmeinlol.model.data.Summoner
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RiotApiKotlin {
    @GET("summoner/v4/summoners/by-name/{summonerName}")
    fun getSummoner(
        @Path("summonerName") name: String,
        @Query("api_key") api: String
    ): Call<Summoner.SummonerDto>

    @GET("league/v4/entries/by-summoner/{encryptedSummonerId}")
    fun getLeagueEntry(
        @Path("encryptedSummonerId") id: String,
        @Query("api_key") api: String
    ): Call<ArrayList<Summoner.LeagueEntry>>

    @GET("{imagePath}")
    fun getImage(@Path("imagePath") imagePath: String): Call<ResponseBody>

    @GET("/lol/match/v5/matches/by-puuid/{puuid}/ids")
    fun getMatchesId(
        @Path("puuid") puuId: String, @Query("api_key") api: String,
        @Query("start") start: Int, @Query("count") count: Int
    ): Call<ArrayList<String>>

    @GET("/lol/match/v5/matches/{matchId}")
    fun getMatchDetail(@Path("matchId") matchId: String, @Query("api_key") api: String): Call<Match>

    @GET("{jsonName}")
    fun getJson(@Path("jsonName") jsonName: String): Call<Any>
}