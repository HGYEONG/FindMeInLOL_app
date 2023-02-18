package com.example.findmeinlol.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.findmeinlol.model.data.User;
import com.example.findmeinlol.model.SearchModel;

import java.util.ArrayList;

public class SearchViewModel extends ViewModel {
    private SearchModel searchModel = new SearchModel();
    public MutableLiveData<ArrayList<User>> liveData = new MutableLiveData<>();

    public SearchViewModel() {
        liveData.setValue(searchModel.getUserList());
    }

    public void addUser(User user) {
        searchModel.addUser(user);
        liveData.setValue(searchModel.getUserList());
    }

    public SearchModel getSearchModel() {
        return searchModel;
    }

    public boolean duplicateName(String name) {
        return searchModel.duplicateName(name);
    }

    public void clearList() {
        searchModel.clearList();
        liveData.setValue(searchModel.getUserList());
    }
}
