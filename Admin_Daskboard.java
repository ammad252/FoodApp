package com.example.signupandlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.airbnb.lottie.LottieAnimationView;

public class Admin_Daskboard extends AppCompatActivity {
    LottieAnimationView detailfood, vieworder, upload, addadmin ,list;
    CardView approve;
    ViewFlipper flipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_daskboard);
        getSupportActionBar().setTitle("Dashboard");
         approve=findViewById(R.id.approve);

        detailfood = findViewById(R.id.detail);
        vieworder = findViewById(R.id.dish);
        upload = findViewById(R.id.upload);
        addadmin=findViewById(R.id.addadmin);
      //  list=findViewById(R.id.approvelist);

        flipper = findViewById(R.id.flipper);
        int imgarray[] = {R.drawable.v1, R.drawable.v2, R.drawable.v3, R.drawable.v4};
        for (int i = 0; i < imgarray.length; i++)
            showimage(imgarray[i]);

//        list.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i =new Intent(Admin_Daskboard.this,dishclass.class);
//                startActivity(i);
//            }
//        });
addadmin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i =new Intent(Admin_Daskboard.this,Admin_Signup.class);
        startActivity(i);
    }
});
approve.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i =new Intent(Admin_Daskboard.this,emailverification.class);
        startActivity(i);
    }
});

     detailfood.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        Intent i = new Intent(getApplicationContext(),fragemnt_dashobard1.class);
        startActivity(i);
    }
    });
     vieworder.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        Intent i = new Intent(getApplicationContext(), dashboardadmindishview.class);
        startActivity(i);
    }
    });
     upload.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        Intent i = new Intent(Admin_Daskboard.this, Upload_Recipe1.class);
        startActivity(i);
    }
    });

}
    private void showimage(int i) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(i);
        flipper.addView(imageView);
        flipper.setFlipInterval(4000);
        flipper.setAutoStart(true);
        flipper.setInAnimation(this, android.R.anim.slide_in_left);
        flipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}