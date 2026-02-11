package com.example.signupandlogin;

public class dishclass {
    String dish,food;

    public dishclass() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public dishclass(String dish,String food) {
        this.dish = dish;
        this.food=food;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }
}
