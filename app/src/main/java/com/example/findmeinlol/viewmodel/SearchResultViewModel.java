package com.example.findmeinlol.viewmodel;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.databinding.Bindable;
import androidx.lifecycle.ViewModel;

import com.example.findmeinlol.model.data.User;

public class SearchResultViewModel extends ViewModel {
    private User mUser;
    public String name;
    public String rank;

    public void init(User user) {
        this.mUser = user;
        this.name = user.getName();
        this.rank = user.getRank();
    }
}
