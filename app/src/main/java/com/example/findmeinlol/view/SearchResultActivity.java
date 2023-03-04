package com.example.findmeinlol.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.findmeinlol.R;
import com.example.findmeinlol.databinding.ActivitySearchBinding;
import com.example.findmeinlol.databinding.ActivitySearchResultBinding;
import com.example.findmeinlol.model.data.User;
import com.example.findmeinlol.viewmodel.MainViewModel;
import com.example.findmeinlol.viewmodel.SearchResultViewModel;
import com.example.findmeinlol.viewmodel.SearchViewModel;
import com.google.gson.Gson;

public class SearchResultActivity extends AppCompatActivity {
    private SearchResultViewModel searchResultViewModel;
    private ActivitySearchResultBinding mBinding;
    User user;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_result);

        searchResultViewModel = new ViewModelProvider(this,
                (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory
                        .getInstance(getApplication())).get(SearchResultViewModel.class);

        mBinding.setLifecycleOwner(this);
        mBinding.setViewModel(searchResultViewModel);

        setSupportActionBar(mBinding.searchResultToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        user = new Gson().fromJson(getIntent().getStringExtra("User"), User.class);
        pos = getIntent().getIntExtra("pos", pos);
        searchResultViewModel.init(user, pos);
        setUI();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUI() {
        mBinding.searchResultImgProfile.setImageBitmap(user.getProfileIcon());
    }
}