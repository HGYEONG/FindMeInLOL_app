package com.example.findmeinlol.viewmodel;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.findmeinlol.APIListener;
import com.example.findmeinlol.model.data.User;
import com.example.findmeinlol.model.SearchModel;

import java.util.ArrayList;

public class SearchViewModel extends ViewModel {
    private SearchModel searchModel = new SearchModel();
    public MutableLiveData<ArrayList<User>> liveData = new MutableLiveData<>();
    private User mUser;
    private UIListener mUIListener;

    public SearchViewModel(UIListener uiListener) {
        liveData.setValue(searchModel.getUserList());
        this.mUIListener = uiListener;
    }

    private void addUser(User user) {
        searchModel.addUser(user);
        liveData.setValue(searchModel.getUserList());
    }

    public SearchModel getSearchModel() {
        return searchModel;
    }

    public boolean isName(String name) {
        return searchModel.isName(name);
    }

    public void clearList() {
        searchModel.clearList();
        liveData.setValue(searchModel.getUserList());
    }

    public void deleteUser(int idx) {
        searchModel.deleteUser(idx);
        liveData.setValue(searchModel.getUserList());
    }

    public void findUser(String name) {
        searchModel.findUser(name, mApiListener);
    }

    private APIListener mApiListener = new APIListener() {
        @Override
        public void setUser(User user) {
            mUser = user;
            if (user != null) {
                if (!isName(mUser.getName())) {
                    searchModel.getProfileIconBitmap(mUser.getProfileIconId(), mApiListener);
                    addUser(mUser);
                }
            }
            else {
                mUIListener.onUpdated(false);
            }
        }

        @Override
        public void setProfileIcon(Bitmap bitmap) {
            mUser.setProfileIcon(bitmap);
            mUIListener.onUpdated(true);
        }
    };
}
