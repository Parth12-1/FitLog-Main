package com.example.fitlog.ui.exercisehistory;

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

import com.example.fitlog.AdapterAddactivity;
import com.example.fitlog.AdapterExerciseHistory;
import com.example.fitlog.R;
import com.example.fitlog.databinding.FragmentExercisehistoryBinding;
import com.example.fitlog.jExercise;
import com.example.fitlog.newExercise;
import com.example.fitlog.ui.addexercise.AddexerciseFragment;
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


public class ExercisehistoryFragment extends Fragment{ // done

    private FragmentExercisehistoryBinding binding;

    private FirebaseAuth mAuth;
    private String UID;
    private DatabaseReference mDatabase;
    FirebaseUser user;
    private boolean changed = false;
    //DatabaseReference ref;
    ArrayList<jExercise> list;
    RecyclerView recyclerView;
    ImageButton back;
    ImageButton next;
    TextView dia;
    FloatingActionButton Fab;
    //LocalDate startDate;
    int datechanged= 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ExercisehistoryViewModel exercisehistoryViewModel =
                new ViewModelProvider(this).get(ExercisehistoryViewModel.class);

        binding = FragmentExercisehistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        back = binding.leftbtn;
        next = binding.rightbtn;
        dia = binding.datetxt;
        Fab = binding.floatingActionButton;
        dia.setText("LOLOLOLOL");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        UID = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("exerciseLog").child(UID);
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
                            list.add(ds.getValue(jExercise.class));
                            changed = true;
                            System.out.println(ds.getValue(jExercise.class).Name);
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
                    AdapterExerciseHistory adapterExerciseHistory = new AdapterExerciseHistory(list);
                    recyclerView.setAdapter(adapterExerciseHistory);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println(error.getMessage() + "CRAZYYYYY");
                }
            });
        }
        /*else if (changed){
            System.out.println("INNETRSFSVE");
            int size = list.size();
            list.clear();
            AdapterExerciseHistory adapterExerciseHistory = new AdapterExerciseHistory(list);
            recyclerView.setAdapter(adapterExerciseHistory);
            adapterExerciseHistory.notifyItemChanged(0, size);
            changed = false;
        }*/
    }

}