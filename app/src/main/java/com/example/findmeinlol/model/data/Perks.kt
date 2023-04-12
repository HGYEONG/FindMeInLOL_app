package com.example.findmeinlol.model.data

import com.google.gson.annotations.SerializedName

data class Perks(
    @SerializedName("styles")
    val styles: ArrayList<PerkStyle>
) {
    data class PerkStyle(
        @SerializedName("description")
        val description: String,
        @SerializedName("selections")
        val selections: ArrayList<PerkStyleSelection>,
        @SerializedName("style")
        val style: Int
    )

    data class PerkStyleSelection(
        @SerializedName("perk")
        val perk: Int,
        @SerializedName("var1")
        val var1: Int,
        @SerializedName("var2")
        val var2: Int,
        @SerializedName("var3")
        val var3: Int,
    )
}
