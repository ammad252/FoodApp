package com.example.signupandlogin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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

public class Login extends AppCompatActivity {
    private TextInputEditText emailTextView, passwordTextView;
    private Button Btn, signup;

    private FirebaseAuth mAuth;
    TextView login, forgetpass, Admin_login, textView;

    public ProgressDialog loginprogress;

    LottieAnimationView lottieAnimationView,pb;
    private DatabaseReference usersRef;


    @Override
    protected void onStart() {
        super.onStart();


        FirebaseUser user = mAuth.getCurrentUser();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //fullscreen show krwany k liye
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Login");

        mAuth = FirebaseAuth.getInstance();
        forgetpass = findViewById(R.id.forgetpass);
        lottieAnimationView = findViewById(R.id.progress_Bar);
        // initialising all views through id defined above

        loginprogress = new ProgressDialog(this);
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.password);
        Btn = findViewById(R.id.btn_login);
//        progressbar = findViewById(R.id.progress_Bar);
        login = findViewById(R.id.LoginScreen);
        signup = findViewById(R.id.SignupScreen);
        Admin_login = findViewById(R.id.adminlogin);
        pb=findViewById(R.id.progress_Bar);

        Admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, SignInAdmin.class);
                startActivity(intent);
                finish();
            }
        });

        //SignUp screen
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
//
            }
        });

        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecoverPasswordDialog();
            }

            ProgressDialog loadingBar;

            private void showRecoverPasswordDialog() {
                //built in class
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setTitle("Recover Password");
                //xml me code fix pre define hta yaha ye dynamicly show krny k liye
                LinearLayout linearLayout = new LinearLayout(Login.this);
                //jesy xml ma hum properties etc define krty wesy ye line yaha same task perform kr ri
                final EditText emailet = new EditText(Login.this);

                // write the email using which you registered
                emailet.setText("Email");
                emailet.setMinEms(16);
                emailet.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                linearLayout.addView(emailet);
                linearLayout.setPadding(10, 10, 10, 10);
                builder.setView(linearLayout);

                // Click on Recover and a email will be sent to your registered email id
                builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String email = emailet.getText().toString().trim(); //space ko consider na kry just word consider kry trim
                        beginRecovery(email);
                    }
                });
///
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();//builder build ho chuka
            }

            private void beginRecovery(String email) {
                loadingBar = new ProgressDialog(Login.this);
                loadingBar.setMessage("Sending Email....");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        loadingBar.dismiss();
                        if (task.isSuccessful()) {

                            Toast.makeText(Login.this, "Email Sent", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Login.this, "Something went Wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loadingBar.dismiss();
                        Toast.makeText(Login.this, "Something went Wrong", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        lottieAnimationView.setVisibility(View.GONE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");

        // Set ClickListener on Sign-in button
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                String email = emailTextView.getText().toString();
                String password = passwordTextView.getText().toString();

                // ... (your existing code)

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        String userId = user.getUid();
                                        pb.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
//
                                        Intent intent = new Intent(Login.this, fragemnt_dashobard.class);
                                        startActivity(intent);

                                        // Retrieve user-specific data from Realtime Database
                                        usersRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {
                                                    String userData = dataSnapshot.child("data").getValue(String.class);
                                                    // ... (handle your data)

                                                    // Navigate to the appropriate screen

                                                } else {
                                                    // Handle data not found for the user
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                // Handle data retrieval cancellation
                                            }
                                        });
                                    }
                                } else {
                                    // Handle login failure
                                }
                            }
                        });
            }
        });
    }


    //Set ClickListener on Sign-in button
//        Btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Take the value of two edit texts in Strings
//                String email, password;
//                email = emailTextView.getText().toString();
//                password = passwordTextView.getText().toString();
//
//                // validations for input email and password
//                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
//                    Toast.makeText(getApplicationContext(), "Please enter Email & Password", Toast.LENGTH_LONG).show();
////                    emailTextView.setError("Please Enter email");
////                    emailTextView.requestFocus();
////                    passwordTextView.setError("Password Enter password");
////                    passwordTextView.requestFocus();
//                    return;
//                }
//                if (TextUtils.isEmpty(email)) {
//                    Toast.makeText(getApplicationContext(), "Please enter email!", Toast.LENGTH_LONG).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(password)) {
//                    Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
//                    return;
//                }
//                // Check for Internet Connection
//                if (isConnected()) {
//
//                    lottieAnimationView.setVisibility(View.VISIBLE);
//
//                    // signin existing user
//                    mAuth.signInWithEmailAndPassword(email, password)
//                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(
//                                        @NonNull Task<AuthResult> task) {
//                                    if (task.isSuccessful()) {
//                                        FirebaseUser user = mAuth.getCurrentUser();
//                                        if (user != null) {
//                                            String userId = user.getUid();
//                                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
//                                            Intent intent = new Intent(Login.this,fragemnt_dashobard.class);
//                                            startActivity(intent);
//                                            // hide the progress bar
//                                            lottieAnimationView.setVisibility(View.GONE);
//
////
//                                            // if sign-in is successful
//                                            // fields clear
//                                            emailTextView.getText().clear();
//                                            passwordTextView.getText().clear();
//
//                                        } else {
//                                            // sign-in failed popup
//                                            Toast.makeText(getApplicationContext(), "Login failed! " + "Admin request Approve then logon", Toast.LENGTH_LONG).show();
//
//                                            // hide the progress bar
//                                            lottieAnimationView.setVisibility(View.GONE);
//                                        }
//                                    }
//                                }
//                            });
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//
//
//
//    }




//   // @Override
//   // public void onActivityResult(int requestCode, int resultCode, Intent data) {
//   //     super.onActivityResult(requestCode, resultCode, data);
//
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                firebaseAuthWithGoogle(account);
//            } catch (ApiException e) {
//                // Google Sign In failed, update UI appropriately
//                // ...
//                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }
//    }



    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
