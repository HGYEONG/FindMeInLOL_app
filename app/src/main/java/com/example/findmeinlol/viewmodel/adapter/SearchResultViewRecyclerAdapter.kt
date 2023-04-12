package com.example.findmeinlol.viewmodel.adapter

import android.graphics.Bitmap
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.findmeinlol.databinding.ResultRecyclerviewItemBinding
import com.example.findmeinlol.model.data.Participant
import com.example.findmeinlol.model.data.Participant.ParticipantDto
import com.example.findmeinlol.model.data.Summoner
import com.example.findmeinlol.model.data.Summoner.SummonerDto
import com.example.findmeinlol.viewmodel.SearchResultViewModel

class SearchResultViewRecyclerAdapter(searchResultViewModel: SearchResultViewModel) :
    RecyclerView.Adapter<SearchResultViewRecyclerAdapter.ViewHolder>() {

    private val searchResultViewModel = searchResultViewModel
    private lateinit var itemIcons: Array<Bitmap?>
    private lateinit var spellIcons: Array<Bitmap?>
    private lateinit var runeIcons: Array<Bitmap?>
    private lateinit var participant: Participant

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ResultRecyclerviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        participant = searchResultViewModel.getSummoner().participantList[position]
        itemIcons = participant.itemIcons
        spellIcons = participant.spellIcons
        runeIcons = participant.runeIcons

        holder.resultRecyclerviewItemBinding.participant = participant
        holder.resultRecyclerviewItemBinding.resultRecyclerviewChampionImg
            .setImageBitmap(participant.championIcon)

        setUI(holder)
    }

    private fun setUI(holder: ViewHolder) {
        if (itemIcons[0] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgItem0.setImageBitmap(
                itemIcons[0]
            )
        }
        if (itemIcons[1] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgItem1.setImageBitmap(
                itemIcons[1]
            )
        }
        if (itemIcons[2] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgItem2.setImageBitmap(
                itemIcons[2]
            )
        }
        if (itemIcons[3] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgItem3.setImageBitmap(
                itemIcons[3]
            )
        }
        if (itemIcons[4] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgItem4.setImageBitmap(
                itemIcons[4]
            )
        }
        if (itemIcons[5] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgItem5.setImageBitmap(
                itemIcons[5]
            )
        }
        if (itemIcons[6] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgItem6.setImageBitmap(
                itemIcons[6]
            )
        }
        if (spellIcons[0] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgSpell1.setImageBitmap(
                spellIcons[0]
            )
        }
        if (spellIcons[1] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgSpell2.setImageBitmap(
                spellIcons[1]
            )
        }
        if (runeIcons[0] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgRune1.setImageBitmap(
                runeIcons[0]
            )
        }
        if (runeIcons[1] != null) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewImgRune2.setImageBitmap(
                runeIcons[1]
            )
        }

        if (participant.participantDto!!.win) {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewResult.setBackgroundColor(
                Color.parseColor(
                    "#50B4FF"
                )
            )
            holder.resultRecyclerviewItemBinding.resultRecyclerviewResult.text = """
                승
                시간
                """
        } else {
            holder.resultRecyclerviewItemBinding.resultRecyclerviewResult.setBackgroundColor(
                Color.parseColor(
                    "#FF8282"
                )
            )
            holder.resultRecyclerviewItemBinding.resultRecyclerviewResult.text = """
                패
                시간
                """
        }
    }

    override fun getItemCount(): Int {
        return searchResultViewModel.getSummoner().participantList.size
    }

    inner class ViewHolder(binding: ResultRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            val resultRecyclerviewItemBinding = binding
        }
}