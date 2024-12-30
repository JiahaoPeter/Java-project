package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// A class represents a list of food

public class ListFoodDataBase implements Writable {
    private List<Food> listOfFood;
    private String name;
    private List<Food> uiFoodList;
    private EventLog eventLog;

    //Constructor
    // Effect Clause: constructs a empty list of Food
    public ListFoodDataBase() {

        listOfFood = new ArrayList<>();
        name = "List of Food";
        eventLog = EventLog.getInstance();
    }

    // Modifies Clause: this
    // Effect Clause:Add Food to a list of Food
    public void addFoodToList(Food f) {
        listOfFood.add(f);
        eventLog.logEvent(new Event("Added Food:" + f.getName()));

    }

    public void setUiFoodList(List<Food> foodList) {
        this.uiFoodList = foodList;
    }


    // Effect Clause: return a list of FOod
    public List<Food> getListOfFood() {
        return listOfFood; // return a list of FOod
    }

    //initialize the list of food that contains all food defined.
    // Modifies Clause: this
    // Effect Clause: add Food to a list
    public void initializeFood() {
        listOfFood.add(apple);
        listOfFood.add(brownRice);
        listOfFood.add(pasta);
        listOfFood.add(beef);
        listOfFood.add(salmon);
        listOfFood.add(oatmeal);
        listOfFood.add((grainbread));
        listOfFood.add(broccoli);
        listOfFood.add(banana);
        addFoodList();
    }

    private void addFoodList() {
        listOfFood.add(zucchini);
        listOfFood.add(cucumber);
        listOfFood.add(spinach);
        listOfFood.add(peanutButter);
        listOfFood.add(almondButter);
        listOfFood.add(avocado);
        listOfFood.add(nuts);
        listOfFood.add(chocolate);
        listOfFood.add(coconutoil);
        listOfFood.add(cheese);
        listOfFood.add(icecream);
        listOfFood.add(cashews);
        listOfFood.add(honey);
        listOfFood.add(mayonnaise);
        listOfFood.add(tahini);
        listOfFood.add(maplesyrup);
        for (Food f: listOfFood) {
            eventLog.logEvent(new Event("Initialized Food" + f.getName()));
        }
    }


    // Effect Clause: create a list of food with calories less than 260
    public List<Food> getlowCaloriesFoood() {
        List<Food> lowercalFoodList = new ArrayList<>();
        for (Food f : listOfFood) {
            if (f.getCaloriesOfFood() < 260) {
                lowercalFoodList.add(f);
            }

        }
        for (Food f: lowercalFoodList) {
            eventLog.logEvent(new Event("Low Weight Food List:" + f.getName()));
        }
        return lowercalFoodList;
    }

    // Effect Clause: create a list of food with calories grater or equal to 260
    public List<Food> gethighCaloriesFoood() {
        List<Food> highcalFoodList = new ArrayList<>();
        for (Food f : listOfFood) {
            if ((f.getCaloriesOfFood()) >= 260) {
                highcalFoodList.add(f);
            }

        }
        for (Food f: highcalFoodList) {
            eventLog.logEvent(new Event("Gain Weight Food List:" + f.getName()));
        }
        return highcalFoodList;
    }

    //Foods with low calories(target user: lose weight)
    //Feature: calories < 260 per 100g food

    Food apple = new Food("apple", 52, 1);
    Food brownRice = new Food("brownRice", 123, 1);
    Food pasta = new Food("pasta", 52, 1.5);
    Food beef = new Food("beef", 250, 5);
    Food salmon = new Food("salmon", 206, 0.75);
    Food oatmeal = new Food("oatmeal", 245, 1.2);
    Food grainbread = new Food("grain bread", 265, 2);
    Food broccoli = new Food("broccoli", 55, 5);
    Food banana = new Food("banana", 89, 1.5);
    Food zucchini = new Food("zucchini", 17, 1.99);
    Food cucumber = new Food("cucumber", 16, 1.49);
    Food spinach = new Food("spinach", 23, 3.99);


    //Foods with high calories(target user: gain weight)
    //Feature: calories >= 260 per 100g food

    Food peanutButter = new Food("peanut butter", 589, 5.99);
    Food almondButter = new Food("almond butter", 614, 9.99);
    Food avocado = new Food("avocado", 260, 2.49);
    Food nuts = new Food("nuts", 607, 13.99);
    Food chocolate = new Food("chocolate", 546, 19.99);
    Food coconutoil = new Food("coconut oil", 862, 8.99);
    Food cheese = new Food("cheese", 402, 11.99);
    Food icecream = new Food("ice cream", 345, 3.99);
    Food cashews = new Food("cashews", 553, 12.49);
    Food honey = new Food("honey", 304, 9.99);
    Food mayonnaise = new Food("mayonnaise", 361, 5.99);
    Food tahini = new Food("tahini", 595, 7.99);
    Food maplesyrup = new Food("maple syrup", 260, 12.99);


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("food name",name);
        json.put("list of recommended food", thingiesToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray thingiesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Food t : uiFoodList) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

//    // EFFECTS: returns an unmodifiable list of thingies in this workroom
    public List<Food> getThingies() {
        return (listOfFood);
    }

    public String getName() {
        return name;
    }

    public List<Food> getFoodList() {
        return listOfFood;
    }
}
