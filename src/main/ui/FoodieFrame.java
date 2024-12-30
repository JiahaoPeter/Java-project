package ui;

import model.Event;
import model.EventLog;
import model.Food;
import model.ListFoodDataBase;
import persistance.JsonReader;
import persistance.JsonWriter;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

// A class represents a GUI for the food app
public class FoodieFrame extends JFrame implements ActionListener {
    private JTextField tf1;
    private JTextField tf2;
    private JTextField tf3;
    private JTextField tf4;
    private JCheckBox cb1;
    private JCheckBox cb2;
    private JMenu menu;
    private JMenuItem i1;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private ListFoodDataBase listFoodDataBase1;
    private ListFoodDataBase jasonFoodList;
    private static final String JSON_STORE = "./data/workroom.json";
    private EventLog eventLog;

    /*
     * Initializes the FoodieFrame with UI components and sets up the initial state.
     * Modifies: This
     * Effects: Sets up the UI components, initializes data structures, and displays the frame.
     */
    public FoodieFrame() {

        super();
        NewPanel result = getNewPanel();


        initilize();


        JButton b = new JButton("Submit");
        b.setBounds(130, 100, 100, 40);
        b.addActionListener(this);


        setContentPane(result.contentPanel);
        addButton(result, b);


        ImageIcon image = new ImageIcon("src/main/ImageIcon/FoodApplogo.png");
        this.setIconImage(image.getImage());

        this.setSize(500, 500);
//        this.setLayout(null);
        this.setVisible(true);


        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printAllEventsToConsole();
            }
        });


    }

    // Modifies:
    // Effects: create the button in GUI for user

    private void addButton(NewPanel result, JButton b) {
        result.contentPanel.add(result.label);
        result.contentPanel.add(tf1);
        result.contentPanel.add(result.label2);
        result.contentPanel.add(tf2);
        result.contentPanel.add(result.label4);
        result.contentPanel.add(tf4);
        result.contentPanel.add(result.label3);
        result.contentPanel.add(cb1);
        result.contentPanel.add(cb2);
        result.contentPanel.add(b);
    }

    // Modifies:
    // Effects: initilize the frame
    private void initilize() {
        tf1 = new JFormattedTextField();
        tf1.setBounds(50, 50, 120, 20);
        tf2 = new JFormattedTextField();
        tf2.setBounds(50, 50, 120, 20);
        tf3 = new JFormattedTextField();
        tf3.setBounds(50, 50, 120, 20);
        tf4 = new JFormattedTextField();
        tf4.setBounds(50, 50, 120, 20);

        cb1.setBounds(100, 200, 150, 20);
        cb2.setBounds(100, 250, 80, 30);
    }

    // Modifies:
    // Effects: get new panel for the food list
    private NewPanel getNewPanel() {
        listFoodDataBase1 = new ListFoodDataBase();
        listFoodDataBase1.initializeFood();
        jasonFoodList = new ListFoodDataBase();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        this.setTitle("FoodieApp");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
        JLabel label = new JLabel("Enter Your Weight(kg):");
        JLabel label2 = new JLabel("Enter Your Height(cm):");
        JLabel label3 = new JLabel("Select Your Sex Gender(male/female):");
        JLabel label4 = new JLabel("Enter Your Age:");
        cb1 = new JCheckBox("Male");
        cb2 = new JCheckBox("Female");

        menu = new JMenu("Menu");
        i1 = new JMenuItem("See Loaded Food List");
        JMenuBar mb = new JMenuBar();
        menu.add(i1);
        mb.add(menu);
        this.setJMenuBar(mb);
        NewPanel result = new NewPanel(contentPanel, label, label2, label3, label4);
        return result;
    }

    // Modifies:  this
    // Effects: set the panel
    private static class NewPanel {
        public final JPanel contentPanel;
        public final JLabel label;
        public final JLabel label2;
        public final JLabel label3;
        public final JLabel label4;

        public NewPanel(JPanel contentPanel, JLabel label, JLabel label2, JLabel label3, JLabel label4) {
            this.contentPanel = contentPanel;
            this.label = label;
            this.label2 = label2;
            this.label3 = label3;
            this.label4 = label4;
        }
    }


    /*
     * Handles the actionPerformed event for the Submit button.
     * Requires: None
     * Modifies: None
     * Effects: Determines the selected gender and calculates daily calories accordingly.
     *           Calls the appropriate method for further actions based on the calculated calories.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        while (true) {
            if (cb1.isSelected() && cb2.isSelected()) {
                JOptionPane.showMessageDialog(this, "Invalid Selection!");
                break;
            }
            if (cb1.isSelected()) {
                calculateMaleDailyCalories();
                break;
            }
            if (cb2.isSelected()) {
                calculateFemaleDailyCalories();
                break;
            }

        }


    }

    // EFFECTS: calculate female calories
    private void calculateFemaleDailyCalories() {
        try {
            float weight = Float.parseFloat(tf1.getText());
            float height = Float.parseFloat(tf2.getText());
            int age = Integer.parseInt(tf4.getText());

            double dailyCalories = 655 + 9.6 * weight + 1.8 * height - 4.7 * age;

            showWeightOptions(dailyCalories);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Invalid input. Please enter valid numbers for weight, height, and age.");
        }
    }
    // EFFECTS: calculate male calories

    private void calculateMaleDailyCalories() {
        try {
            float weight = Float.parseFloat(tf1.getText());
            float height = Float.parseFloat(tf2.getText());
            int age = Integer.parseInt(tf4.getText());

            double dailyCalories = 66 + 13.7 * weight + 5 * height - 6.8 * age;
            showWeightOptions(dailyCalories);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Invalid input. Please enter valid numbers for weight, height, and age.");
        }
    }

    // EFFECTS: show weight options
    private void showWeightOptions(double dailyCalories) {
        ChooseOption option = getChooseOption(dailyCalories);

        if (option.choice == 0) {
            gainWeightOption(dailyCalories, option.options);
        } else if (option.choice == 1) {
            handleWeight(dailyCalories, option);
        }
    }

    // EFFECTS: handle weight options
    private void handleWeight(double dailyCalories, ChooseOption option) {
        dailyCalories -= 500;

        JOptionPane.showMessageDialog(this,
                "Your suggested calories intake: " + dailyCalories);

        AddFoodList result = getAddFoodList(option.options);

        if (result.choice2 == 0) {
            jasonFoodList.setUiFoodList(result.lowfoodlist);
            saveFoodList();
        } else if (result.choice2 == 1) {
            loadFoodList();
        } else if (result.choice2 == 2) {
            handleAddFood(result);
        }
    }

    // EFFECTS: handle add food to the food list
    private void handleAddFood(AddFoodList result) {
        AddFoodPanel2 result2 = getAddFoodPanel2();
        while (true) {
            int addFoodResult = JOptionPane.showConfirmDialog(this, result2.addFoodPanel,
                    "Add Food", JOptionPane.OK_CANCEL_OPTION);

            if (addFoodResult == JOptionPane.OK_OPTION) {

                try {
                    int choice3 = lowFoodList(result2.nameField, result2.priceField,
                            result2.caloriesField, result.lowfoodlist);
                    if (considerOptin(result, choice3)) {
                        break;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this,
                            "Invalid input. Please enter valid numbers for price and calories.");
                    break; // Exit the loop if there is an exception
                }
            }
        }
    }

    // EFFECTS: handle add or save or load food list
    private boolean considerOptin(AddFoodList result, int choice3) {
        if (choice3 == 1) {
            loadFoodList();
        } else if (choice3 == 2) {
            displayFoodList();
            return true;
        } else if (choice3 == 0) {
            jasonFoodList.setUiFoodList(result.lowfoodlist);
            saveFoodList();
        }
        return false;
    }

    // EFFECTS: display new food list
    private ChooseOption getChooseOption(double dailyCalories) {
        String[] options = {"Gain Weight", "Lose Weight"};
        int choice = JOptionPane.showOptionDialog(this,
                "Your approximately daily calories: " + dailyCalories,
                "Weight Management Options",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);
        ChooseOption option = new ChooseOption(options, choice);
        return option;
    }

    // MODIFIES: this
    // EFFECTS: set the panel of the add food option
    private static class ChooseOption {
        public final String[] options;
        public final int choice;

        public ChooseOption(String[] options, int choice) {
            this.options = options;
            this.choice = choice;
        }
    }

    private void displayFoodList() {
        StringBuilder loadedThings = new StringBuilder("Loaded Food List:\n");
        List<Food> thingies = jasonFoodList.getThingies();
        for (Food t : thingies) {
            loadedThings.append("Food Name: ").append(t.getName()).append("\t");
            loadedThings.append("Food Calories: ").append(t.getCaloriesOfFood()).append("\t");
            loadedThings.append("Food Price: ").append(t.getPrice()).append("\n");
        }
        JTextArea textArea = new JTextArea(loadedThings.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Show the loaded food list in a scrollable dialog
        JOptionPane.showMessageDialog(this, scrollPane, "Loaded Food List",
                JOptionPane.PLAIN_MESSAGE);

        return;
    }

    // EFFECTS: load the food list
    private void loadFoodList() {
        try {
            jasonFoodList = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // save the food list
    private void saveFoodList() {
        try {
            jsonWriter.open();
            jsonWriter.write(jasonFoodList);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: add new food in low calories of food list
    private int lowFoodList(JTextField result2, JTextField result21, JTextField result22, List<Food> result) {
        String foodName = result2.getText();
        double foodPrice = Double.parseDouble(result21.getText());
        double foodCalories = Double.parseDouble(result22.getText());

        // Create a new Food object
        Food newFood = new Food(foodName, foodCalories, foodPrice);
        listFoodDataBase1.addFoodToList(newFood);
        result.add(newFood);

        StringBuilder updatedFoodListText = new StringBuilder("Updated Food List:\n");

        for (Food food : result) {
            updatedFoodListText.append("Food Name: ").append(food.getName()).append("\t");
            updatedFoodListText.append("Food Calories: ")
                    .append(food.getCaloriesOfFood()).append("\t");
            updatedFoodListText.append("Food Price: ").append(food.getPrice()).append("\n");
        }

        JFrame f = new JFrame();
        String[] options3 = {"Save", "Load", "Print", "Add"};
        int choice3 = JOptionPane.showOptionDialog(f, updatedFoodListText
                        + "\nDo you want to:", "Food Added", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, options3, options3[0]);
        return choice3;
    }

    // EFFECTS: get added food list
    private static AddFoodPanel2 getAddFoodPanel2() {
        JPanel addFoodPanel = new JPanel();
        addFoodPanel.setLayout(new GridLayout(3, 2));

        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField caloriesField = new JTextField();

        addFoodPanel.add(new JLabel("Enter Food Name:"));
        addFoodPanel.add(nameField);
        addFoodPanel.add(new JLabel("Enter Food Price:"));
        addFoodPanel.add(priceField);
        addFoodPanel.add(new JLabel("Enter Food Calories:"));
        addFoodPanel.add(caloriesField);
        AddFoodPanel2 result2 = new AddFoodPanel2(addFoodPanel, nameField, priceField, caloriesField);
        return result2;
    }

    // Modifies: this
    // EFFECTS:  set the panel for add food
    private static class AddFoodPanel2 {
        public final JPanel addFoodPanel;
        public final JTextField nameField;
        public final JTextField priceField;
        public final JTextField caloriesField;

        public AddFoodPanel2(JPanel addFoodPanel, JTextField nameField,
                             JTextField priceField, JTextField caloriesField) {
            this.addFoodPanel = addFoodPanel;
            this.nameField = nameField;
            this.priceField = priceField;
            this.caloriesField = caloriesField;
        }
    }

    // EFFECTS: get added food list
    private AddFoodList getAddFoodList(String[] options) {
        StringBuilder foodListText = new StringBuilder("Here's Your Recommended Food List:\n");
        List<Food> lowfoodlist = listFoodDataBase1.getlowCaloriesFoood();
        for (Food f : lowfoodlist) {
            displaysFood(foodListText, f);
        }

        String[] options2 = {"Save", "Load", "Add"};
        int choice2 = JOptionPane.showOptionDialog(this,
                foodListText.toString(),
                "Weight Management Options",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options2,
                options[0]);
        AddFoodList result = new AddFoodList(lowfoodlist, choice2);
        return result;
    }

    private static class AddFoodList {
        public final List<Food> lowfoodlist;
        public final int choice2;

        public AddFoodList(List<Food> lowfoodlist, int choice2) {
            this.lowfoodlist = lowfoodlist;
            this.choice2 = choice2;
        }
    }

    // EFFECTS: choose to gain or lose weight based on calories
    private void gainWeightOption(double dailyCalories, String[] options) {
        dailyCalories += 500;

        JOptionPane.showMessageDialog(this,
                "Your suggested calories intake: " + dailyCalories);


        StringBuilder foodListText = new StringBuilder("Here's Your Recommended Food List:\n");
        List<Food> foodList = listFoodDataBase1.gethighCaloriesFoood();
        for (Food f : foodList) {
            displaysFood(foodListText, f);
        }

        String[] options2 = {"Save", "Load", "Add"};
        int choice2 = JOptionPane.showOptionDialog(this,
                foodListText.toString(),
                "Weight Management Options",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options2,
                options[0]);

        setFoodList(foodList, choice2);
    }

    // EFFECTS: set the food list
    private void setFoodList(List<Food> foodList, int choice2) {
        if (choice2 == 0) {
            jasonFoodList.setUiFoodList(foodList);
            saveFoodList();
        } else if (choice2 == 1) {
            loadFoodList();
        } else if (choice2 == 2) {
            AddFoodPanel result = getAddFoodPanel();
            while (true) {
                int addFoodResult = JOptionPane.showConfirmDialog(this, result.addFoodPanel,
                        "Add Food", JOptionPane.OK_CANCEL_OPTION);

                if (addFoodResult == JOptionPane.OK_OPTION) {
                    if (handleGainWeight(foodList, result)) {
                        break;
                    }
                }
            }


        }
    }


    // EFFECTS: handle the food list and give high calories food list if user choose gain weight
    private boolean handleGainWeight(List<Food> foodList, AddFoodPanel result) {
        try {
            int choice3 = lowFoodList(result.nameField, result.priceField, result.caloriesField, foodList);
            if (choice3 == 0) {
                jasonFoodList.setUiFoodList(foodList);
                saveFoodList();
                return true;
            } else if (choice3 == 1) {
                loadFoodList();
            } else if (choice3 == 2) {
                displayFoodList();
                return true;

            } else if (choice3 == 3) {
                //
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Invalid input. Please enter valid numbers for price and calories.");
            return true;
        }
        return false;
    }

    // EFFECTS: display the food list
    private static void displaysFood(StringBuilder foodListText, Food f) {
        String foodName = f.getName();
        double foodCal = f.getCaloriesOfFood();
        double foodPrice = f.getPrice();

        foodListText.append("Food Name: ").append(foodName).append("\t");
        foodListText.append("Food Calories: ").append(foodCal).append("\t");
        foodListText.append("Food Price: ").append(foodPrice).append("\n");
    }

    // EFFECTS: get the food list of added
    private static AddFoodPanel getAddFoodPanel() {
        JPanel addFoodPanel = new JPanel();
        addFoodPanel.setLayout(new GridLayout(3, 2));

        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField caloriesField = new JTextField();

        addFoodPanel.add(new JLabel("Enter Food Name:"));
        addFoodPanel.add(nameField);
        addFoodPanel.add(new JLabel("Enter Food Price:"));
        addFoodPanel.add(priceField);
        addFoodPanel.add(new JLabel("Enter Food Calories:"));
        addFoodPanel.add(caloriesField);
        AddFoodPanel result = new AddFoodPanel(addFoodPanel, nameField, priceField, caloriesField);
        return result;
    }

    // MODIFIES: this
    // EFFECTS: set the food panel
    private static class AddFoodPanel {
        public final JPanel addFoodPanel;
        public final JTextField nameField;
        public final JTextField priceField;
        public final JTextField caloriesField;

        public AddFoodPanel(JPanel addFoodPanel, JTextField nameField, 
                            JTextField priceField, JTextField caloriesField) {
            this.addFoodPanel = addFoodPanel;
            this.nameField = nameField;
            this.priceField = priceField;
            this.caloriesField = caloriesField;
        }
    }

    // EFFECTS: print of events after user exit
    private void printAllEventsToConsole() {
        System.out.println("All Events:");

        for (Event event : eventLog.getInstance()) {
            System.out.println(event.toString());
        }
    }

}