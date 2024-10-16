package com.example.androidapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {

    private List<Reservation> reservationList;

    public ReservationAdapter(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_reservation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reservation reservation = reservationList.get(position);
        holder.tvReservationDate.setText("Date : " + reservation.getDate());
        holder.tvReservationTime.setText("Heure : " + reservation.getTime());
        holder.tvReservationService.setText("Service : " + reservation.getService());
    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvReservationDate, tvReservationTime, tvReservationService;

        public ViewHolder(View itemView) {
            super(itemView);
            tvReservationDate = itemView.findViewById(R.id.tv_reservation_date);
            tvReservationTime = itemView.findViewById(R.id.tv_reservation_time);
            tvReservationService = itemView.findViewById(R.id.tv_reservation_service);
        }
    }
}
