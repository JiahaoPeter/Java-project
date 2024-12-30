package model;

// A Food class represents the food with its name, calories per 100g and price.

import org.json.JSONObject;
import persistance.Writable;

public class Food implements Writable {



    private String name; // The name of the food
    private double caloriesOfFood; // 100g certain food's calories
    private double price; // Amount of price($) in 1kg


    // Constructor
    // Required Clause: caloriesOfFood and price should be non-negative
    // Effect Clause: create a constructor of Food with name, calories, and price
    public Food(String n, double c, double p) {
        this.name = n;
        this.caloriesOfFood = c;
        this.price = p;

    }

    // Effect Clause: return the name of the food
    public String getName() {
        return name; // return the name of the food
    }

    // Effect Clause: return the calories of the food
    public double getCaloriesOfFood() {
        return caloriesOfFood; // return the calories of the food
    }

    // // Effect Clause: return the price of the food
    public double getPrice() {
        return price; // return the price of the food
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("food name", name);
        json.put("food calories", caloriesOfFood);
        json.put("food price", price);
        return json;
    }
}
