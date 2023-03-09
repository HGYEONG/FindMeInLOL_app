package com.example.findmeinlol.model;

import android.graphics.Bitmap;

import com.example.findmeinlol.model.data.ParticipantDto;

public class Item {
    private int participantNum;
    private int itemNum;
    private Bitmap itemBitmap;
    private ParticipantDto participantDto;

    public void setParticipantNum(int num) {
        this.participantNum = num;
    }

    public void setItemNum(int num) {
        this.itemNum = num;
    }

    public void setItemBitmap(Bitmap bitmap) {
        this.itemBitmap = bitmap;
    }

    public int getParticipantNum() {
        return this.participantNum;
    }

    public int getItemNum() {
        return this.itemNum;
    }

    public Bitmap getItemBitmap() {
        return this.itemBitmap;
    }

    public ParticipantDto getParticipantDto() {
        return this.participantDto;
    }

    public void setParticipantDto(ParticipantDto participantDto) {
        this.participantDto = participantDto;
    }
}

