package com.example.findmeinlol.viewmodel.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmeinlol.databinding.RecyclerviewItemBinding;
import com.example.findmeinlol.model.data.SummonerDto;
import com.example.findmeinlol.model.SearchModel;
import com.example.findmeinlol.viewmodel.SearchViewModel;

public class SearchViewRecyclerAdapter
        extends RecyclerView.Adapter<SearchViewRecyclerAdapter.ViewHolder> {

    SearchViewModel mSearchViewModel;
    SearchModel mSearchModel;
    CallBackListener mCallBackListener;

    public SearchViewRecyclerAdapter(SearchViewModel searchViewModel) {
        this.mSearchViewModel = searchViewModel;
        mSearchModel = mSearchViewModel.getSearchModel();
    }

    public void setCallBackListener(CallBackListener mCallBackListener) {
        this.mCallBackListener = mCallBackListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                   int viewType) {
        RecyclerviewItemBinding binding = RecyclerviewItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SummonerDto summonerDto = mSearchViewModel.getSearchModel().getSummoner(position);
        holder.recyclerviewItemBinding.setSummonerDto(summonerDto);
        holder.recyclerviewItemBinding.recyclerviewItemImgUser.setImageBitmap(summonerDto.getProfileIcon());
        holder.recyclerviewItemBinding.recyclerviewItemImgTier.setImageURI(Uri.parse(summonerDto.getTierIconId()));
        setListener(holder, position);
    }

    private void setListener(ViewHolder holder, int position) {
        holder.recyclerviewItemBinding.recyclerviewItemBtnFavorite.setOnClickListener(v -> {
            mCallBackListener.favoriteBtnClicked(position,
                    holder.recyclerviewItemBinding.recyclerviewItemBtnFavorite.isChecked());
        });

        holder.recyclerviewItemBinding.recyclerviewItemBtnDelete.setOnClickListener(v -> {
            mCallBackListener.deleteBtnClicked(position);
        });

        holder.itemView.setOnClickListener(v -> {
            mCallBackListener.itemClicked(position);
        });
    }

    @Override
    public int getItemCount() {
        return mSearchModel.getSize();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerviewItemBinding recyclerviewItemBinding;

        ViewHolder(RecyclerviewItemBinding recyclerviewItemBinding) {
            super(recyclerviewItemBinding.getRoot());
            this.recyclerviewItemBinding = recyclerviewItemBinding;
        }
    }
}
