package com.example.findmeinlol.model;

import com.example.findmeinlol.model.data.User;

import java.util.ArrayList;

public class SearchModel {
    private static ArrayList<User> userList = new ArrayList<>();
    private static User mUser = new User();

    public void addUser(User user) {
        userList.add(user);
    }
    
    public User getUser(int idx) {return userList.get(idx); }

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
}
