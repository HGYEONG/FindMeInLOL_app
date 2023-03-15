package com.example.findmeinlol.view;

import androidx.annotation.NonNull;
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
import com.example.findmeinlol.model.data.ParticipantDto;
import com.example.findmeinlol.model.data.SummonerDto;
import com.example.findmeinlol.viewmodel.SearchViewModel;
import com.example.findmeinlol.viewmodel.adapter.CallBackListener;
import com.example.findmeinlol.viewmodel.adapter.SearchViewRecyclerAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SearchActivity extends AppCompatActivity {

    private SearchViewModel mSearchViewModel;
    private ActivitySearchBinding mBinding;
    private SearchViewRecyclerAdapter mSearchViewRecyclerAdapter;

    private CallBackListener callBackListener = new CallBackListener() {
        @Override
        public void itemClicked(int pos) {
            Log.d("SA", String.valueOf(pos));
            Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
            SummonerDto summonerDto = mSearchViewModel.getSearchModel().getSummoner(pos);
            intent.putExtra("Summoner", new Gson().toJson(summonerDto));
            startActivity(intent);
        }

        @Override
        public void deleteBtnClicked(int pos) {
            mSearchViewModel.deleteUser(pos);
        }

        @Override
        public void favoriteBtnClicked(int pos, boolean isChecked) {
            if (isChecked) {
                Log.d("SA", "add db");
            } else {
                Log.d("SA", "delete db");
            }
        }

        @Override
        public void onUpdated(Object response) {
        }

        @Override
        public void onFinished() {
            SummonerDto summonerDto = mSearchViewModel.getSearchModel().getSummoner();

            // 비동기화 처리로 인해 값이 순서대로 안 와서 정렬
            Collections.sort(summonerDto.getParticipantDtoArrayList());

            if (summonerDto != null) {
                if (mSearchViewModel.getSearchModel().isName(summonerDto.getName())) return;
                mSearchViewModel.addUser();
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                intent.putExtra("Summoner", new Gson().toJson(summonerDto));
                startActivity(intent);
            } else {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SearchActivity.this)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.setMessage("존재하지 않는 소환사입니다.");
                alertDialog.show();
            }
        }
    };

    @Override
    protected void onResume() {
        mSearchViewModel.liveData.setValue(mSearchViewModel.getSearchModel().getUserList());
        mSearchViewRecyclerAdapter.notifyDataSetChanged();
        super.onResume();
    }

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


    int count = 0;
    private void setObserve() {
        mSearchViewModel.liveData.observe(this, users -> {
            mSearchViewRecyclerAdapter.notifyDataSetChanged();
        });

        mSearchViewModel.summonerDtoMutableLiveData.observe(this, summonerDto -> {
            mSearchViewModel.getSearchModel().setSummoner(summonerDto);
            if (summonerDto == null) {
                callBackListener.onFinished();
            }
        });

        mSearchViewModel.participantDtoMutableLiveData.observe(this, participantDto -> {
            mSearchViewModel.addParticipantDto(participantDto);
        });

        mSearchViewModel.itemIconMutableLiveData.observe(this, item -> {
            count++;
            String matchId = item.getMatchId();
            ArrayList<ParticipantDto> participantDtoArrayList
                    = mSearchViewModel.getParticipantArrayList();
            ParticipantDto participantDto = null;

            for (int i= 0; i < participantDtoArrayList.size(); i++) {
                if (participantDtoArrayList.get(i).getMatchId() == matchId) {
                    participantDto = participantDtoArrayList.get(i);
                    break;
                }
            }

            participantDto.getItemIcons()[item.getItemNum()] = item.getItemBitmap();

            if(count == mSearchViewModel.integerMutableLiveData.getValue() * 7) {
                callBackListener.onFinished();
                count = 0;
            }
        });

        mSearchViewModel.championIconMutableLiveData.observe(this, item -> {
            String matchId = item.getMatchId();
            ArrayList<ParticipantDto> participantDtoArrayList
                    = mSearchViewModel.getParticipantArrayList();
            ParticipantDto participantDto = null;

            for (int i= 0; i < participantDtoArrayList.size(); i++) {
                if (participantDtoArrayList.get(i).getMatchId() == matchId) {
                    participantDto = participantDtoArrayList.get(i);
                    break;
                }
            }
            participantDto.setChampionIcon(item.getItemBitmap());
        });

        mSearchViewModel.spellIconMutableLiveData.observe(this, item -> {
            String matchId = item.getMatchId();
            ArrayList<ParticipantDto> participantDtoArrayList
                    = mSearchViewModel.getParticipantArrayList();
            ParticipantDto participantDto = null;

            for (int i= 0; i < participantDtoArrayList.size(); i++) {
                if (participantDtoArrayList.get(i).getMatchId() == matchId) {
                    participantDto = participantDtoArrayList.get(i);
                    break;
                }
            }
            participantDto.getSpellIcons()[item.getItemNum()] = item.getItemBitmap();
        });

        mSearchViewModel.runeIconMutableLiveData.observe(this, item -> {
            String matchId = item.getMatchId();
            ArrayList<ParticipantDto> participantDtoArrayList
                    = mSearchViewModel.getParticipantArrayList();
            ParticipantDto participantDto = null;

            for (int i= 0; i < participantDtoArrayList.size(); i++) {
                if (participantDtoArrayList.get(i).getMatchId() == matchId) {
                    participantDto = participantDtoArrayList.get(i);
                    break;
                }
            }
            Log.d("*****", "observe!");
            participantDto.getRuneIcons()[item.getItemNum()] = item.getItemBitmap();
        });
    }

    private void setListener() {
        mBinding.searchSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchViewModel.getSummoner(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    mBinding.searchSearchView.setMaxWidth(880);
                    mBinding.searchButton1.setVisibility(View.INVISIBLE);
                } else {
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
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}