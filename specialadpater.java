package com.example.signupandlogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class specialadpater extends RecyclerView.Adapter<specialadpater.MyViewHolder> {
    Context context;
    String user;
    FirebaseAuth firebaseAuth;
    ArrayList<specialordeclass> list;

    public specialadpater(Context context, ArrayList<specialordeclass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public specialadpater.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.specialitem,parent,false);
        return  new specialadpater.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        specialordeclass user = list.get(position);
        if (user != null) {
            if (user.getDish() != null) {
                holder.d.setText(user.getDish());
            }
            if (user.getFood() != null) {
                holder.foodname.setText(user.getFood());
            }
        }

    }



    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView d, foodname;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            d = itemView.findViewById(R.id.rep);
            foodname = itemView.findViewById(R.id.foodname);

        }

        public String getCurrentUserUID() {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                return currentUser.getUid();
            } else {
                return "";
            }
        }

    }}
