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
    private EditText edit_password;
    private EditText edit_email;
    private EditText edit_nom;
    private EditText edit_prenom;

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
                createAccount(email, password,nom,prenom);
            }
        });

        // Gestion du clic sur "Déjà un compte? Connectez-vous ici"
        binding.move.setOnClickListener(view -> {
            Intent intent = new Intent(Signup.this, Login.class);
            startActivity(intent);
        });
    }

    private void createAccount(String email, String password,String nom, String prenom) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Inscription réussie
                        String profilID=mAuth.getUid();

                        this.CreateProfil(prenom,nom,profilID,email);

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


    private void CreateProfil(String prenom, String nom,String profilID,String email) {
        Map<String, Object> Profil = new HashMap<>();
        Profil.put("prenom", prenom);
        Profil.put("nom", nom);
        Profil.put("role", "client"); // Ajouter l'e-mail du client au rendez-vous
        Profil.put("email", email);
        Profil.put("profilID", profilID);

        // Ajouter le Profil à Firestore
        db.collection("Profil")
                .add(Profil)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "utilisatreur creé avec succé", Toast.LENGTH_LONG).show();
                        edit_prenom.setText("");
                        edit_nom.setText("");
                        edit_password.setText("");
                        edit_email.setText(""); // Réinitialiser l'e-mail

                    } else {
                        Toast.makeText(this, "Échec de l'ajout : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
