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
                addRendezVous(service, date, clientEmail); // Ajouter l'e-mail lors de l'ajout du rendez-vous
            }
        });

        // Charger les rendez-vous au démarrage
        loadRendezVous();

        // Événement pour le bouton Retour
        findViewById(R.id.button_back).setOnClickListener(view -> {
            Intent intent = new Intent(RendezVousActivity.this, MainActivity.class); // Remplacez MainActivity par le nom de votre activité principale
            startActivity(intent);
            finish(); // Termine l'activité actuelle
        });


    }

    private void addRendezVous(String service, String date, String clientEmail) {
        Map<String, Object> rendezvous = new HashMap<>();
        rendezvous.put("service", service);
        rendezvous.put("date", date);
        rendezvous.put("clientEmail", clientEmail); // Ajouter l'e-mail du client au rendez-vous

        // Ajouter le rendez-vous à Firestore
        db.collection("rendezvous")
                .add(rendezvous)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Rendez-vous ajouté", Toast.LENGTH_SHORT).show();
                        editTextService.setText("");
                        editTextDate.setText("");
                        editTextClientEmail.setText(""); // Réinitialiser l'e-mail
                        loadRendezVous(); // Recharge les rendez-vous après l'ajout
                        checkForPromotion(clientEmail);
                    } else {
                        Toast.makeText(this, "Échec de l'ajout : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadRendezVous() {
        db.collection("rendezvous").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                rendezvousList.clear(); // Vider la liste avant de la recharger
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String service = document.getString("service");
                    String date = document.getString("date");
                    String email = document.getString("clientEmail"); // Récupérer l'e-mail du client
                    rendezvousList.add(service + " - " + date + " - " + email); // Ajouter chaque rendez-vous à la liste
                }
                adapter.notifyDataSetChanged(); // Notifier l'adaptateur que les données ont changé
            } else {
                Toast.makeText(this, "Échec du chargement : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void addPromotion(String email) {
        Map<String, Object> promotion = new HashMap<>();
        promotion.put("email", email);
        promotion.put("date", System.currentTimeMillis()); // Date de la promotion
        promotion.put("typePromotion", "10% de réduction"); // Exemple de type de promotion

        // Ajouter la promotion dans Firestore
        db.collection("promotion")
                .add(promotion)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        MailSender mailSender = new MailSender();
                        String subject = "Félicitations ! Vous avez une promotion !";
                        String body = "Merci d'avoir réservé avec nous. Profitez de votre réduction pour votre prochaine réservation !";
                        mailSender.sendEmail(email, subject, body);
                        Toast.makeText(this, "Promotion ajoutée pour " + email, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Échec de l'ajout de la promotion : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updatePromo (int idpromo){

    }
    private void checkForPromotion(String email) {
        // Récupérer toutes les réservations de l'utilisateur
        db.collection("rendezvous")
                .whereEqualTo("clientEmail", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        int reservationCount = task.getResult().size(); // Compter le nombre de réservations

                        // Si l'utilisateur a atteint 3 réservations
                        if (reservationCount % 3 == 0) {
                            addPromotion(email); // Attribuer une promotion
                        }
                    } else {
                        Toast.makeText(this, "Erreur lors de la vérification des réservations : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void removepromo(int id_promo){

    }
}
