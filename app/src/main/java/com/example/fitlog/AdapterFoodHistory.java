package com.example.fitlog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterFoodHistory extends RecyclerView.Adapter<AdapterFoodHistory.MyViewHolder> {

    ArrayList<jFood> list;

    public AdapterFoodHistory(ArrayList<jFood> list){
        this.list = list;
    }

    public ArrayList<jFood> getList() {
        return list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.id.setText(list.get(position).Name);
        holder.desc.setText(list.get(position).Totalcalories);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView id, desc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.dealId);
            desc = itemView.findViewById(R.id.description);
        }

    }

}

