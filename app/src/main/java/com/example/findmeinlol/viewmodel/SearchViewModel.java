package com.example.findmeinlol.viewmodel;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.findmeinlol.APIListener;
import com.example.findmeinlol.model.UserRepository;
import com.example.findmeinlol.model.data.User;
import com.example.findmeinlol.model.SearchModel;
import com.example.findmeinlol.viewmodel.adapter.CallBackListener;

import java.util.ArrayList;

public class SearchViewModel extends ViewModel {
    private SearchModel mSearchModel = SearchModel.getInstance();
    public MutableLiveData<ArrayList<User>> mUserArrayListLiveData = UserRepository.getInstance().
            getUserArrayListLiveData();

    private User mUser;
    private CallBackListener mCallBackListener;

    public String name;
    public String tier;
    public long level;
    public boolean checked;

    private void addUser(User user) {
        setUi();
        mSearchModel.addUser(user);
        mUserArrayListLiveData.setValue(mSearchModel.getUserList());
    }

    public SearchModel getSearchModel() {
        return mSearchModel;
    }

    public boolean isName(String name) {
        return mSearchModel.isName(name);
    }

    public void clearList() {
        mSearchModel.clearList();
        mUserArrayListLiveData.setValue(mSearchModel.getUserList());
    }

    public void deleteUser(int idx) {
        mSearchModel.deleteUser(idx);
        mUserArrayListLiveData.setValue(mSearchModel.getUserList());
    }

    public void setUserInfo(String name) {
        mSearchModel.setUserInfo(name, mApiListener);
    }

    private APIListener mApiListener = new APIListener() {
        @Override
        public void setUser(User user) {
            mUser = user;
            if (user != null) {
                if (!isName(mUser.getName())) {
                    mSearchModel.getProfileIconBitmap(mUser.getProfileIconId(), mApiListener);
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

    public void setUi() {
        this.name = mUser.getName();
        this.tier = mUser.getTier();
        this.level = mUser.getLevel();
        this.checked = mUser.getFavorite();
    }
}
