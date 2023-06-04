package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableInt;

import android.os.Bundle;

import com.example.contador.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ObservableInt count = new ObservableInt(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //setContentView(R.layout.activity_main);

        binding.setCount(count);

        binding.addButton.setOnClickListener(v -> {
            count.set(count.get() + 1);
        });

        binding.subtractButton.setOnClickListener(v -> {
            count.set(count.get() - 1);
        });
    }
}