package com.example.androidapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientsActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<String> clientsList; // Liste des clients
    private ArrayAdapter<String> adapter; // Adaptateur pour la ListView
    private EditText editTextClientName; // Champ pour le nom du client
    private EditText editTextClientEmail; // Champ pour l'adresse e-mail du client
    private ListView listViewClients; // Liste des clients

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients); // Charge le layout

        // Initialisation des vues
        editTextClientName = findViewById(R.id.editText_client_name);
        editTextClientEmail = findViewById(R.id.editText_client_email); // Initialisation du champ e-mail
        listViewClients = findViewById(R.id.listView_clients);

        // Initialisation de la liste et de l'adaptateur
        clientsList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, clientsList);
        listViewClients.setAdapter(adapter);

        // Événement pour ajouter un client
        findViewById(R.id.button_add_client).setOnClickListener(view -> {
            String clientName = editTextClientName.getText().toString().trim();
            String clientEmail = editTextClientEmail.getText().toString().trim(); // Récupération de l'e-mail

            if (clientName.isEmpty() || clientEmail.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else {
                addClient(clientName, clientEmail); // Ajout de l'e-mail lors de l'ajout du client
            }
        });

        loadClients(); // Charge les clients au démarrage
    }

    private void addClient(String clientName, String clientEmail) {
        Map<String, Object> client = new HashMap<>();
        client.put("name", clientName);
        client.put("email", clientEmail); // Ajout de l'e-mail au document Firestore

        // Ajouter le client à Firestore
        db.collection("clients")
                .add(client)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Client ajouté", Toast.LENGTH_SHORT).show();
                        editTextClientName.setText("");
                        editTextClientEmail.setText(""); // Réinitialisation de l'e-mail
                        loadClients(); // Recharge les clients après l'ajout
                    } else {
                        Toast.makeText(this, "Échec de l'ajout : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadClients() {
        db.collection("clients").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                clientsList.clear(); // Vider la liste avant de la recharger
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String name = document.getString("name");
                    clientsList.add(name); // Ajouter chaque client à la liste
                }
                adapter.notifyDataSetChanged(); // Notifier l'adaptateur que les données ont changé
            } else {
                Toast.makeText(this, "Échec du chargement : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
