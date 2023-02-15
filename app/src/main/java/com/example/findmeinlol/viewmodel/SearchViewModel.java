package com.example.findmeinlol.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.findmeinlol.model.SearchModel;

import java.util.ArrayList;

public class SearchViewModel extends ViewModel {
    private SearchModel searchModel = new SearchModel();
    public MutableLiveData<ArrayList<String>> liveData = new MutableLiveData<>();

    public SearchViewModel() {
        liveData.setValue(searchModel.getNameList());
    }

    public boolean findName(String name) {
        if(searchModel.findName(name)) {
            return true;
        }
        else {
            return false;
        }
    }

    public void addName(String name) {
        searchModel.addName(name);
        liveData.setValue(searchModel.getNameList());
    }

    public SearchModel getSearchModel() {
        return searchModel;
    }

    public boolean duplicateName(String name) {
        return searchModel.duplicateName(name);
    }

    public void clearList() {
        searchModel.clearList();
        liveData.setValue(searchModel.getNameList());
    }
}
