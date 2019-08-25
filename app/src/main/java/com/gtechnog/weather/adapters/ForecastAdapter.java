package com.gtechnog.weather.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gtechnog.weather.R;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastItemViewHolder> {

    @NonNull
    @Override
    public ForecastAdapter.ForecastItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast, parent, false);
        return new ForecastItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastAdapter.ForecastItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    static class ForecastItemViewHolder extends RecyclerView.ViewHolder {

        TextView day;
        TextView tempreature;
        ForecastItemViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.day);
            tempreature = itemView.findViewById(R.id.tempreature);
        }
    }
}
