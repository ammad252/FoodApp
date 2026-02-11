package com.example.signupandlogin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class emailverification extends AppCompatActivity {
    TextView fname, lname, email, passwd, address, city;
    FirebaseAuth mAuth;
    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapterdatashow myAdapter;
    ArrayList<savedata> list;

    private ListView listView;
    FirebaseUser user;
    Button button, verify;
//    TextView sendto, subject, body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emailverification);
        getSupportActionBar().setTitle("verification");

//        verify = findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mAuth = FirebaseAuth.getInstance();

//
//        TextView textView = findViewById(R.id.f_name);
//        TextView textView2 = findViewById(R.id.L_name);
//        TextView textView3 = findViewById(R.id.email);
//        TextView textView4 = findViewById(R.id.password);
//        TextView textView5 = findViewById(R.id.address);
//        TextView textView6 = findViewById(R.id.city);


        recyclerView = findViewById(R.id.userLis);
        database = FirebaseDatabase.getInstance().getReference("savedata");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new MyAdapterdatashow(this,list);
        recyclerView.setAdapter(myAdapter);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    savedata user = dataSnapshot.getValue(savedata.class);
                    list.add(user);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



            }


    }
