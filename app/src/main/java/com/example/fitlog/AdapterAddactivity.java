package com.example.fitlog;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterAddactivity extends RecyclerView.Adapter<AdapterAddactivity.MyViewHolder> {

    ArrayList<newExercise> list;
    private OnNoteListener mOnNoteListener;

    public AdapterAddactivity(ArrayList<newExercise> list, OnNoteListener onNoteListener){
        this.list = list;
        this.mOnNoteListener = onNoteListener;
    }
    public AdapterAddactivity(ArrayList<newExercise> list){
        this.list = list;
    }

    public ArrayList<newExercise> getList() {
        return list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder,parent,false);
        return new MyViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.id.setText(list.get(position).getExercisename());
        holder.desc.setText(list.get(position).getExercise());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView id, desc;
        OnNoteListener onNoteListener;

        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            id = itemView.findViewById(R.id.dealId);
            desc = itemView.findViewById(R.id.description);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.OnNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void OnNoteClick(int position);
    }
}