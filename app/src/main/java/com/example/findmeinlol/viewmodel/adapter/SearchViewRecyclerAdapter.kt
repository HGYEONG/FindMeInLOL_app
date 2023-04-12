package com.example.findmeinlol.viewmodel.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.findmeinlol.databinding.RecyclerviewItemBinding
import com.example.findmeinlol.model.data.Summoner
import com.example.findmeinlol.view.CallBack
import com.example.findmeinlol.viewmodel.SearchViewModel

class SearchViewRecyclerAdapter(searchViewModel: SearchViewModel):
    RecyclerView.Adapter<SearchViewRecyclerAdapter.ViewHolder> () {
    private val searchViewModel = searchViewModel
    private lateinit var callback: CallBack

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchViewRecyclerAdapter.ViewHolder {
        val binding: RecyclerviewItemBinding = RecyclerviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewRecyclerAdapter.ViewHolder, position: Int) {
        val summoner: Summoner = searchViewModel.getSummoner(position)
        holder.recyclerviewItemBinding.summoner = summoner
        holder.recyclerviewItemBinding.recyclerviewItemImgUser.setImageBitmap(summoner.profileIcon)
        holder.recyclerviewItemBinding.recyclerviewItemImgTier.setImageURI(Uri.parse(summoner.tierIconId))
        setListener(holder, position)
    }

    override fun getItemCount(): Int {
        return searchViewModel.getSearchModel().getSize()
    }

    private fun setListener(holder: ViewHolder, position: Int) {
        holder.recyclerviewItemBinding.recyclerviewItemBtnDelete.setOnClickListener{
            searchViewModel.deleteSummoner(position)
        }
        holder.itemView.setOnClickListener{
            callback.onItemClicked(position)
        }
    }

    fun setListener(c: CallBack) {
        this.callback = c
    }

    inner class ViewHolder(binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val recyclerviewItemBinding = binding
    }
}