package com.example.androidapp;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class Item_reservation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_reservation);

        // Configuration du RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rv_latest_activity_item_reservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Créer une liste de réservations
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation("12 Octobre 2024", "14:00", "Coupe de Cheveux"));
        reservations.add(new Reservation("13 Octobre 2024", "15:30", "Coloration"));
        reservations.add(new Reservation("14 Octobre 2024", "11:00", "Soin du Visage"));
        // Ajoutez plus de données de réservation si nécessaire

        // Créer l'adapter avec la liste de réservations
        ReservationAdapter adapter = new ReservationAdapter(reservations);
        recyclerView.setAdapter(adapter);
    }
}
