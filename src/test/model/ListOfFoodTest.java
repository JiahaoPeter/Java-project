package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ListOfFoodTest {
    private ListFoodDataBase FoodList1;
    private ListFoodDataBase FoodList2;

    @BeforeEach
    void runbefore(){
        FoodList1 = new ListFoodDataBase();
        FoodList2 = new ListFoodDataBase();
        FoodList1.initializeFood();
    }

    @Test
    void testEmptyFoodList() {
        assertTrue(FoodList2.getListOfFood().isEmpty());
    }


    @Test
    void testgetLowCaloriesFoodList() {
        List<Food> lowCaloriesFoodList = FoodList1.getlowCaloriesFoood();
        for(Food food: lowCaloriesFoodList) {
            assertTrue(food.getCaloriesOfFood() < 260);
        }
    }

    @Test
    void testgetHihgCaloriesFoodList() {
        List<Food> HighCaloriesFoodList = FoodList1.gethighCaloriesFoood();
        for(Food food: HighCaloriesFoodList) {
            assertTrue(food.getCaloriesOfFood() >= 260);
        }
    }

    @Test
    void testaddFoodTFoodList() {
        FoodList1.addFoodToList(new Food("banana",60,6));
        List<Food> lowCaloriesFoodList = FoodList1.getlowCaloriesFoood();
        for(Food food: lowCaloriesFoodList) {
            assertTrue(food.getCaloriesOfFood() < 260);
        }
    }

    @Test
    void testgetThingies() {
        FoodList2.addFoodToList(new Food("banana",60,6));
        List<Food> lowCaloriesFoodList = FoodList1.getThingies();

        for(Food food: lowCaloriesFoodList) {
            assertTrue(food.getCaloriesOfFood() < 2600);
        }
    }

}
