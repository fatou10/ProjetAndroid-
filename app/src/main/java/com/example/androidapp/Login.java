package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapp.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialisation du View Binding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialisation de Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Gestion du clic sur le bouton "Se connecter"
        binding.connexion.setOnClickListener(view -> {
            String email = binding.email.getText().toString().trim();
            String password = binding.password.getText().toString().trim();

            // Vérifier que les champs ne sont pas vides
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(Login.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else {
                signIn(email, password);
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
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Rediriger vers MainActivity si l'utilisateur est connecté
            updateUI(currentUser);
        }
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Connexion réussie
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // En cas d'échec, afficher un message à l'utilisateur
                        Toast.makeText(Login.this, "Authentification échouée : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // Rediriger vers MainActivity si l'utilisateur est connecté
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish(); // Empêche de revenir à l'activité de connexion en appuyant sur "Retour"
        }
    }
}
