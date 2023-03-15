package com.example.findmeinlol.viewmodel.adapter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
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
        Bitmap[] itemIcons = participantDto.getItemIcons();
        Bitmap[] spellIcons = participantDto.getSpellIcons();
        Bitmap[] runeIcons = participantDto.getRuneIcons();
        holder.resultRecyclerviewItemBinding.setParticipant(participantDto);
        holder.resultRecyclerviewItemBinding.resultRecyclerviewChampionImg.
                setImageBitmap(participantDto.getChampionIcon());

        if (itemIcons[0] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgItem0.setImageBitmap(
                    itemIcons[0]);
        }

        if (itemIcons[1] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgItem1.setImageBitmap(
                    itemIcons[1]);
        }

        if (itemIcons[2] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgItem2.setImageBitmap(
                    itemIcons[2]);
        }

        if (itemIcons[3] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgItem3.setImageBitmap(
                    itemIcons[3]);
        }

        if (itemIcons[4] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgItem4.setImageBitmap(
                    itemIcons[4]);
        }

        if (itemIcons[5] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgItem5.setImageBitmap(
                    itemIcons[5]);
        }

        if (itemIcons[6] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgItem6.setImageBitmap(
                    itemIcons[6]);
        }

        if (spellIcons[0] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgSpell1.setImageBitmap(
                    spellIcons[0]);
        }
        if (spellIcons[1] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgSpell2.setImageBitmap(
                    spellIcons[1]);
        }

        if (runeIcons[0] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgRune1.setImageBitmap(
                    runeIcons[0]);
        }
        if (runeIcons[1] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgRune2.setImageBitmap(
                    runeIcons[1]);
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
