package com.example.findmeinlol.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.findmeinlol.R;
import com.example.findmeinlol.databinding.ActivitySearchBinding;
import com.example.findmeinlol.model.SearchModel;
import com.example.findmeinlol.viewmodel.SearchViewModel;
import com.example.findmeinlol.viewmodel.adapter.SearchViewRecyclerAdapter;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private SearchViewModel searchViewModel;
    private ActivitySearchBinding binding;
    private SearchViewRecyclerAdapter searchViewRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        searchViewModel = new ViewModelProvider(this,
                (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication())).get(SearchViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setSearchViewModel(searchViewModel);

        setSupportActionBar(binding.searchToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        setListener();
        setSearchViewRecyclerView();
        setObserve();
    }

    private void setObserve() {
        searchViewModel.liveData.observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                searchViewRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }
    private void setListener() {
        binding.searchSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (searchViewModel.findName(query)) {
                    if (!searchViewModel.duplicateName(query)) {
                        Log.d("SVAT", "add name : " + query);
                        searchViewModel.addName(query);
                    }
                    else {
                        Log.d("SVAT", "not add name : " + query);
                    }
                }
                else {
                    Log.d("SVAT", "not add name : " + query);
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "존재하지 않는 소환사 입니다.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                binding.searchSearchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    binding.searchSearchView.setMaxWidth(880);
                    binding.searchButton1.setVisibility(View.INVISIBLE);
                }
                else {
                    binding.searchSearchView.setMaxWidth(750);
                    binding.searchButton1.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        binding.searchButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchSearchView.setQuery("",false);
                binding.searchSearchView.setMaxWidth(880);
                binding.searchButton1.setVisibility(View.INVISIBLE);
                binding.searchSearchView.clearFocus();
            }
        });
    }

    private void setSearchViewRecyclerView() {
        binding.searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchViewRecyclerAdapter = new SearchViewRecyclerAdapter(searchViewModel.getSearchModel());
        binding.searchRecyclerView.setAdapter(searchViewRecyclerAdapter);

        addDivideLine();
    }

    private void addDivideLine() {
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(
                        getApplicationContext(),
                        new LinearLayoutManager(this).getOrientation());
        binding.searchRecyclerView.addItemDecoration(dividerItemDecoration);
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