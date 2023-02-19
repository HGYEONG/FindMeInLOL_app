package com.example.findmeinlol.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.Contacts;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.findmeinlol.RiotAPIRepository;
import com.example.findmeinlol.R;
import com.example.findmeinlol.RiotImageAPIRepository;
import com.example.findmeinlol.APIListener;
import com.example.findmeinlol.model.data.User;
import com.example.findmeinlol.databinding.ActivitySearchBinding;
import com.example.findmeinlol.viewmodel.SearchViewModel;
import com.example.findmeinlol.viewmodel.UIListener;
import com.example.findmeinlol.viewmodel.adapter.SearchViewRecyclerAdapter;

public class SearchActivity extends AppCompatActivity {

    private SearchViewModel mSearchViewModel;
    private ActivitySearchBinding mBinding;
    private SearchViewRecyclerAdapter mSearchViewRecyclerAdapter;
    private UIListener mUiListener;

    class SearchViewModelFactory implements ViewModelProvider.Factory {
        UIListener uiListener;

        public SearchViewModelFactory(UIListener uiListener) {
            this.uiListener = uiListener;
        }
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(SearchViewModel.class)) {
                return (T) new SearchViewModel(this.uiListener);
            }
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        mUiListener = new UIListener() {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(SearchActivity.this)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            @Override
            public void onUpdated(boolean flag) {
                if (flag) {
                    mSearchViewRecyclerAdapter.notifyDataSetChanged();
                }
                else {
                    alertDialog.setMessage("존재하지 않는 소환사입니다.");
                    alertDialog.show();
                }
            }
        };

        SearchViewModelFactory searchViewModelFactory = new SearchViewModelFactory(mUiListener);
        mSearchViewModel = new ViewModelProvider(this,searchViewModelFactory).
                get(SearchViewModel.class);

        mBinding.setLifecycleOwner(this);
        mBinding.setSearchViewModel(mSearchViewModel);

        setSupportActionBar(mBinding.searchToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        setListener();
        setSearchViewRecyclerView();
        setObserve();
    }

    private void setObserve() {
        mSearchViewModel.liveData.observe(this, users -> {
            mSearchViewRecyclerAdapter.notifyDataSetChanged();
        });
    }
    private void setListener() {
        mBinding.searchSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchViewModel.findUser(query);
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