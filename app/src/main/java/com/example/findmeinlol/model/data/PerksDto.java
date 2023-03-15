package com.example.findmeinlol.model.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PerksDto {
    public class PerkStyleDto {
        @SerializedName("description")
        private String description;
        @SerializedName("selections")
        private ArrayList<PerkStyleSelectionDto> selections;
        @SerializedName("style")
        private int style;

        public int getStyle() { return this.style; }

        public ArrayList<PerkStyleSelectionDto> getSelections() { return this.selections; }
    }

    public class PerkStyleSelectionDto {
        @SerializedName("perk")
        int perk;
        @SerializedName("var1")
        int var1;
        @SerializedName("var2")
        int var2;
        @SerializedName("var3")
        int var3;

        public int getPerk() {
            return this.perk;
        }
    }

    @SerializedName("styles")
    ArrayList<PerkStyleDto> styles;

    public PerkStyleDto getPerksStyleDto(int idx) {
        return styles.get(idx);
    }
}
