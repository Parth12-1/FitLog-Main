package com.example.fitlog.ui.foodhistory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.fitlog.AdapterFoodHistory;
import com.example.fitlog.R;
import com.example.fitlog.databinding.FragmentFoodhistoryBinding;
import com.example.fitlog.jFood;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;


public class FoodhistoryFragment extends Fragment{ // done

    private FragmentFoodhistoryBinding binding;

    private FirebaseAuth mAuth;
    private String UID;
    private DatabaseReference mDatabase;
    FirebaseUser user;
    private boolean changed = false;
    //DatabaseReference ref;
    ArrayList<jFood> list;
    RecyclerView recyclerView;
    ImageButton back;
    ImageButton next;
    TextView dia;
    FloatingActionButton Fab;
    //LocalDate startDate;
    int datechanged= 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FoodhistoryViewModel foodhistoryViewModel =
                new ViewModelProvider(this).get(FoodhistoryViewModel.class);

        binding = FragmentFoodhistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        back = binding.leftbtn;
        next = binding.rightbtn;
        dia = binding.datetxt;
        Fab = binding.floatingActionButton;
        dia.setText("LOLOLOLOL");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        UID = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("foodLog").child(UID);
        dia.setText(LocalDate.now().minusDays(datechanged).toString());


        recyclerView = root.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("CLICK LEFT");
                datechanged+=1;
                //System.out.println(LocalDate.now().minusDays(datechanged).toString() + "BACKERRr");
                dia.setText(LocalDate.now().minusDays(datechanged).toString());
                onStart();
                /*if (list.size()!=0){
                    list.clear();
                }*/

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("CLICK RIGHT");
                datechanged-=1;
                dia.setText(LocalDate.now().minusDays(datechanged).toString());
                onStart();
                /*if (list.size()!=0){
                    list.clear();
                }*/
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mDatabase.child(LocalDate.now().minusDays(datechanged).toString()).get() != null) {
            System.out.println("innnnitttttttt to win " + LocalDate.now().minusDays(datechanged).toString());
            mDatabase.child(LocalDate.now().minusDays(datechanged).toString()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        System.out.println("INNITTTT 2");
                        list = new ArrayList<>();
                        //list.clear();
                        changed = false;
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            list.add(ds.getValue(jFood.class));
                            changed = true;
                            System.out.println(ds.getValue(jFood.class).Name);
                            System.out.println("INNNITTTTTT 3");
                        }
                        if (!changed){
                            list.clear();
                            System.out.println("CLEAERED");
                        }

                    }
                    else{
                        list.clear();
                        System.out.println("CLEAERED");
                    }
                    AdapterFoodHistory adapterFoodHistory = new AdapterFoodHistory(list);
                    recyclerView.setAdapter(adapterFoodHistory);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println(error.getMessage() + "CRAZYYYYY");
                }
            });
        }

    }

}