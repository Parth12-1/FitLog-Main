package com.example.fitlog;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterExerciseHistory extends RecyclerView.Adapter<AdapterExerciseHistory.MyViewHolder> {

    ArrayList<jExercise> list;

    public AdapterExerciseHistory(ArrayList<jExercise> list){
        this.list = list;
    }

    public ArrayList<jExercise> getList() {
        return list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder2,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.id.setText(list.get(position).Name);
        holder.desc.setText(list.get(position).Weight);
        holder.sets.setText(list.get(position).Sets);
        holder.reps.setText(list.get(position).Reps);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView id, desc,sets, reps;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.dealId);
            desc = itemView.findViewById(R.id.description);
            sets = itemView.findViewById(R.id.sets);
            reps = itemView.findViewById(R.id.reps);
        }

    }

}
