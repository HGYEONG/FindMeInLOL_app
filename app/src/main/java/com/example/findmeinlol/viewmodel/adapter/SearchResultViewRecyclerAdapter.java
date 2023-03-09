package com.example.findmeinlol.viewmodel.adapter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmeinlol.databinding.ResultRecyclerviewItemBinding;
import com.example.findmeinlol.model.data.ParticipantDto;
import com.example.findmeinlol.model.data.SummonerDto;
import com.example.findmeinlol.viewmodel.SearchResultViewModel;

public class SearchResultViewRecyclerAdapter
        extends RecyclerView.Adapter<SearchResultViewRecyclerAdapter.ViewHolder>{
    SearchResultViewModel mSearchResultViewModel;

    public SearchResultViewRecyclerAdapter(SearchResultViewModel searchResultViewModel) {
        this.mSearchResultViewModel = searchResultViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ResultRecyclerviewItemBinding binding = ResultRecyclerviewItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new SearchResultViewRecyclerAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SummonerDto summonerDto = mSearchResultViewModel.getSummonerDto();
        ParticipantDto participantDto = summonerDto.getParticipantDtoArrayList().get(position);
        Bitmap[] bitmaps = participantDto.getItemIcons();
        holder.resultRecyclerviewItemBinding.setParticipant(participantDto);
        holder.resultRecyclerviewItemBinding.resultRecyclerviewChampionImg.
                setImageBitmap(participantDto.getChampionIcon());

        if (bitmaps[0] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgItem0.setImageBitmap(
                    bitmaps[0]);
        }

        if (bitmaps[1] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgItem1.setImageBitmap(
                    bitmaps[1]);
        }

        if (bitmaps[2] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgItem2.setImageBitmap(
                    bitmaps[2]);
        }

        if (bitmaps[3] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgItem3.setImageBitmap(
                    bitmaps[3]);
        }

        if (bitmaps[4] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgItem4.setImageBitmap(
                    bitmaps[4]);
        }

        if (bitmaps[5] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgItem5.setImageBitmap(
                    bitmaps[5]);
        }

        if (bitmaps[6] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgItem6.setImageBitmap(
                    bitmaps[6]);
        }

        if(participantDto.getWin()) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewResult.
                    setBackgroundColor(Color.parseColor("#50B4FF"));
            holder.resultRecyclerviewItemBinding.resultRecyclerviewResult.
                    setText("승" + "\n" + "시간");
        }
        else {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewResult.
                    setBackgroundColor(Color.parseColor("#FF8282"));
            holder.resultRecyclerviewItemBinding.resultRecyclerviewResult.
                    setText("패" + "\n" + "시간");
        }

    }

    @Override
    public int getItemCount() {
        return mSearchResultViewModel.getSummonerDto().getParticipantDtoArrayList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ResultRecyclerviewItemBinding resultRecyclerviewItemBinding;

        ViewHolder(ResultRecyclerviewItemBinding recyclerviewItemBinding) {
            super(recyclerviewItemBinding.getRoot());
            this.resultRecyclerviewItemBinding = recyclerviewItemBinding;
        }
    }

}
