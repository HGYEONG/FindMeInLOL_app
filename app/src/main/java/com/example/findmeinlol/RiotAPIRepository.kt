package com.example.findmeinlol

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.findmeinlol.model.data.Match
import com.example.findmeinlol.model.data.Participant
import com.example.findmeinlol.model.data.Summoner
import com.example.findmeinlol.view.CallBack
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private interface ApiCallback {
    fun onUpdated()
}

data class Item(var matchId: String = "", var bitmap: Bitmap? = null, var number: Int = 0)

object RiotAPIRepository : ApiCallback {
    private const val RIOT_URL: String = "https://kr.api.riotgames.com/lol/";
    private const val RIOT_PROFILE_IMAGE_URL: String =
        "https://ddragon.leagueoflegends.com/cdn/13.3.1/img/profileicon/";
    private const val RIOT_CHAMPION_IMAGE_URL: String =
        "https://ddragon.leagueoflegends.com/cdn/13.3.1/img/champion/";
    private const val RIOT_ITEM_IMAGE_URL: String =
        "https://ddragon.leagueoflegends.com/cdn/13.3.1/img/item/";
    private const val RIOT_MATCH_URL: String = "https://asia.api.riotgames.com/";
    private const val RIOT_JSON_URL: String = "https://ddragon.leagueoflegends.com/cdn/13.5.1/data/en_US/"
    private const val RIOT_SPELL_IMAGE_URL: String = "https://ddragon.leagueoflegends.com/cdn/13.3.1/img/spell/"
    private const val RIOT_RUNE_IMAGE_URL: String =  "https://ddragon.leagueoflegends.com/cdn/img/"

    var summonerLiveData: MutableLiveData<Summoner> = MutableLiveData()
    var participantLiveData: MutableLiveData<Participant> = MutableLiveData()
    var matchSizeLiveData: MutableLiveData<Int> = MutableLiveData()
    var championIconLiveData: MutableLiveData<Item> = MutableLiveData()
    var itemIconLiveData: MutableLiveData<Item> = MutableLiveData()
    var spellIconLiveData: MutableLiveData<Item> = MutableLiveData()
    var runeIconLiveData: MutableLiveData<Item> = MutableLiveData()

    private var itemIds: ArrayList<Int> = ArrayList()
    private var spellIds: ArrayList<Int> = ArrayList()
    private var runeIds: ArrayList<Int> = ArrayList()

    private var spellNames: HashMap<String, String> = hashMapOf()
    private var runeNames: HashMap<Int, String> = hashMapOf()

    private val riotAPI = Retrofit.Builder()
        .baseUrl(RIOT_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RiotApiKotlin::class.java)

    private val riotProfileImageAPI = Retrofit.Builder()
        .baseUrl(RIOT_PROFILE_IMAGE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RiotApiKotlin::class.java)

    private val riotMatchAPI = Retrofit.Builder()
        .baseUrl(RIOT_MATCH_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RiotApiKotlin::class.java)

    private val riotChampionImageAPI = Retrofit.Builder()
        .baseUrl(RIOT_CHAMPION_IMAGE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RiotApiKotlin::class.java)

    private val riotItemImageAPI = Retrofit.Builder()
        .baseUrl(RIOT_ITEM_IMAGE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RiotApiKotlin::class.java)

    private val riotSpellImageApi = Retrofit.Builder()
        .baseUrl(RIOT_SPELL_IMAGE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RiotApiKotlin::class.java)

    private val riotRuneImageApi = Retrofit.Builder()
        .baseUrl(RIOT_RUNE_IMAGE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RiotApiKotlin::class.java)

    private val riotJsonApi = Retrofit.Builder()
        .baseUrl(RIOT_JSON_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RiotApiKotlin::class.java)

    init {
        getJson()
    }

    private fun getJson() {
        riotJsonApi.getJson("summoner.json").enqueue(object: Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                val json = Gson().toJson(response.body())
                var jsonObject = JSONObject(json)
                jsonObject = jsonObject.getJSONObject("data")

                spellNames = jsonObject.keys().asSequence()
                    .associateBy(
                        { jsonObject.getJSONObject(it.toString()).getString("key")},
                        { it.toString() })
                        as HashMap<String, String>
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {

            }
        })

