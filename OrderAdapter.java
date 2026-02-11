package com.example.signupandlogin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.orderDishTextView.setText("Dish: " + order.getDish());
        holder.orderFoodNameTextView.setText("Food Name: " + order.getFoodName());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderDishTextView, orderFoodNameTextView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderDishTextView = itemView.findViewById(R.id.orderDishTextView);
            orderFoodNameTextView = itemView.findViewById(R.id.orderFoodNameTextView);
        }
    }
}
