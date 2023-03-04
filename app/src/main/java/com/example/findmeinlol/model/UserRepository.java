package com.example.findmeinlol.model;

import androidx.lifecycle.MutableLiveData;

import com.example.findmeinlol.model.data.User;

import java.util.ArrayList;

public class UserRepository {
    private static UserRepository mUserRepository;
    private MutableLiveData<ArrayList<User>> mUserArrayListLiveData = new MutableLiveData<>();
    private static ArrayList<User> mUserList = SearchModel.getInstance().getUserList();

    public UserRepository () {
        mUserArrayListLiveData.setValue(mUserList);
    }

    public static UserRepository getInstance() {
        if (mUserRepository == null) {
            mUserRepository = new UserRepository();
        }
        return mUserRepository;
    }

    public MutableLiveData<ArrayList<User>> getUserArrayListLiveData() {
        return mUserArrayListLiveData;
    }
}
