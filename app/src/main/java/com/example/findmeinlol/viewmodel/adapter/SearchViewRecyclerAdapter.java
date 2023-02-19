package com.example.findmeinlol.viewmodel.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmeinlol.BR;
import com.example.findmeinlol.R;
import com.example.findmeinlol.databinding.ActivitySearchBinding;
import com.example.findmeinlol.databinding.RecyclerviewItemBinding;
import com.example.findmeinlol.model.data.User;
import com.example.findmeinlol.model.SearchModel;
import com.example.findmeinlol.viewmodel.SearchViewModel;

public class SearchViewRecyclerAdapter
        extends RecyclerView.Adapter<SearchViewRecyclerAdapter.ViewHolder>
        implements ClickListener{

    SearchViewModel searchViewModel;
    SearchModel searchModel;
    ViewHolder viewHolder;

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
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {
        User user = searchModel.getUser(position);
        holder.recyclerviewItemBinding.setUser(user);
        holder.recyclerviewItemBinding.recyclerviewItemImgUser.
                setImageBitmap(user.getProfileIcon());
        holder.recyclerviewItemBinding.setClick(this);
        this.viewHolder = holder;
    }

    @Override
    public int getItemCount() {
        return searchModel.getSize();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerviewItemBinding recyclerviewItemBinding;

        ViewHolder(RecyclerviewItemBinding recyclerviewItemBinding) {
            super(recyclerviewItemBinding.getRoot());
            this.recyclerviewItemBinding = recyclerviewItemBinding;
        }
    }

    public SearchViewRecyclerAdapter(SearchViewModel searchViewModel) {
        this.searchViewModel = searchViewModel;
        searchModel = searchViewModel.getSearchModel();
    }

    @Override
    public void deleteClicked(View view) {
        searchViewModel.deleteUser(viewHolder.getLayoutPosition());
    }

    @Override
    public void favoriteClicked(View view) {
        if(viewHolder.recyclerviewItemBinding.recyclerviewItemBtnFavorite.isChecked()) {
            // ToDo: 즐겨찾기 추가
            Log.d("SVRA", "checked!");
        }
        else {
            // Todo: 즐겨찾기 삭제
            Log.d("SVRA", "unchecked!");
        }
    }
}
