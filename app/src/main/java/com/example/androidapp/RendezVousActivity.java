package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RendezVousActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<String> rendezvousList; // Liste des rendez-vous
    private ArrayAdapter<String> adapter; // Adaptateur pour la ListView
    private EditText editTextService; // Champ pour le service
    private EditText editTextDate; // Champ pour la date
    private EditText editTextClientEmail; // Champ pour l'adresse e-mail du client
    private ListView listViewRendezvous; // Liste des rendez-vous

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rendez_vous); // Charge le layout

        // Initialisation des vues
        editTextService = findViewById(R.id.editText_service);
        editTextDate = findViewById(R.id.editText_date);
        editTextClientEmail = findViewById(R.id.editText_client_email); // Ajoutez un champ pour l'e-mail
        listViewRendezvous = findViewById(R.id.listView_rendezvous);

        // Initialisation de la liste et de l'adaptateur
        rendezvousList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rendezvousList);
        listViewRendezvous.setAdapter(adapter);

        // Événement pour ajouter un rendez-vous
        findViewById(R.id.button_add_rendezvous).setOnClickListener(view -> {
            String service = editTextService.getText().toString().trim();
            String date = editTextDate.getText().toString().trim();
            String clientEmail = editTextClientEmail.getText().toString().trim(); // Récupérer l'e-mail du client

            if (service.isEmpty() || date.isEmpty() || clientEmail.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else {
                // J'ajoute les rendez-vous dansla base
                //Je verifie aussi apres l'ajout s'il doit recevoir ou pas des promos

            }
        });

        // Charger les rendez-vous au démarrage


        // Événement pour le bouton Retour
        findViewById(R.id.button_back).setOnClickListener(view -> {
            Intent intent = new Intent(RendezVousActivity.this, MainActivity.class); // Remplacez MainActivity par le nom de votre activité principale
            startActivity(intent);
            finish(); // Termine l'activité actuelle
        });


    }



}
