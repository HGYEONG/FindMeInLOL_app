package com.example.findmeinlol;

import android.graphics.Bitmap;

import com.example.findmeinlol.model.data.User;

public interface APIListener {
    void setUser(User user);
    void setProfileIcon(Bitmap bitmap);
}
