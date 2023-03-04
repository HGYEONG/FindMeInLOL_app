package com.example.findmeinlol.viewmodel;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import androidx.databinding.Bindable;
import androidx.lifecycle.ViewModel;

import com.example.findmeinlol.model.SearchModel;
import com.example.findmeinlol.model.UserRepository;
import com.example.findmeinlol.model.data.User;

public class SearchResultViewModel extends ViewModel {
    private UserRepository mUserRepository = UserRepository.getInstance();
    private SearchModel mSearchModel = SearchModel.getInstance();

    private User mUser;
    public String name;
    public String rank;
    public boolean checked;
    private int pos;

    public void init(User user, int pos) {
        this.mUser = user;
        this.name = user.getName();
        this.rank = user.getRank();
        this.checked = user.getFavorite();
        this.pos = pos;
    }

    public void favoriteBtnClicked(CompoundButton compoundButton, boolean isChecked) {
        mSearchModel.setFavorite(pos, isChecked);
        mUserRepository.getUserArrayListLiveData().setValue(mSearchModel.getUserList());
    }
}
