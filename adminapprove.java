package com.example.signupandlogin;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class adminapprove extends AppCompatActivity {
    TextView fname, lname, email, pincode, address, city,phoneno;
    Button button;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminapprove);
        getSupportActionBar().setTitle("Admin Register user");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        TextView textView = findViewById(R.id.f_name);
        TextView textView2 = findViewById(R.id.L_name);
        TextView textView3 = findViewById(R.id.email);
        TextView textView4 = findViewById(R.id.pincode);
        TextView textView5 = findViewById(R.id.address);
        TextView textView6 = findViewById(R.id.city);
       TextView textView7=findViewById(R.id.phoneno);
        button = findViewById(R.id.button);
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        String value  = sharedPreferences.getString("value", "");
        String value1 = sharedPreferences.getString("value1", "");
        String value2 = sharedPreferences.getString("value2", "");
        String value3 = sharedPreferences.getString("value3", "");
        String value4 = sharedPreferences.getString("value4", "");
        String value5 = sharedPreferences.getString("value5", "");
        String value6 = sharedPreferences.getString("value6","");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mAuth.createUserWithEmailAndPassword(value2, value3).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    //
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            ObjectAnimator animator = ObjectAnimator.ofInt(button,
                                    "backgroundColor", Color.BLUE, Color.GREEN, Color.YELLOW);
                            animator.setDuration(5000);
                            animator.setEvaluator(new ArgbEvaluator());
                            animator.setRepeatCount(Animation.INFINITE);
                            animator.setRepeatMode(ValueAnimator.REVERSE);
                            animator.start();
                            FirebaseDatabase.getInstance().getReference("Users");
                            Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
//                        .setEnabled(true);


                                    if (task.isSuccessful()) {

                                        Toast.makeText(getApplicationContext(), "verify", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getApplicationContext(), thankyou.class);
                                        startActivity(i);
                                        reference = FirebaseDatabase.getInstance().getReference("Users");


                                    } else {
                                        Toast.makeText(getApplicationContext(), "Failed verification", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            // Registration failed
                            Toast.makeText(getApplicationContext(), "Registration failed!!" + " Email already exist", Toast.LENGTH_LONG).show();

                            // hide the progress bar

                        }


                    }
                });

            }
        });
//        String name = textView.getText().toString();
//        String value = sharedPreferences.getString("value", "");
//        if (!value.isEmpty()) {
//
//            deleteData(value);
//
//        } else {
//
//            Toast.makeText(adminapprove.this, "Please fill", Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    private void deleteData(String value) {
//
//        reference = FirebaseDatabase.getInstance().getReference("savedata");
//        reference.child(value).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//
//                    Toast.makeText(adminapprove.this, "reject", Toast.LENGTH_SHORT).show();
//                    fname.setText("");
//
//
//                } else {
//                    Toast.makeText(adminapprove.this, "Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
    }
}