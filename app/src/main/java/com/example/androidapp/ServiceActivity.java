package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
                addService(service, price); // Ajouter l'e-mail lors de l'ajout du rendez-vous
            }
        });

        // Charger les rendez-vous au démarrage
        loadRendezVous();

        // Événement pour le bouton Retour
        findViewById(R.id.button_back).setOnClickListener(view -> {
            Intent intent = new Intent(ServiceActivity.this, MainActivity.class); // Remplacez MainActivity par le nom de votre activité principale
            startActivity(intent);
            finish(); // Termine l'activité actuelle
        });


    }

    private void addService(String service, String prix) {
        Map<String, Object> set_service = new HashMap<>();
        set_service.put("service", service);
        set_service.put("price", prix);
        int idService = generateRandomId();
        set_service.put("id_service", idService);

        // Ajouter le rendez-vous à Firestore
        db.collection("service")
                .add(set_service)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "service ajouté avec succes", Toast.LENGTH_SHORT).show();
                        editTextService.setText("");
                        editTextPrice.setText(""); // Réinitialiser l'e-mail
                        loadRendezVous(); // Recharge les rendez-vous après l'ajout
                    } else {
                        Toast.makeText(this, "Échec de l'ajout : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadRendezVous() {
        db.collection("service").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ListServices.clear(); // Vider la liste avant de la recharger
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String service = document.getString("service");
                    String prix = document.getString("price");

                    ListServices.add(service + " - " + prix ); // Ajouter service
                }
                adapter.notifyDataSetChanged(); // Notifier l'adaptateur que les données ont changé
            } else {
                Toast.makeText(this, "Échec du chargement : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private int generateRandomId() {
        Random random = new Random();
        return random.nextInt(1000000); // Génère un nombre aléatoire entre 0 et 999999
    }
}
