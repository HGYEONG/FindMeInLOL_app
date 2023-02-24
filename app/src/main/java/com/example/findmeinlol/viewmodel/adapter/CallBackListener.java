package com.example.findmeinlol.viewmodel.adapter;

import com.example.findmeinlol.model.data.User;

public interface CallBackListener {
    void itemClicked(int pos);
    void deleteBtnClicked(int pos);
    void favoriteBtnClicked(int pos, boolean isChecked);
    void userInfoUpdated(boolean need, User user);
}
