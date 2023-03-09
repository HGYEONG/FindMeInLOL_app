package com.example.findmeinlol.viewmodel.adapter;

import com.example.findmeinlol.model.data.SummonerDto;

public interface CallBackListener<T> {
    void itemClicked(int pos);
    void deleteBtnClicked(int pos);
    void favoriteBtnClicked(int pos, boolean isChecked);
    void onUpdated(T response);
    void onFinished();
}
