package com.example.fitlog.ui.addexercise;

//import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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

import com.example.fitlog.AdapterAddactivity;
import com.example.fitlog.R;
import com.example.fitlog.SetUpActivity;
import com.example.fitlog.SomeActivity;
import com.example.fitlog.databinding.FragmentAddexerciseBinding;
import com.example.fitlog.newExercise;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;


public class AddexerciseFragment extends Fragment implements AdapterAddactivity.OnNoteListener{ // Done

    private FragmentAddexerciseBinding binding;

    private boolean currentlyclicked = false;

    private String currentclicked;

    private FirebaseAuth mAuth;
    private String UID;
    private DatabaseReference mDatabase;
    FirebaseUser user;
    private boolean changed = false;
    private static final String TAG = "AddexerciseFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddexerciseViewModel addexerciseViewModel =
                new ViewModelProvider(this).get(AddexerciseViewModel.class);

        binding = FragmentAddexerciseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        user = mAuth.getCurrentUser();
        UID = user.getUid();


        final Button push = binding.pushbtn;
        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentclicked = "push";
                currentlyclicked = true;
            }
        });
        final Button pull = binding.pullbtn;
        pull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentclicked = "pull";
                currentlyclicked = true;
            }
        });
        final Button leg = binding.legbtn;
        leg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentclicked = "leg";
                currentlyclicked = true;

            }
        });

        final TextInputEditText name = binding.nameEditTxt;

        final FloatingActionButton Fab = binding.Fab;
        Fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentlyclicked && name.getText().length()!=0){
                    System.out.println(name.getText().toString() + "nameeeee");
                    System.out.println(currentclicked + "currentclicked");
                    newExercise one = new newExercise(currentclicked, name.getText().toString());
                    mDatabase.child("exerciseList").child(UID).child(name.getText().toString()).setValue(one);
                }
            }
        });


        ref = FirebaseDatabase.getInstance().getReference().child("exerciseList").child(UID);
        //recyclerView = binding.rv;
        searchView= binding.searchView;


        recyclerView = root.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        return root;
    }

    DatabaseReference ref;
    ArrayList<newExercise> list;
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
                            list.add(ds.getValue(newExercise.class));
                            System.out.println(ds.getValue(newExercise.class).getExercisename());

                        }
                        AdapterAddactivity adapterAddactivity = new AdapterAddactivity(list, AddexerciseFragment.this);
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

    AdapterAddactivity adapterAddactivity;
    ArrayList<newExercise> myList = new ArrayList<>();
    private void search(String str){
        for(newExercise object : list){
            if (object.getExercisename().toLowerCase().contains(str.toLowerCase())){
                myList.add(object);
                System.out.println(object.getExercisename());
            }
        }
        adapterAddactivity = new AdapterAddactivity(myList, this);
        recyclerView.setAdapter(adapterAddactivity);
        changed = true;
        //myList.clear();
    }


    @Override
    public void OnNoteClick(int position) {
        //Log.d(TAG, "OnNoteClick: clicked" + position);
        System.out.println(position);
        //System.out.println(adapterAddactivity.getList().get(position).getExercisename());
        newExercise send;
        if (changed){
            System.out.println(myList.get(position).getExercisename());
            send = myList.get(position);
        }
        else{
            System.out.println(list.get(position).getExercisename());
            send = list.get(position);
        }
        Intent intent = new Intent(getActivity(), SomeActivity.class);
        intent.putExtra("name", send.getExercisename());
        startActivity(intent);
    }
}
