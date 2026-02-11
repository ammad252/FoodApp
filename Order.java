package com.example.signupandlogin;

public class Order {
    private String dish;
    private String foodName;

    public Order() {
        // Default constructor required by Firebase
    }

    public Order(String dish, String foodName) {
        this.dish = dish;
        this.foodName = foodName;
    }

    public String getDish() {
        return dish;
    }

    public String getFoodName() {
        return foodName;
    }
}
