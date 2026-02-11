package com.example.signupandlogin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MyAdapterdatashow extends RecyclerView.Adapter<MyAdapterdatashow.MyViewHolder> {
    Context context;
    FirebaseAuth mAuth;
    FirebaseUser user;
    ArrayList<savedata> list;


    public MyAdapterdatashow(Context context, ArrayList<savedata> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.displayitem,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mAuth = FirebaseAuth.getInstance();

        savedata user = list.get(position);
        holder.firstName.setText(user.getFname());

        holder.lastname.setText(user.getLname());

        holder.email.setText(user.getEmail());

        holder.passwd.setText(user.getPassword());
          holder.phoeno.setText(user.getPhoneno());
        holder.address.setText(user.getAddress());

        holder.city.setText(user.getCity());
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i =new Intent(context,reject.class);
                context.startActivity(i);
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(context,adminapprove.class);
                         context.startActivity(i);
//                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
////                        .setEnabled(true);
//
//                        if (task.isSuccessful()) {
//
//                            Toast.makeText(context, "verify", Toast.LENGTH_SHORT).show();
//                            Intent i =new Intent(context,thankyou.class);
//                            context.startActivity(i);
//
//                        } else {
//                            Toast.makeText(context, "Failed verification", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView firstName, lastname,email,passwd,address,city,phoeno;
        Button button,reject;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            reject=itemView.findViewById(R.id.rej);
            firstName = itemView.findViewById(R.id.tvfirstName);
            button=itemView.findViewById(R.id.button);
            lastname= itemView.findViewById(R.id.tvlastnmae);
            email= itemView.findViewById(R.id.tvEmail);
            passwd = itemView.findViewById(R.id.tvPassword);
            phoeno=itemView.findViewById(R.id.phoneno);
            address= itemView.findViewById(R.id.tvAddress);
            city= itemView.findViewById(R.id.tvCity);
        }
}
}
