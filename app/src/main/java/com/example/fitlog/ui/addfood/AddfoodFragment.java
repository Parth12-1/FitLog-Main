package com.example.fitlog.ui.addfood;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitlog.AdapterAddfood;

import com.example.fitlog.R;
import com.example.fitlog.SomeActivity;
import com.example.fitlog.SomeActivity2;
import com.example.fitlog.databinding.FragmentAddfoodBinding;
import com.example.fitlog.newExercise;
import com.example.fitlog.newFood;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;


public class AddfoodFragment extends Fragment implements AdapterAddfood.OnNoteListener{ // Done

    private FragmentAddfoodBinding binding;

    private FirebaseAuth mAuth;
    private String UID;
    private DatabaseReference mDatabase;
    FirebaseUser user;
    private boolean changed = false;
    private static final String TAG = "AddfoodFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddfoodViewModel addfoodViewModel =
                new ViewModelProvider(this).get(AddfoodViewModel.class);

        binding = FragmentAddfoodBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        user = mAuth.getCurrentUser();
        UID = user.getUid();



        final TextInputEditText cals = binding.calEditTxt;

        final TextInputEditText name = binding.nameEditTxt;

        final FloatingActionButton Fab = binding.Fab;
        Fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cals.getText().length()!=0 && name.getText().length()!=0){
                    newFood one = new newFood(cals.getText().toString(), name.getText().toString());
                    mDatabase.child("foodList").child(UID).child(name.getText().toString()).setValue(one);
                }
            }
        });


        ref = FirebaseDatabase.getInstance().getReference().child("foodList").child(UID);
        //recyclerView = binding.rv;
        searchView= binding.searchView;


        recyclerView = root.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        return root;
    }

    DatabaseReference ref;
    ArrayList<newFood> list;
    RecyclerView recyclerView;
    SearchView searchView;

    @Override
    public void onStart() {
        super.onStart();
        if (ref!=null){
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        list = new ArrayList<>();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            list.add(ds.getValue(newFood.class));
                            System.out.println(ds.getValue(newFood.class).getFoodname());

                        }
                        AdapterAddfood adapterAddactivity = new AdapterAddfood(list, com.example.fitlog.ui.addfood.AddfoodFragment.this);
                        recyclerView.setAdapter(adapterAddactivity);
                        changed = false;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println(error.getMessage() + "CRAZYYYYY");
                }
            });
        }

        if (searchView!=null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    myList.clear();
                    search(s);

                    return false;
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    AdapterAddfood adapterAddactivity;
    ArrayList<newFood> myList = new ArrayList<>();
    private void search(String str){
        for(newFood object : list){
            if (object.getFoodname().toLowerCase().contains(str.toLowerCase())){
                myList.add(object);
                System.out.println(object.getFoodname());
            }
        }
        adapterAddactivity = new AdapterAddfood(myList, this);
        recyclerView.setAdapter(adapterAddactivity);
        changed = true;
        //myList.clear();
    }


    @Override
    public void OnNoteClick(int position) {
        //Log.d(TAG, "OnNoteClick: clicked" + position);
        System.out.println(position);
        //System.out.println(adapterAddactivity.getList().get(position).getFoodname());
        newFood send;
        if (changed){
            System.out.println(myList.get(position).getFoodname());
            send = myList.get(position);
        }
        else{
            System.out.println(list.get(position).getFoodname());
            send = list.get(position);
        }

        Intent intent = new Intent(getActivity(), SomeActivity2.class);
        intent.putExtra("name", send.getFoodname());
        startActivity(intent);
    }
}