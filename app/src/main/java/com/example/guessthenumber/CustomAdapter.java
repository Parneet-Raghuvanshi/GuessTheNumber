package com.example.guessthenumber;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    String[] strings;
    Context context;
    public CustomAdapter(Context context, String[] strings) {
        this.context = context;
        this.strings = strings;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String maintext = strings[position];
        Log.d("Maintext","main ===   "+maintext);
        String[] arr = maintext.split("#");
        String name = arr[0];
        String time = arr[1];
        String attempts = arr[2];
        String res = arr[3];
        holder.vh_name.setText(name);
        holder.vh_date.setText(time);
        holder.vh_attempts.setText(attempts);
        if (res.equals("T"))
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.green));
        else if (res.equals("F"))
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.red));
    }

    @Override
    public int getItemCount() {
        return strings.length;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView vh_name, vh_date, vh_attempts;
        public CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            vh_name = itemView.findViewById(R.id.tv_name);
            vh_date = itemView.findViewById(R.id.tv_time);
            vh_attempts = itemView.findViewById(R.id.tv_attempts);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }
}
