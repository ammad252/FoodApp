package com.example.signupandlogin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInAdmin extends AppCompatActivity {


    private TextInputEditText username, password;
    private TextView PLogin;
    private Button login_btn;
    private ProgressBar progressbar;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foodappdelivery-936ee-default-rtdb.firebaseio.com/");

    //aik app sy dusri application me move krty or ye us ki identification key hti wapis any k liye
    @Override
    protected void onStart() {
        super.onStart();


        FirebaseUser user = mAuth.getCurrentUser();
/*        if(user!=null){
           Intent intent = new Intent(getApplicationContext(),fragemnt_dashobard.class);
           startActivity(intent);
       } */


    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sign_in_admin);
        getSupportActionBar().setTitle("AdminSignin");

        mAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.Username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.btn_login);
        PLogin = findViewById(R.id.LoginP);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);

        final Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.bounce);


        progressbar = findViewById(R.id.progress_Bar);


        PLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PLogin.startAnimation(animation1);
                Intent i = new Intent(SignInAdmin.this, Login.class);
                startActivity(i);
                finish();
            }
        });


        login_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                login_btn.startAnimation(animation);
                final String User = username.getText().toString();
                final String pass = password.getText().toString();

                // Check for Internet Connection
                if (isConnected()) {

                    if (User.isEmpty() && pass.isEmpty()) {
                        Toast.makeText(SignInAdmin.this, "Please add your credentials", Toast.LENGTH_SHORT).show();
                    } else if (!User.isEmpty() && pass.isEmpty()) {
                        Toast.makeText(SignInAdmin.this, "Please add your password", Toast.LENGTH_SHORT).show();
                    } else if (User.isEmpty() && !pass.isEmpty()) {
                        Toast.makeText(SignInAdmin.this, "Please add your Username", Toast.LENGTH_SHORT).show();
                    } else {
                        progressbar.setVisibility(View.VISIBLE);
                        databaseReference.child("Admin").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.hasChild(User)) {
                                    final String getpassword = snapshot.child(User).child("password").getValue(String.class);
                                    if (getpassword.equals(pass)) {
                                        Toast.makeText(SignInAdmin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(SignInAdmin.this, Admin_Daskboard.class);
                                        startActivity(i);
                                        progressbar.setVisibility(View.GONE);
                                        //------------------------------------
                                        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putBoolean("isAdminLogin", true);//isi app k editor objec ma tha data
                                        editor.commit();//ab ja ky data file me store ho geyaaa
                                        //------------------------------------
                                        // fields clear
                                        username.getText().clear();
                                        password.getText().clear();
                                        finish();
                                    } else {
                                        Toast.makeText(SignInAdmin.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                        progressbar.setVisibility(View.GONE);
                                    }
                                } else {
                                    Toast.makeText(SignInAdmin.this, "Login failed! check your credentials!", Toast.LENGTH_SHORT).show();
                                    progressbar.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }



    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }


}