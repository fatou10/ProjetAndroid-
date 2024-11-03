package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapp.databinding.ActivitySignupBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {
    private ActivitySignupBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
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
            String email = binding.editEmail.getText().toString().trim();
            String password = binding.editPassword.getText().toString().trim();
            String prenom = binding.editPrenom.getText().toString().trim();
            String nom = binding.editNom.getText().toString().trim();


            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(Signup.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else {
            // crée le compte de l'utilisateur avec ses options
            }
        });

        // Gestion du clic sur "Déjà un compte? Connectez-vous ici"
        binding.move.setOnClickListener(view -> {
            Intent intent = new Intent(Signup.this, Login.class);
            startActivity(intent);
        });
    }


}
