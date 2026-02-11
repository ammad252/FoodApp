package com.example.signupandlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

public class dashboardadmindishview extends AppCompatActivity {
LottieAnimationView o,so;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboardadmindishview);
        getSupportActionBar().setTitle("View Order");
    o=findViewById(R.id.o);
    so=findViewById(R.id.so);
    o.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i =new Intent(dashboardadmindishview.this,Adminviewdishes.class);
            startActivity(i);
        }
    });
    so.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i =new Intent(dashboardadmindishview.this,Display.class);
            startActivity(i);
        }
    });
    }
}