package com.example.findmeinlol.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.findmeinlol.R;
import com.example.findmeinlol.databinding.ActivitySearchBinding;
import com.example.findmeinlol.model.data.User;
import com.example.findmeinlol.viewmodel.SearchViewModel;
import com.example.findmeinlol.viewmodel.adapter.CallBackListener;
import com.example.findmeinlol.viewmodel.adapter.SearchViewRecyclerAdapter;
import com.google.gson.Gson;

public class SearchActivity extends AppCompatActivity {

    private SearchViewModel mSearchViewModel;
    private ActivitySearchBinding mBinding;
    private SearchViewRecyclerAdapter mSearchViewRecyclerAdapter;


    private CallBackListener callBackListener = new CallBackListener() {
        @Override
        public void itemClicked(int pos) {
            Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
            User user = mSearchViewModel.getSearchModel().getUser(pos);
            intent.putExtra("User", new Gson().toJson(user));
            intent.putExtra("pos", pos);
            startActivity(intent);
        }

        @Override
        public void deleteBtnClicked(int pos) {
            mSearchViewModel.deleteUser(pos);
        }

        @Override
        public void favoriteBtnClicked(int pos, boolean isChecked) {
            if (isChecked) {
                mSearchViewModel.getSearchModel().getUser(pos).setFavorite(true);
            }
            else {
                mSearchViewModel.getSearchModel().getUser(pos).setFavorite(false);
            }
        }

        @Override
        public void userInfoUpdated(boolean need, User user) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(SearchActivity.this)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            if (need) {
                mSearchViewRecyclerAdapter.notifyDataSetChanged();
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                intent.putExtra("User", new Gson().toJson(user));
                startActivity(intent);
            }
            else {
                alertDialog.setMessage("존재하지 않는 소환사입니다.");
                alertDialog.show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        mSearchViewModel = new ViewModelProvider(this,
                (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory
                        .getInstance(getApplication())).get(SearchViewModel.class);

        mBinding.setLifecycleOwner(this);
        mBinding.setSearchViewModel(mSearchViewModel);

        setSupportActionBar(mBinding.searchToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");


        setListener();
        setSearchViewRecyclerView();
        setObserve();
        mSearchViewModel.setCallBackListener(callBackListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        if(mSearchViewModel.getSearchModel().getSize() > 0 ) {
            Log.d("SAA", "onResume "
                    + mSearchViewModel.getSearchModel().getUser(0).getFavorite());
        }
        super.onResume();
    }

    private void setObserve() {
        mSearchViewModel.mUserArrayListLiveData.observe(this, users -> {
            if(users.size() > 0 ) Log.d("SAA", "observe " + users.get(0).getFavorite());
            mSearchViewRecyclerAdapter.notifyDataSetChanged();
        });
    }

    private void setListener() {
        mBinding.searchSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchViewModel.setUserInfo(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    mBinding.searchSearchView.setMaxWidth(880);
                    mBinding.searchButton1.setVisibility(View.INVISIBLE);
                }
                else {
                    mBinding.searchSearchView.setMaxWidth(750);
                    mBinding.searchButton1.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        mBinding.searchButton1.setOnClickListener(v -> {
            mBinding.searchSearchView.setQuery("", false);
            mBinding.searchSearchView.setMaxWidth(880);
            mBinding.searchButton1.setVisibility(View.INVISIBLE);
            mBinding.searchSearchView.clearFocus();
        });
    }

    private void setSearchViewRecyclerView() {
        mBinding.searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchViewRecyclerAdapter = new SearchViewRecyclerAdapter(mSearchViewModel);
        mSearchViewRecyclerAdapter.setCallBackListener(callBackListener);
        mBinding.searchRecyclerView.setAdapter(mSearchViewRecyclerAdapter);
        addDivideLine();
    }

    private void addDivideLine() {
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(
                        getApplicationContext(),
                        new LinearLayoutManager(this).getOrientation());
        mBinding.searchRecyclerView.addItemDecoration(dividerItemDecoration);
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
}