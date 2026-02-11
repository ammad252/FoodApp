package com.example.signupandlogin;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.signupandlogin.databinding.ActivityRejectBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class reject extends AppCompatActivity {
    ActivityRejectBinding binding;
    DatabaseReference reference;
    NotificationManagerCompat notificationManagerCompat, notificationManagerCompat1;
    Notification notification, notification1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRejectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.deletedataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
                String username = binding.etusername.getText().toString();
                String value = sharedPreferences.getString("value", "");
              if (!value.isEmpty()) {

                    deleteData(value);

                } else {

                    Toast.makeText(reject.this, "Please fill", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void deleteData(String value) {

        reference = FirebaseDatabase.getInstance().getReference("savedata");
        reference.child(value).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                        NotificationChannel channel = new NotificationChannel("mynotification", "Notification", NotificationManager.IMPORTANCE_DEFAULT);

                        NotificationManager notificationManager = getSystemService(NotificationManager.class);
                        notificationManager.createNotificationChannel(channel);
                    }
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "mynotification").setContentText("add successfully").setAutoCancel(true)
                            .setContentText("Reject").setSmallIcon(android.R.drawable.stat_notify_sync);
                    notification = builder.build();
                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
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
                    Toast.makeText(getApplicationContext(), "reject ", Toast.LENGTH_SHORT).show();

                    Toast.makeText(reject.this, "reject", Toast.LENGTH_SHORT).show();
                    binding.etusername.setText("");





                } else {
                    Toast.makeText(reject.this,"Failed: " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}