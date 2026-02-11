package com.example.signupandlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Orderactivity extends AppCompatActivity {

    private DatabaseReference ordersRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private EditText editTextDish, editTextFoodName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_order);
        getSupportActionBar().setTitle("Special Order");
        // Initialize Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ordersRef = database.getReference("special_orders");

        // Initialize FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        editTextDish = findViewById(R.id.dishes);
        editTextFoodName = findViewById(R.id.f_name);
        Button submitButton = findViewById(R.id.submit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dish = editTextDish.getText().toString().trim();
                String foodName = editTextFoodName.getText().toString().trim();

                if (!dish.isEmpty() && !foodName.isEmpty()) {

                    String userId = firebaseUser.getUid();
                    DatabaseReference userOrdersRef = ordersRef.child(userId);
                    String orderId = userOrdersRef.push().getKey();

                    Order newOrder = new Order(dish, foodName);
                    userOrdersRef.child(orderId).setValue(newOrder);

                   Intent i =new Intent(getApplicationContext(),UserSpecificOrdersActivity.class);
                    startActivity(i);

                } else {
                    Toast.makeText(Orderactivity.this, "Fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
