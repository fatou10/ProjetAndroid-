package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapp.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding; // Assurez-vous que ce soit ActivityMainBinding


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Vérifier si l'utilisateur est connecté


        // Gestion des clics sur les autres boutons pour naviguer vers les différentes activités
        binding.buttonRendezvous.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RendezVousActivity.class); // Remplacez par l'activité de gestion des rendez-vous
            startActivity(intent);
        });

        binding.buttonPromotions.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, PromotionsActivity.class); // Remplacez par l'activité de gestion des promotions
            startActivity(intent);
        });

        binding.buttonClients.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ClientsActivity.class); // Remplacez par l'activité de gestion des clients
            startActivity(intent);
        });

        binding.buttonService.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ServiceActivity.class); // Remplacez par l'activité de gestion des clients
            startActivity(intent);
        });

        binding.buttonNotifications.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NotificationsActivity.class); // Remplacez par l'activité de gestion des notifications
            startActivity(intent);
        });
    }
}
