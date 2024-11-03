package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ServiceActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<String> ListServices;
    private ArrayAdapter<String> adapter;
    private EditText editTextService;
    private EditText editTextPrice;
    private ListView listViewService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service); // Charge le layout

        // Initialisation des vues
        editTextService = findViewById(R.id.editText_service);
        editTextPrice = findViewById(R.id.editText_price); // Ajoutez un champ pour l'e-mail
        listViewService = findViewById(R.id.listView_service);

        // Initialisation de la liste et de l'adaptateur
        ListServices = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,ListServices);
        listViewService.setAdapter(adapter);

        // Événement pour ajouter un service
        findViewById(R.id.button_add_service).setOnClickListener(view -> {
            String service = editTextService.getText().toString().trim();
            String price = editTextPrice.getText().toString().trim();

            if (service.isEmpty()  || price.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else {
                 // Ajouter l'e-mail lors de l'ajout du rendez-vous
            }
        });

        // Charger les rendez-vous au démarrage


        // Événement pour le bouton Retour
        findViewById(R.id.button_back).setOnClickListener(view -> {
            Intent intent = new Intent(ServiceActivity.this, MainActivity.class); // Remplacez MainActivity par le nom de votre activité principale
            startActivity(intent);
            finish(); // Termine l'activité actuelle
        });


    }

}