        riotJsonApi.getJson("runesReforged.json").enqueue(object: Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                val json = Gson().toJson(response.body())
                val jsonArray = JSONArray(json)

                for (i in 0 until jsonArray.length()) {
                    val mainRune = jsonArray.getJSONObject(i)
                    runeNames[mainRune.getInt("id")] = mainRune.getString("icon")
                    val slots = mainRune.getJSONArray("slots")
                    for (j in 0 until slots.length()) {
                        val secondRune = slots.getJSONObject(j).getJSONArray("runes")
                        for (k in 0 until secondRune.length()) {
                            val rune = secondRune.getJSONObject(k)
                            runeNames[rune.getInt("id")] = rune.getString("icon")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
            }

        })
    }

    fun getSummoner(name: String) {
        riotAPI.getSummoner(name, BuildConfig.riot_api_key).enqueue(object : Callback<Summoner.SummonerDto> {
            override fun onResponse(
                call: Call<Summoner.SummonerDto>,
                response: Response<Summoner.SummonerDto>
            ) {
                var summoner = Summoner()
                summoner.summonerDto = response.body()
                summonerLiveData.value = summoner

                if (response.isSuccessful) {
                    getLeagueEntry()
                    getMatch()
                }
            }

            override fun onFailure(call: Call<Summoner.SummonerDto>, t: Throwable) {
            }
        })
    }

    private fun getLeagueEntry() {
        val id: String = summonerLiveData.value!!.summonerDto!!.id

        riotAPI.getLeagueEntry(id, BuildConfig.riot_api_key)
            .enqueue(object : Callback<ArrayList<Summoner.LeagueEntry>> {
                @RequiresApi(Build.VERSION_CODES.S)
                override fun onResponse(
                    call: Call<ArrayList<Summoner.LeagueEntry>>,
                    response: Response<ArrayList<Summoner.LeagueEntry>>
                ) {
                    var path: String =
                        "android.resource://" + R::class.java.packageName.toString() + "/";
                    var leagueEntry = Summoner.LeagueEntry()

                    if (response.isSuccessful) {
                        if (response.body()?.isEmpty() != true) {
                            leagueEntry = response.body()!![0]
                        }
                        path += getResourceId(leagueEntry.tier)
                        summonerLiveData.value!!.leagueEntry = leagueEntry
                        summonerLiveData.value!!.tierIconId = path

                        getIconImage()
                    }
                }

                override fun onFailure(call: Call<ArrayList<Summoner.LeagueEntry>>, t: Throwable) {
                    println(t.message)
                }
            })
    }

