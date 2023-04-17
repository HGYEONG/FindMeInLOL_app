package com.example.findmeinlol.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle

import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findmeinlol.R
import com.example.findmeinlol.RiotAPIRepository
import com.example.findmeinlol.databinding.ActivitySearchBinding

import com.example.findmeinlol.model.data.Summoner
import com.example.findmeinlol.viewmodel.SearchViewModel
import com.example.findmeinlol.viewmodel.adapter.SearchViewRecyclerAdapter
import com.example.findmeinlol.viewmodel.summonerListLiveData
import com.google.gson.Gson


interface CallBack{
    fun onFinished()
    fun onItemClicked(position: Int)
}

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchViewRecyclerAdapter: SearchViewRecyclerAdapter

    private val callBackListener = object: CallBack{
        override fun onFinished() {
            val summoner: Summoner = searchViewModel.getSummoner()

            summoner.participantList.sort()


            summoner.summonerDto?.let {
                if (searchViewModel.getSearchModel().isName(it.name)) return
            }

            searchViewModel.addSummoner()
            val intent = Intent(applicationContext, SearchResultActivity::class.java)
            intent.putExtra("Summoner", Gson().toJson(summoner))
            startActivity(intent)
        }

        override fun onItemClicked(position: Int) {
            val intent = Intent(applicationContext, SearchResultActivity::class.java)
            intent.putExtra("Summoner", Gson().toJson(searchViewModel.getSummoner(position)))
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        searchViewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(SearchViewModel::class.java)

        binding.lifecycleOwner = this
        binding.searchViewModel = searchViewModel

        setSupportActionBar(binding.searchToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        setObserve()
        setSearchViewRecyclerView()
        setListener()
    }

    override fun onResume() {
        summonerListLiveData.value = searchViewModel.getSearchModel().getSummonerList()
        super.onResume()
    }

    private var count: Int = 0
    private fun setObserve() {
        summonerListLiveData.observe(this) {
            searchViewRecyclerAdapter.notifyDataSetChanged()
        }

        RiotAPIRepository.summonerLiveData.observe(this) {
            if (it.summonerDto != null) {
                searchViewModel.getSearchModel().setSummoner(it)
            }
            else {
                val alertDialog = AlertDialog.Builder(this)
                    .setPositiveButton("확인") { dialog, _ -> dialog.dismiss() }
                alertDialog.setMessage("존재하지않는 소환사입니다.")
                alertDialog.show()
            }
        }

        RiotAPIRepository.participantLiveData.observe(this) {
            searchViewModel.addParticipant(it)
        }

        RiotAPIRepository.championIconLiveData.observe(this) {
            searchViewModel.getParticipantList()
                .asSequence()
                .filter { p -> p.matchId == it.matchId }
                .first().championIcon = it.bitmap
        }

        RiotAPIRepository.itemIconLiveData.observe(this) {
            count++

            searchViewModel.getParticipantList()
                .asSequence()
                .filter { p -> p.matchId == it.matchId }
                .first().itemIcons[it.number] = it.bitmap

            if (count == RiotAPIRepository.matchSizeLiveData.value!! * 7) {
                callBackListener.onFinished()
                count = 0
            }
        }

        RiotAPIRepository.spellIconLiveData.observe(this) {
            searchViewModel.getParticipantList()
                .asSequence()
                .filter { p -> p.matchId == it.matchId }
                .first().spellIcons[it.number] = it.bitmap
        }

        RiotAPIRepository.runeIconLiveData.observe(this) {
            searchViewModel.getParticipantList()
                .asSequence()
                .filter { p -> p.matchId == it.matchId }
                .first().runeIcons[it.number] = it.bitmap
        }
    }

    private fun setListener() {
        binding.searchSearchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { RiotAPIRepository.getSummoner(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.isEmpty()) {
                        binding.searchSearchView.maxWidth = 880
                        binding.searchButton1.visibility = View.INVISIBLE
                    }
                    else {
                        binding.searchSearchView.maxWidth = 750
                        binding.searchButton1.visibility = View.VISIBLE
                    }
                }
                return false
            }
        })

        binding.searchButton1.setOnClickListener {
            binding.searchSearchView.setQuery("", false)
            binding.searchSearchView.maxWidth = 880
            binding.searchButton1.visibility = View.VISIBLE
            binding.searchSearchView.clearFocus()
        }
    }


    private fun setSearchViewRecyclerView() {
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(this)
        searchViewRecyclerAdapter = SearchViewRecyclerAdapter(searchViewModel)
        searchViewRecyclerAdapter.setListener(callBackListener)
        binding.searchRecyclerView.adapter = searchViewRecyclerAdapter
        addDivideLine()
    }

    private fun addDivideLine() {
        binding.searchRecyclerView.addItemDecoration(DividerItemDecoration(
                applicationContext,
                LinearLayoutManager(this).orientation))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> { super.onOptionsItemSelected(item) }
        }
    }
}