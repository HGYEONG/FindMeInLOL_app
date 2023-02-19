package com.example.findmeinlol.model;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.findmeinlol.APIListener;
import com.example.findmeinlol.RiotAPIRepository;
import com.example.findmeinlol.RiotImageAPIRepository;
import com.example.findmeinlol.model.data.User;
import com.example.findmeinlol.model.retrofit.RiotAPI;
import com.example.findmeinlol.model.retrofit.RiotImageAPI;

import java.util.ArrayList;

public class SearchModel {
    private static ArrayList<User> userList = new ArrayList<>();
    private static User mUser = new User();

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

    public boolean duplicateName(String s) {
        return userList.contains(s);
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

    public void findUser(String name, APIListener apiListener) {
        RiotAPIRepository riotAPIRepository = new RiotAPIRepository(apiListener);
        riotAPIRepository.findUser(name);
    }
    public void getProfileIconBitmap(int profileIconId, APIListener apiListener) {
        RiotImageAPIRepository riotImageAPIRepository = new RiotImageAPIRepository(apiListener);
        riotImageAPIRepository.getProfileIconImage(profileIconId);
    }
}
