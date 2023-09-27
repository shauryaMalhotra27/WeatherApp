package com.example.weatherappsam;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    Context context;
    List<Hour> arrHour;
    ToggleButton toggleButton;

    public WeatherAdapter(Context context, List<Hour> arrHour, ToggleButton toggleButton) {
        this.context = context;
        this.arrHour = arrHour;
        this.toggleButton = toggleButton;
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

        holder.RVcondition.setText(arrHour.get(position).getCondition().getText());
        holder.RVdate.setText(arrHour.get(position).getTime().substring(0, 10));
        // holder.RVtime.setText(arrHour.get(position).getTime().substring(11));

//        toggleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (toggleButton.isChecked()){
//                    holder.RVtempShow.setText(String.valueOf(arrHour.get(position).getTempC()) + " °C");
//                }
//                else {
//                    holder.RVtempShow.setText(String.valueOf(arrHour.get(position).getTempF()) + " °F");
//                }
//            }
//        });

        SimpleDateFormat input = new SimpleDateFormat("yyyy-mm-dd hh:mm");
        SimpleDateFormat output = new SimpleDateFormat("hh:mm aa");

        try {
            Date date = input.parse(arrHour.get(position).getTime());
            holder.RVtime.setText(output.format(date));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


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
