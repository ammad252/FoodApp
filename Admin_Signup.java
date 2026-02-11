package com.example.signupandlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Admin_Signup extends AppCompatActivity {
    private TextInputEditText username,password,Cpassword;
    private Button register_btn;
    ProgressBar progressBar;
    //    private TextView Login;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://foodappdelivery-936ee-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup);

        getSupportActionBar().setTitle("AdminSignup");
        username=findViewById(R.id.username);
        password=findViewById(R.id.passwd);
        Cpassword=findViewById(R.id.cpassword);
        register_btn=findViewById(R.id.reg);
        progressBar=findViewById(R.id.progressbar);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register_btn.startAnimation(animation);

                String UserName = username.getText().toString();
                String Pass = password.getText().toString();
                String confirmpassword = Cpassword.getText().toString();

                if(UserName.isEmpty() && Pass.isEmpty() && confirmpassword.isEmpty()){
                    Toast.makeText(Admin_Signup.this, "Please add your credentials", Toast.LENGTH_SHORT).show();
                }
                else if(!UserName.isEmpty() && Pass.isEmpty() && confirmpassword.isEmpty()){
                    Toast.makeText(Admin_Signup.this, "Please add Password & Confirm it", Toast.LENGTH_SHORT).show();
                }
                else if(UserName.isEmpty() && !Pass.isEmpty() && !confirmpassword.isEmpty()){
                    Toast.makeText(Admin_Signup.this, "Please add UserName", Toast.LENGTH_SHORT).show();
                }
                else if(!UserName.isEmpty() && Pass.isEmpty() && !confirmpassword.isEmpty()){
                    Toast.makeText(Admin_Signup.this, "Please add Password", Toast.LENGTH_SHORT).show();
                }
                else if(!UserName.isEmpty() && !Pass.isEmpty() && confirmpassword.isEmpty()){
                    Toast.makeText(Admin_Signup.this, "Please add Confirm password", Toast.LENGTH_SHORT).show();
                }
                else if (!Pass.equals(confirmpassword)){
                    Toast.makeText(Admin_Signup.this, "Confirm password not matched", Toast.LENGTH_SHORT).show();
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    databaseReference.child("Admin").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(UserName)){
                                Toast.makeText(Admin_Signup.this, "UserName already exist", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                databaseReference.child("Admin").child(UserName).child("password").setValue(Pass);
                                databaseReference.child("Admin").child(UserName).child("confirm_password").setValue(confirmpassword);

                                Toast.makeText(Admin_Signup.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent i =new Intent(Admin_Signup.this, Admin_Signup.class);
                                startActivity(i);
                                progressBar.setVisibility(View.GONE);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                      progressBar.setVisibility(View.GONE);
                        }
                    });


                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}