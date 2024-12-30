package persisitance;

import model.Food;
import model.ListFoodDataBase;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import persisitance.JsonTest;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ListFoodDataBase wr = new ListFoodDataBase();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyListFoodDataBase() {
        try {
            ListFoodDataBase wr = new ListFoodDataBase();
            ArrayList<Food> foodlist = new ArrayList<>();
            wr.setUiFoodList(foodlist);

            JsonWriter writer = new JsonWriter("./data/testWriterEmptyFoodList.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyFoodList.json");
            ListFoodDataBase readWr = reader.read();

            assertEquals("List of Food", readWr.getName());
            List<Food> foods = readWr.getFoodList();
            assertEquals(0, foods.size());  // This should match the number of foods initialized in initializeFood()
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralListFoodDataBase() {
        try {
            ListFoodDataBase wr = new ListFoodDataBase();
            ArrayList<Food> foodlist = new ArrayList<>();
            foodlist.add(new Food("apple", 20, 20));
            wr.setUiFoodList( foodlist);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralFoodList.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralFoodList.json");
            wr = reader.read();
            assertEquals("List of Food", wr.getName());
            List<Food> foods = wr.getFoodList();
            assertEquals(1, foods.size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}
