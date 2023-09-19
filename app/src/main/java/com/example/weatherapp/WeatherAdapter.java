package com.example.weatherapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    Context context;
    List<Hour> arrHour;

    public WeatherAdapter(Context context, List<Hour> arrHour) {
        this.context = context;
        this.arrHour = arrHour;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context)
                .load(Uri.parse("https:" + arrHour.get(position).getCondition().getIcon()))
                .into(holder.RVimageView);
        // holder.RVimageView.setImageResource(Integer.parseInt("https:" + arrHour.get(position).getCondition().getIcon()));
        holder.RVcondition.setText(arrHour.get(position).getCondition().getText());
        holder.RVdate.setText(arrHour.get(position).getTime().substring(0, 10));
        holder.RVtime.setText(arrHour.get(position).getTime().substring(11));
        holder.RVtempShow.setText(String.valueOf(arrHour.get(position).getTempC()) + " °C");
    }

    @Override
    public int getItemCount() {
        return arrHour.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView RVimageView;
        TextView RVcondition, RVdate, RVtime, RVtempShow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            RVimageView = itemView.findViewById(R.id.RVimageView);
            RVcondition = itemView.findViewById(R.id.RVcondition);
            RVdate = itemView.findViewById(R.id.RVdate);
            RVtime = itemView.findViewById(R.id.RVtime);
            RVtempShow = itemView.findViewById(R.id.RVtempShow);
        }
    }


}
