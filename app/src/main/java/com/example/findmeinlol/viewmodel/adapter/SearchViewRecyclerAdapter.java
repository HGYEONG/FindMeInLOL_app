package com.example.findmeinlol.viewmodel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmeinlol.R;
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
        String s = searchModel.getName(position);
        holder.textView.setText(s);
    }

    @Override
    public int getItemCount() {
        return searchModel.getSize();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView ;

        ViewHolder(View view) {
            super(view);

            textView = view.findViewById(R.id.recyclerview_item_text);
        }
    }

    public SearchViewRecyclerAdapter(SearchModel searchModel) {
        this.searchModel = searchModel;
    }
}
