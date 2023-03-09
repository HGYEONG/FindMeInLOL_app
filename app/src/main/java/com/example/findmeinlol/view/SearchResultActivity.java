package com.example.findmeinlol.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.findmeinlol.R;
import com.example.findmeinlol.databinding.ActivitySearchResultBinding;
import com.example.findmeinlol.model.data.SummonerDto;
import com.example.findmeinlol.viewmodel.SearchResultViewModel;
import com.example.findmeinlol.viewmodel.adapter.SearchResultViewRecyclerAdapter;
import com.google.gson.Gson;

public class SearchResultActivity extends AppCompatActivity {
    private SearchResultViewModel mSearchResultViewModel;
    private ActivitySearchResultBinding mBinding;
    private SearchResultViewRecyclerAdapter mSearchResultViewRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_result);

        mSearchResultViewModel = new ViewModelProvider(this,
                (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory
                        .getInstance(getApplication())).get(SearchResultViewModel.class);

        mBinding.setLifecycleOwner(this);
        mBinding.setViewModel(mSearchResultViewModel);

        setSupportActionBar(mBinding.searchResultToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");


        setSearchResultViewRecyclerView();

        SummonerDto summonerDto = new Gson().fromJson(getIntent().getStringExtra("Summoner"), SummonerDto.class);
        mSearchResultViewModel.setSummonerDto(summonerDto);
        mBinding.searchResultImgProfile.setImageBitmap(summonerDto.getProfileIcon());

        addDivideLine();
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

    private void setSearchResultViewRecyclerView() {
        mBinding.searchResultRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mSearchResultViewRecyclerAdapter = new SearchResultViewRecyclerAdapter(mSearchResultViewModel);
        mBinding.searchResultRecyclerview.setAdapter(mSearchResultViewRecyclerAdapter);
    }

    private void addDivideLine() {
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(
                        getApplicationContext(),
                        new LinearLayoutManager(this).getOrientation());
        mBinding.searchResultRecyclerview.addItemDecoration(dividerItemDecoration);
    }
}