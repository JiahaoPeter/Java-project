package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FoodTest {
    private Food apple;

    @BeforeEach
    void runbefore() {
        apple = new Food("apple",30,3);
    }

    @Test
    void testFoodPrice() {
        assertEquals(3,apple.getPrice());
    }

    @Test
    void testGetNAme() {
        assertEquals("apple", apple.getName());
    }

}
