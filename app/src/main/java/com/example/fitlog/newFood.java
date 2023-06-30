package com.example.fitlog;

public class newFood {
    public String calorie;
    public String foodname;

    public newFood(String calorie, String foodname) {
        this.foodname = foodname;
        this.calorie = calorie;
    }

    public newFood() {
    }

    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }
}
