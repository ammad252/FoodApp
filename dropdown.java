package com.example.signupandlogin;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class dropdown extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //    String[] courses = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    TextView okay_text, cancel_text, T_D ;
    NotificationManagerCompat notificationManagerCompat, notificationManagerCompat1;
    Notification notification, notification1;
    EditText  fname,sendto,      editTextTo,editTextSubject,editTextMessage;
    Button submit;
    private int abc = 0;
    String t_dish, F_name;
    FirebaseDatabase db;
    DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private FirebaseDatabase database;
    private DatabaseReference userReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropdown);
        getSupportActionBar().setTitle("Order");

        editTextTo=(EditText)findViewById(R.id.editText1);
        editTextSubject=(EditText)findViewById(R.id.editText2);
        editTextMessage=(EditText)findViewById(R.id.editText3);
        firebaseAuth =FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        editTextTo.setText(firebaseUser.getEmail());

        Spinner spinner = findViewById(R.id.spinner);

        List<String> numbersList = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            numbersList.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, numbersList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel("mynotification", "Notification", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "mynotification").setContentText("add successfully").setAutoCancel(true)
                .setContentText("order").setSmallIcon(android.R.drawable.stat_notify_sync);
        notification = builder.build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

        T_D = findViewById(R.id.dishes);
        fname = findViewById(R.id.f_name);
        submit = findViewById(R.id.submit);
//        T_D.setFilters(new InputFilter[]{new MinMaxFilter("1", "10")});


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                t_dish = T_D.getText().toString();
                F_name = fname.getText().toString();
                String to=editTextTo.getText().toString();
                String subject=editTextSubject.getText().toString();
                String message=editTextMessage.getText().toString();


                // Check for Internet Connection
                if (t_dish.isEmpty() && F_name.isEmpty()) {
                    Toast.makeText(dropdown.this, "fill field", Toast.LENGTH_SHORT).show();
                }
                if (!t_dish.isEmpty() && !F_name.isEmpty()) {
                    Dialog dialog = new Dialog(dropdown.this);
                    dialog.setContentView(R.layout.dialog);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.setCancelable(false);
                    dialog.getWindow().getAttributes().windowAnimations = R.style.Theme_Signup;

                    okay_text = dialog.findViewById(R.id.okay_text);
                    cancel_text = dialog.findViewById(R.id.cancel_text);

                    okay_text.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();


                            dishclass users = new dishclass(t_dish, F_name);
                            db = FirebaseDatabase.getInstance();
                            reference = db.getReference("Dishes");
                            reference.child(t_dish).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {

//                                    final String useremail = "ammadsaleem252@gmail.com";
//                                    final String password = "Vx~4]4p6s#>z-~UT";
//                                    String messagetosend = body.getText().toString();
//                                    Properties props = new Properties();
//                                    props.put("mail.smtp.auth", "true");
//                                    props.put("mail.smtp.starttls.enable", "true");
//                                    props.put("mail.smtp.host", "smtp.gmail.com");
//                                    props.put(",ail.smtp.post", "587");
//                                    Session session = Session.getInstance(props, new Authenticator() {
//                                        @Override
//                                        protected PasswordAuthentication getPasswordAuthentication() {
//                                            return new PasswordAuthentication(useremail, password);
//
//
//                                        }
//                                    });
//                                    try {
//                                    Message message1=new MimeMessage(session);
//                                    message1.setFrom(new InternetAddress(useremail));
//                                    message1.setRecipients(Message.RecipientType.TO,InternetAddress.parse(emaill.getText().toString()));
//                                    message1.setSubject("food");
//                                    message1.setText("order");
//                                    Transport.send(message1);
//                                        Toast.makeText(dropdown.this, "send email", Toast.LENGTH_SHORT).show();
//                                    }
//                                    catch (Exception e){
//                                    throw new RuntimeException(e);
//                                    }


                                    Intent i = new Intent(dropdown.this, ordersuccsully.class);
                                    startActivity(i);
                                    Toast.makeText(dropdown.this, "Successfully", Toast.LENGTH_SHORT).show();
                                    if (ActivityCompat.checkSelfPermission(dropdown.this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
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

                                    Intent emaill = new Intent(Intent.ACTION_SEND);
                                    emaill.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
                                    emaill.putExtra(Intent.EXTRA_SUBJECT, subject);
                                    emaill.putExtra(Intent.EXTRA_TEXT, message);

                                    //need this to prompts email client only
                                    emaill.setType("message/rfc822");

                                    startActivity(Intent.createChooser(emaill, "Choose an Email client :"));


                                }
                            });

                        }


                    });
                    StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    cancel_text.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Toast.makeText(dropdown.this, "Your Order is Cancel ", Toast.LENGTH_SHORT).show();
                        }
                    });

                    dialog.show();
                }
            }




        });

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Get the selected number from the Spinner
        String selectedNumber = parent.getItemAtPosition(position).toString();

        // Update the TextView with the selected number
        T_D.setText(selectedNumber);
//        int result = abc * selectedNumber;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
    }
}