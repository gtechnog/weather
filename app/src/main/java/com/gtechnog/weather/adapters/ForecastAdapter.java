package com.gtechnog.weather.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gtechnog.apixu.models.forecast.ForecastDay;
import com.gtechnog.weather.R;
import com.gtechnog.weather.utils.TextUtils;

import java.util.ArrayList;

/**
 * ForecastAdapter is for showing the list of forecast by providing the
 * list of forecast{@link #ForecastAdapter(ArrayList)}
 */
public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastItemViewHolder> {

    private final ArrayList<ForecastDay> forecastDay;

    public ForecastAdapter(ArrayList<ForecastDay> forecastDay) {
        this.forecastDay = forecastDay;
    }

    @NonNull
    @Override
    public ForecastAdapter.ForecastItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast, parent, false);
        return new ForecastItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastAdapter.ForecastItemViewHolder holder, int position) {
        // TODO: make sure  displayDayString, displayForecastTempString returns empty strings in negative cases
        holder.day.setText(TextUtils.displayDayString(forecastDay.get(position).getDateEpoch()));
        holder.temp.setText(TextUtils.displayForecastTempString(forecastDay.get(position).getDay().getAverageTempInCelcius()));
    }


    @Override
    public int getItemCount() {
        return forecastDay == null ? 0 :forecastDay.size();
    }

    static class ForecastItemViewHolder extends RecyclerView.ViewHolder {

        TextView day;
        TextView temp;
        ForecastItemViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.day);
            temp = itemView.findViewById(R.id.tempreature);
        }
    }
}
