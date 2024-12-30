package persisitance;

import model.Food;
import model.ListFoodDataBase;
import org.junit.jupiter.api.Test;
import persistance.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ListFoodDataBase wr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyListFoodDataBase() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyFoodList.json");
        try {
            ListFoodDataBase wr = reader.read();
            // Check if the name and the list of recommended food are empty
            assertEquals("List of Food", wr.getName());
            List<Food> foods = wr.getFoodList();
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralListFoodDataBase() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralFoodList.json");
        try {
            ListFoodDataBase wr = reader.read();
            assertEquals("List of Food", wr.getName());
            List<Food> foods = wr.getFoodList();
            assertEquals(2, foods.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}
