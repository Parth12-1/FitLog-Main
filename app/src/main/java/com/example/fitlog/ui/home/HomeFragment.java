package com.example.fitlog.ui.home;
//import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitlog.AdapterAddactivity;
import com.example.fitlog.R;
import com.example.fitlog.ScheduleUser;
import com.example.fitlog.SomeActivity;
import com.example.fitlog.databinding.FragmentHomeBinding;
import com.example.fitlog.newExercise;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class HomeFragment extends Fragment implements AdapterAddactivity.OnNoteListener{ // Done

    private FragmentHomeBinding binding;

    private FirebaseAuth mAuth;
    private String UID;
    private DatabaseReference mDatabase;
    FirebaseUser user;
    private static final String TAG = "HomeFragment";
    Date d1 = new Date();
    Date d2 = new Date();
    String SDate;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel addhomeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        user = mAuth.getCurrentUser();
        UID = user.getUid();


        ref = FirebaseDatabase.getInstance().getReference().child("exerciseList").child(UID);
        //recyclerView = binding.rv;
        recyclerView = root.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));


        mDatabase.child("users")
                .child(UID)
                .child("scheduleUser").get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        SDate = task.getResult().child("startDate").getValue().toString();
    }
});

        return root;
    }

    DatabaseReference ref;
    ArrayList<newExercise> list;
    RecyclerView recyclerView;

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
                        AdapterAddactivity adapterAddactivity = new AdapterAddactivity(list, com.example.fitlog.ui.home.HomeFragment.this);
                        recyclerView.setAdapter(adapterAddactivity);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println(error.getMessage() + "CRAZYYYYY");
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void OnNoteClick(int position) {
        //Log.d(TAG, "OnNoteClick: clicked" + position);
        System.out.println(position);
        //System.out.println(adapterAddactivity.getList().get(position).getExercisename());
        newExercise send;
        System.out.println(list.get(position).getExercisename());
        send = list.get(position);
        Intent intent = new Intent(getActivity(), SomeActivity.class);
        intent.putExtra("name", send.getExercisename());
        startActivity(intent);
    }
}
