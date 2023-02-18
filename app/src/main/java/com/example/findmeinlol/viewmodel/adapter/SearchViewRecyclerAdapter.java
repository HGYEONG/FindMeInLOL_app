package com.example.findmeinlol.viewmodel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmeinlol.R;
import com.example.findmeinlol.model.data.User;
import com.example.findmeinlol.model.SearchModel;

public class SearchViewRecyclerAdapter
        extends RecyclerView.Adapter<SearchViewRecyclerAdapter.ViewHolder>{

    SearchModel searchModel;

    @NonNull
    @Override
    public SearchViewRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                   int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
        SearchViewRecyclerAdapter.ViewHolder viewHolder
                = new SearchViewRecyclerAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewRecyclerAdapter.ViewHolder holder,
                                 int position) {
        User user = searchModel.getUser(position);

        holder.name.setText(user.getName());
        holder.level.setText(String.valueOf(user.getLevel()));
    }

    @Override
    public int getItemCount() {
        return searchModel.getSize();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView level;

        ViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.recyclerview_item_text_name);
            level = view.findViewById(R.id.recyclerview_item_text_level);
        }
    }

    public SearchViewRecyclerAdapter(SearchModel searchModel) {
        this.searchModel = searchModel;
    }
}
