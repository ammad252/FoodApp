package com.example.signupandlogin;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity
{
    //defining all variables
    private TextInputEditText emailTextView, passwordTextView,f_name,l_name,add,city,phoneno;
    private Button Btn,login,verify;
    DatabaseReference databaseReference;
    savedata savedata;
    TextView Signup;
    FirebaseDatabase db;
    DatabaseReference reference;
    private ProgressBar progressbar;

    NotificationManagerCompat notificationManagerCompat, notificationManagerCompat1;
    Notification notification, notification1;
    FirebaseUser user;
    int i=0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //user define class
        //2 reasons thy 1: fields multiple thi
        // structured code hy refine hy is liye
        savedata=new savedata();

//        verify=findViewById(R.id.verify);
        login = findViewById(R.id.Login);
        f_name=findViewById(R.id.firstname);
        phoneno=findViewById(R.id.phoneno);
        l_name=findViewById(R.id.lastname);
        add=findViewById(R.id.address);
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.passwd);
        Btn = findViewById(R.id.register);
        city=findViewById(R.id.city);
        FirebaseAuth auth = FirebaseAuth.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Login.class);
                startActivity(i);
            }
        });

// yaha sy admin ki traf sy user ko notification jata
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel("mynotification", "Notification", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "mynotification").setContentText("add successfully").setAutoCancel(true)
                .setContentText("request").setSmallIcon(android.R.drawable.stat_notify_sync);
        notification = builder.build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

        progressbar = findViewById(R.id.progressbar);

// btn is used for register new user. on screen title of this buttoon is creat an account
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password, F_name,L_name,Address,City,Phoneno;

                //whatever user insert any type of data in these boxes data will forcefully convert into string form
                email = emailTextView.getText().toString();
                password = passwordTextView.getText().toString();
                F_name=f_name.getText().toString();
                L_name=l_name.getText().toString();
                Address=add.getText().toString();
                City=city.getText().toString();
                Phoneno=phoneno.getText().toString();

                if(F_name.isEmpty()){
                    f_name.setError("Please fill Field");
                }
                if(L_name.isEmpty()){
                    l_name.setError("Please fill Field");
                }
               if(Phoneno.isEmpty() )   {
                   phoneno.setError("Please fill");
               }
                if(Phoneno.length()<10 )   {
                    phoneno.setError("10 digit must");
                    phoneno.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailTextView.setError("Check your Email");
                    emailTextView.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    passwordTextView.setError("Password Required");
                    passwordTextView.requestFocus();
                    return;
                }




                if (password.length() < 6)
                {
                    passwordTextView.setError("six digit pincode");
                    //requestfocus ye method hum ny is liye use kiya k agr cardinalities match ni kr ri to wo usi box me ruka rahy
                    // agy na jaye jb tk user teek sy data ni dy dyta
                    passwordTextView.requestFocus();
                    return;
                }

                if(Address.isEmpty()){
                    add.setError("Please fill Field");
                }
                if(City.isEmpty()){
                    city.setError("Please fill Field");
                }

                else {
                    savedata users = new savedata(F_name,L_name,email,password,Address,City,Phoneno);

                    //storing all data in firebase
                    db = FirebaseDatabase.getInstance();

                    //db is k through hum real time database ky andar ponch gaye
                    // firebase me data tree structure me store hta hy is liye
                    // getreference sy hum normaly root node  py ponch jaty


                    reference = db.getReference("savedata");

                    //top sy bottom complete firebase ma pary huwy data me move krty
                    reference.child(F_name).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {  /*is ma jo upr data diya geya user ki traf sy wo upr store ho chuka
                           yaha hum  ab un sb fields ko clear kr ry
                            hamari task complete ho chuki */


                            f_name.setText("");
                            l_name.setText("");
                            emailTextView.setText("");
                            passwordTextView.setText("");
                            add.setText("");
                            city.setText("");
                            phoneno.setText("");
                            Toast.makeText(MainActivity.this,"",Toast.LENGTH_SHORT).show();

                        }
                    });


                    /* The primary purpose of SharedPreference is to store user-specified configuration details,
                     such as settings, and to keep the user logged in to the app.
                      We can make use of SharedPreference in situations
                      where we don't need to store a lot of data and we don't require any specific structure.

                      sharedpreference hum tb use krty jb data long ni hta
                      ye key or mode ky pairs sy kam krta jesy hashing hti wesy
                      sharedpre aik file hy jis ma hum likty

                     */

                    SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("value", F_name);
                    editor.putString("value1", L_name);
                    editor.putString("value2", email);
                    editor.putString("value3", password);
                    editor.putString("value4", Address);
                    editor.putString("value5", City);
                    editor.putString("value6",Phoneno);
                    editor.apply();

                    Intent intent = new Intent(MainActivity.this, Approve.class);
                    startActivity(intent);
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }

                    notificationManagerCompat.notify(1, notification);
                    Toast.makeText(MainActivity.this, "Your request Send to Admin ", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public boolean isConnected() {
        boolean connected = false;
        try
        {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        }
        catch (Exception e)
        {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

    }
