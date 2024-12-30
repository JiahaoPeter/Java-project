package persisitance;

import model.Food;
import model.ListFoodDataBase;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class JsonTest {
    protected void checkThingy(String name, ListFoodDataBase thingy) {
        assertEquals(name, thingy.getName());
    }
}
