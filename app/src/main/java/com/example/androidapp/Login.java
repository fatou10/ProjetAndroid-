package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapp.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import androidx.annotation.NonNull;


public class Login extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialisation du View Binding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialisation de Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Initialisation de firestore
        db = FirebaseFirestore.getInstance();

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
            String user_id = currentUser.getUid();
            getUserProfileByUserId(user_id);
        }
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Connexion réussie
                        FirebaseUser user = mAuth.getCurrentUser();
                        String user_id = user.getUid();
                        getUserProfileByUserId(user_id);

                    } else {
                        // En cas d'échec, afficher un message à l'utilisateur
                        Toast.makeText(Login.this, "Authentification échouée : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }



    private void getUserProfileByUserId(String userId) {
        // Requête pour trouver le document où profilID correspond à userId
        db.collection("Profil")
                .whereEqualTo("profilID", userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // Récupère le premier document correspondant
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                String name = document.getString("nom");
                                String prenom = document.getString("prenom");
                                String role = document.getString("role");

                                // Affichez ou utilisez les données
                                Log.d("Profile", "Nom: " + name + ", Prenom: " + prenom + ", Role: " + role);

                                // Redirection en fonction du rôle
                                Intent intent;
                                if ("admin".equals(role)) {
                                    intent = new Intent(getApplicationContext(), MainActivity.class);
                                } else if ("client".equals(role)) {
                                    intent = new Intent(getApplicationContext(), Home_client.class);
                                } else {
                                    Log.w("Profile", "Rôle non reconnu : " + role);
                                    return; // Sortie si le rôle n'est pas reconnu
                                }

                                // Lancer l'activité correspondante
                                startActivity(intent);
                                finish(); // Fermer cette activité pour éviter de revenir en arrière
                                return; // Sortir de la boucle dès que le rôle est trouvé et redirigé
                            }
                        } else {
                            Log.d("Profile", "Aucun document de profil trouvé pour cet utilisateur.");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Profile", "Erreur lors de la récupération du profil", e);
                    }
                });
    }

}
