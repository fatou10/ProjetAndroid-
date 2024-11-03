package com.example.androidapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapp.databinding.ActivityHomeClientBinding;

public class Home_client extends AppCompatActivity {

    private ActivityHomeClientBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialisation du View Binding pour activity_home_client.xml
        binding = ActivityHomeClientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}
