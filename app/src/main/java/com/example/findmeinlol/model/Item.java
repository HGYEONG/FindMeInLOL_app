package com.example.findmeinlol.model;

import android.graphics.Bitmap;

public class Item {
    private String matchId;
    private int itemNum;
    private Bitmap itemBitmap;

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public void setItemNum(int num) {
        this.itemNum = num;
    }

    public void setItemBitmap(Bitmap bitmap) {
        this.itemBitmap = bitmap;
    }

    public String getMatchId() {
        return this.matchId;
    }

    public int getItemNum() {
        return this.itemNum;
    }

    public Bitmap getItemBitmap() {
        return this.itemBitmap;
    }
}

