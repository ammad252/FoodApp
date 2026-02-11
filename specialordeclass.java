package com.example.signupandlogin;

public class specialordeclass {
    private String dish;
    private String food;

    public specialordeclass() {
        // Default constructor required for calls to DataSnapshot.getValue(specialordeclass.class)
    }

    public specialordeclass(String dish, String food) {
        this.dish = dish;
        this.food = food;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }
}
