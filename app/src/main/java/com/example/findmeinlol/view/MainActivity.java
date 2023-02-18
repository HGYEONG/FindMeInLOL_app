package com.example.findmeinlol.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;

import com.example.findmeinlol.R;
import com.example.findmeinlol.databinding.ActivityMainBinding;
import com.example.findmeinlol.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new ViewModelProvider(this,
                (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory
                        .getInstance(getApplication())).get(MainViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setMainViewModel(mainViewModel);
        binding.mainSearchView.clearFocus();
        binding.mainSearchView.setInputType(InputType.TYPE_NULL);

        setEventListener();
    }

    private void setEventListener() {
        binding.mainSearchView.setOnClickListener(v -> {
            binding.mainSearchView.clearFocus();
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        binding.mainSearchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.mainSearchView.clearFocus();
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        binding.mainTextView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LckMatchActivity.class);
            startActivity(intent);
        });
    }
}