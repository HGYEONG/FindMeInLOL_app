package com.example.findmeinlol.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findmeinlol.R
import com.example.findmeinlol.databinding.ActivitySearchResultBinding
import com.example.findmeinlol.model.data.Summoner
import com.example.findmeinlol.viewmodel.SearchResultViewModel
import com.example.findmeinlol.viewmodel.adapter.SearchResultViewRecyclerAdapter
import com.google.gson.Gson

class SearchResultActivity : AppCompatActivity() {
    private lateinit var searchResultViewModel: SearchResultViewModel
    private lateinit var binding: ActivitySearchResultBinding
    private lateinit var searchResultViewRecyclerAdapter: SearchResultViewRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_result)

        searchResultViewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(SearchResultViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = searchResultViewModel

        setSupportActionBar(binding.searchResultToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        val summoner = Gson().fromJson(intent.getStringExtra("Summoner"), Summoner::class.java)
        searchResultViewModel.setSummoner(summoner)
        binding.searchResultImgProfile.setImageBitmap(summoner.profileIcon)

        setSearchResultViewRecyclerView()
        addDivideLine()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> { super.onOptionsItemSelected(item) }
        }
    }

    private fun setSearchResultViewRecyclerView() {
        binding.searchResultRecyclerview.layoutManager = LinearLayoutManager(this)
        searchResultViewRecyclerAdapter = SearchResultViewRecyclerAdapter(searchResultViewModel)
        binding.searchResultRecyclerview.adapter = searchResultViewRecyclerAdapter
    }

    private fun addDivideLine() {
        binding.searchResultRecyclerview.addItemDecoration(DividerItemDecoration(
                applicationContext,
                LinearLayoutManager(this).orientation))
    }
}