package com.example.findmeinlol.viewmodel;

import android.graphics.Bitmap;

import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.findmeinlol.APIListener;
import com.example.findmeinlol.model.data.User;
import com.example.findmeinlol.model.SearchModel;
import com.example.findmeinlol.viewmodel.adapter.CallBackListener;

import java.util.ArrayList;

public class SearchViewModel extends ViewModel {
    private SearchModel searchModel = new SearchModel();
    public MutableLiveData<ArrayList<User>> liveData = new MutableLiveData<>();
    private User mUser;
    private CallBackListener mCallBackListener;

    public SearchViewModel() {
        liveData.setValue(searchModel.getUserList());
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

    public void setUserInfo(String name) {
        searchModel.setUserInfo(name, mApiListener);
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
                mCallBackListener.userInfoUpdated(false, mUser);
            }
        }

        @Override
        public void setProfileIcon(Bitmap bitmap) {
            mUser.setProfileIcon(bitmap);
            mCallBackListener.userInfoUpdated(true, mUser);
        }
    };

    public void setCallBackListener(CallBackListener callBackListener) {
        this.mCallBackListener = callBackListener;
    }
}
