package com.example.findmeinlol.viewmodel;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    public void onTextClicked(View view) {
        Log.d("main", "onTextClicked!");
    }
}
