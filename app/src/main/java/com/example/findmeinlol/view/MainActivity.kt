package com.example.findmeinlol.view

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.findmeinlol.R
import com.example.findmeinlol.databinding.ActivityMainBinding
import com.example.findmeinlol.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )
            .get(MainViewModel::class.java)

        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.mainSearchView.clearFocus()
        binding.mainSearchView.inputType = InputType.TYPE_NULL

        setEventListener()
    }

    private fun setEventListener() {
        binding.mainSearchView.setOnClickListener {
            binding.mainSearchView.clearFocus()
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        binding.mainSearchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.mainSearchView.clearFocus()
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
            }
        }

        binding.mainTextView.setOnClickListener{
            val intent = Intent(this, LckMainActivity::class.java)
            startActivity(intent)
        }
    }
}