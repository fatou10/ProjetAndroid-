package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapp.databinding.ActivitySignupBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup extends AppCompatActivity {
    private ActivitySignupBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialiser Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Gestion du clic sur le bouton "S'inscrire"
        binding.connexion.setOnClickListener(view -> {
            String email = binding.email.getText().toString().trim();
            String password = binding.password.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(Signup.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else {
                createAccount(email, password);
            }
        });

        // Gestion du clic sur "Déjà un compte? Connectez-vous ici"
        binding.move.setOnClickListener(view -> {
            Intent intent = new Intent(Signup.this, Login.class);
            startActivity(intent);
        });
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Inscription réussie
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // En cas d'échec, afficher un message à l'utilisateur
                        Toast.makeText(Signup.this, "Échec de l'inscription : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // Rediriger vers MainActivity si l'inscription est réussie
            Intent intent = new Intent(Signup.this, MainActivity.class);
            startActivity(intent);
            finish(); // Empêche de revenir à Signup
        }
    }
}