    private fun getIconImage() {
        var profileIconId: Int = summonerLiveData.value!!.summonerDto!!.profileIconId
        var path = """${profileIconId}.png"""

        riotProfileImageAPI.getImage(path).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        summonerLiveData.value!!.profileIcon =
                            BitmapFactory.decodeStream(response.body()!!.byteStream())
                        summonerLiveData.value = summonerLiveData.value
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                println(t.message)
            }
        })
    }

    private fun getMatch() {
        val puuId: String = summonerLiveData.value!!.summonerDto!!.puuId

        riotMatchAPI.getMatchesId(puuId, BuildConfig.riot_api_key, 0, 19)
            .enqueue(object : Callback<ArrayList<String>> {
                override fun onResponse(
                    call: Call<ArrayList<String>>,
                    response: Response<ArrayList<String>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            matchSizeLiveData.value = it.size
                            it.forEach { id -> getMatchDetail(id) }
                        }
                    }
                }
                override fun onFailure(call: Call<ArrayList<String>>, t: Throwable) {
                }
            })
    }

    private fun getMatchDetail(matchId: String) {
        riotMatchAPI.getMatchDetail(matchId, BuildConfig.riot_api_key)
            .enqueue(object : Callback<Match> {
                override fun onResponse(call: Call<Match>, response: Response<Match>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val participants: ArrayList<Participant.ParticipantDto> = it.info.participants

                            // 반복문 돌면서 조건에 맞는 첫번째 participant
                            val participantDto: Participant.ParticipantDto = participants.asSequence()
                                .filter { p -> p.summonerName == summonerLiveData.value!!.summonerDto!!.name }
                                .first()

                            var participant = Participant()
                            participant.gameDuration = getGameDuration(it.info.gameDuration)
                            participant.matchId = matchId
                            participant.participantDto = participantDto
                            participantLiveData.value = participant

                            setIds()
                            onUpdated()
                        }
                    }
                }

                override fun onFailure(call: Call<Match>, t: Throwable) {
                }
            })
    }

    private fun getChampionIconImage(matchId: String, championName: String) {
        val path = """${championName}.png"""
        var item = Item()

        riotChampionImageAPI.getImage(path).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        item.matchId = matchId
                        item.bitmap = BitmapFactory.decodeStream(it.byteStream())
                        championIconLiveData.value = item
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })
    }

    private fun getItemImage(matchId: String, itemId: Int, pos: Int) {
        val path = """${itemId}.png"""

        riotItemImageAPI.getImage(path).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                var item = Item()
                item.matchId = matchId
                item.number = pos

                if (response.isSuccessful) {
                    item.bitmap = BitmapFactory.decodeStream(response.body()!!.byteStream())
                } else {
                    item.bitmap = null
                }
                itemIconLiveData.value = item
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }

        })
    }

    private fun getSpellImage(matchId: String, spellId: Int, pos: Int) {
        val path: String = spellNames[spellId.toString()] + ".png"

        riotSpellImageApi.getImage(path).enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                var item = Item()
                item.matchId = matchId
                item.number = pos

                if (response.isSuccessful) {
                    item.bitmap = BitmapFactory.decodeStream(response.body()!!.byteStream())
                }
                else {
                    item.bitmap = null
                }

                spellIconLiveData.value = item
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }
        })
    }

    private fun getRuneImage(matchId: String, runeId: Int, pos: Int) {
        val path: String = runeNames[runeId].toString()

        riotRuneImageApi.getImage(path).enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                var item = Item()
                item.matchId = matchId
                item.number = pos

                if (response.isSuccessful) {
                    item.bitmap = BitmapFactory.decodeStream(response.body()!!.byteStream())
                }
                else {
                    item.bitmap = null
                }

                runeIconLiveData.value = item
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }
        })
    }

    private fun setIds() {
        itemIds.add(participantLiveData.value!!.participantDto!!.item0)
        itemIds.add(participantLiveData.value!!.participantDto!!.item1)
        itemIds.add(participantLiveData.value!!.participantDto!!.item2)
        itemIds.add(participantLiveData.value!!.participantDto!!.item3)
        itemIds.add(participantLiveData.value!!.participantDto!!.item4)
        itemIds.add(participantLiveData.value!!.participantDto!!.item5)
        itemIds.add(participantLiveData.value!!.participantDto!!.item6)

        spellIds.add(participantLiveData.value!!.participantDto!!.summoner1Id)
        spellIds.add(participantLiveData.value!!.participantDto!!.summoner2Id)

        runeIds.add(participantLiveData.value!!.participantDto!!.perks.styles[0].selections[0].perk)
        runeIds.add(participantLiveData.value!!.participantDto!!.perks.styles[1].style)
    }

    private fun getResourceId(resource: String): Int {
        return when (resource) {
            "BRONZE" -> R.drawable.emblem_bronze
            "IRON" -> R.drawable.emblem_iron
            "SLIVER" -> R.drawable.emblem_silver
            "GOLD" -> R.drawable.emblem_gold
            "PLATINUM" -> R.drawable.emblem_platinum
            "DIAMOND" -> R.drawable.emblem_diamond
            "MASTER" -> R.drawable.emblem_master
            "GRANDMASTER" -> R.drawable.emblem_grandmaster
            "CHALLENGER" -> R.drawable.emblem_challenger
            else -> {
                R.drawable.emblem_unrank
            }
        }
    }

    private fun getGameDuration(gameDuration: Long): String {
        val minute = TimeUnit.SECONDS.toMinutes(gameDuration)
        - TimeUnit.SECONDS.toHours(gameDuration) * 60
        val second = gameDuration - minute * 60

        return """${minute}:${second}"""
    }

    override fun onUpdated() {
        val participant: Participant = participantLiveData.value!!

        getChampionIconImage(participant.matchId, participant.participantDto!!.championName)
        for (i in 0..6) {
            getItemImage(participant.matchId, itemIds[i], i)
        }
        for (i in 0..1) {
            getSpellImage(participant.matchId, spellIds[i], i)
        }
        for (i in 0..1) {
            getRuneImage(participant.matchId, runeIds[i], i)
        }

        itemIds.clear()
        runeIds.clear()
        spellIds.clear()
    }
}