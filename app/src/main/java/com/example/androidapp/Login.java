package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapp.databinding.ActivityLoginBinding;



public class Login extends AppCompatActivity {
    private ActivityLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialisation du View Binding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Gestion du clic sur le bouton "Se connecter"
        binding.connexion.setOnClickListener(view -> {
            String email = binding.email.getText().toString().trim();
            String password = binding.password.getText().toString().trim();

            // Vérifier que les champs ne sont pas vides
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(Login.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else {
              // je me connecte
            }
        });

        // Gestion du clic sur le bouton "S'inscrire"
        binding.move.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Signup.class);
            startActivity(intent);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
            // Vérifier si l'utilisateur est déjà connecté

            // Rediriger vers MainActivity si l'utilisateur est connecté
            // je recupere l'id de l'utilisateur et en fonction de son role je le redirige vers la page correspondante
        }



}
