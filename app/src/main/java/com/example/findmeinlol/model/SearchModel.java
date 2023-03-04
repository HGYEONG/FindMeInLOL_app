package com.example.findmeinlol.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.findmeinlol.APIListener;
import com.example.findmeinlol.R;
import com.example.findmeinlol.RiotAPIRepository;
import com.example.findmeinlol.RiotImageAPIRepository;
import com.example.findmeinlol.model.data.User;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchModel {
    private static SearchModel mSearchModel;
    private static ArrayList<User> userList = new ArrayList<>();
    private static User mUser = new User();

    public static SearchModel getInstance() {
        if (mSearchModel == null) {
            mSearchModel = new SearchModel();
        }
        return mSearchModel;
    }

    public void addUser(User user) {
        userList.add(user);
    }

    public boolean isName(String name) {
        for (User user : userList) {
            if (user.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public User getUser(int idx) {
        return userList.get(idx);
    }

    public int getSize() {
        return userList.size();
    }

    public void clearList() {
        userList.clear();
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void deleteUser(int idx) {
        userList.remove(idx);
    }

    public void setUserInfo(String name, APIListener apiListener) {
        RiotAPIRepository riotAPIRepository = new RiotAPIRepository(apiListener);
        riotAPIRepository.getUserInfo(name);
    }

    public void getProfileIconBitmap(int profileIconId, APIListener apiListener) {
        RiotImageAPIRepository riotImageAPIRepository = new RiotImageAPIRepository(apiListener);
        riotImageAPIRepository.getProfileIconImage(profileIconId);
    }

    public void setFavorite(int idx, boolean checked) {
        Log.d("SRM", "setFavorite " + checked);
        userList.get(idx).setFavorite(checked);
        Log.d("SRM", "setFavorite " + userList.get(idx).getFavorite());
    }
}
