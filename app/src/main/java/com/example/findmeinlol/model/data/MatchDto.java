package com.example.findmeinlol.model.data;

import com.google.gson.annotations.SerializedName;

public class MatchDto {
    @SerializedName("info")
    InfoDto infoDto;

    public InfoDto getInfoDto() { return this.infoDto; }
}
