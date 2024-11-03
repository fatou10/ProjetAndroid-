package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding; // Assurez-vous que ce soit ActivityMainBinding
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialiser Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Vérifier si l'utilisateur est connecté
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // Si l'utilisateur n'est pas connecté, rediriger vers Login
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish(); // Empêche de revenir à MainActivity
        }

        // Gestion du clic sur le bouton de déconnexion
        binding.buttonLogout.setOnClickListener(view -> {
            mAuth.signOut(); // Déconnecter l'utilisateur
            Intent intent = new Intent(MainActivity.this, Login.class); // Créer un Intent pour Login
            startActivity(intent); // Démarrer l'activité Login
            finish(); // Empêche de revenir à MainActivity
            Toast.makeText(MainActivity.this, "Déconnexion réussie", Toast.LENGTH_SHORT).show(); // Message de succès
        });

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
