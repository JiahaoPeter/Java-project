package ui;

import model.Food;
import model.ListFoodDataBase;
import model.UserProfile;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class FoodieApp {
    private double weight;
    private double height;
    private String sex;
    private int age;
    private ListFoodDataBase listFoodDataBase;
    private String foodName;
    private double foodCal;
    private double foodPrice;
    private String foodName2;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/workroom.json";

    // Effects: run the teller application
    public FoodieApp() {
        runApp();
    }

    // Modifies: this
    // Effects: run the teller application
    private void runApp() {
        initilized();
        Makeuserprofile result = getMakeUserProfile();
        double basicMetabolism = result.userProfile.calculateDailyCaloires(weight, height, sex, age);
        System.out.println("your approximately basic metabolism:" + basicMetabolism);


        //Display menu of options for user
        String command = null;
        while (result.validInput) {
            displaymenu();
            command = result.scanner.next();
            command = command.toLowerCase();

            if (command.equals("g")) {
                basicMetabolism += 500;
                System.out.println("Suggested caloric intake:" + basicMetabolism);
                getHighCalFoods();
                break;
            } else if (command.equals("l")) {
                basicMetabolism -= 500;
                System.out.println("Suggested caloric intake:" + basicMetabolism);
                getLowCalFoods();
                break;
            } else {
                System.out.println("Selection not valid...");
            }

        }

    }

    private void initilized() {
        listFoodDataBase = new ListFoodDataBase();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    //MODIFIES: this
    //EFFECTS: create a user profile with weight, height, sex, and age
    private Makeuserprofile getMakeUserProfile() {
        boolean validInput = true;
        Scanner scanner = new Scanner((System.in));
        enterWeight(validInput, scanner);

        enterHeight(validInput, scanner);


        enterGender(validInput, scanner);

        enterAge(validInput, scanner);

        //create a UserProfile object with given weight, height, sex and calculate calories
        UserProfile userProfile;
        userProfile = new UserProfile(weight, height, sex, age);
        Makeuserprofile result = new Makeuserprofile(validInput, scanner, userProfile);
        return result;
    }

    //MODIFIES: this
    //EFFECTS: create a user profile with weight, height, sex, and age
    private static class Makeuserprofile {
        public final boolean validInput;
        public final Scanner scanner;
        public final UserProfile userProfile;

        public Makeuserprofile(boolean validInput, Scanner scanner, UserProfile userProfile) {
            this.validInput = validInput;
            this.scanner = scanner;
            this.userProfile = userProfile;
        }
    }


    // Effects: display manu of options for user
    private void displaymenu2() {
        System.out.println("\nDo you have allergic of food in the food list?");
        System.out.println("\ty -> Yes");
        System.out.println("\tn -> No and Save my Food List");
    }

    // EFFECTS: get a list of Food with calories less tha 260
    private void getLowCalFoods() {
        getLowFoodList();

    }

    // EFFECTS: create a list of low calories food list
    private void getLowFoodList() {
        List<Food> foodList = getFoodList();
        displayFoodList(foodList);
        String command = null;
        Scanner scanner = new Scanner((System.in));
        boolean validInput = true;
        while (true) {
            while (validInput) {
                displaymanu3();
                command = scanner.next();
                command = command.toLowerCase();
                if (command.equals("n")) {
                    validInput = displayFinalFoodList(foodList, validInput);
                    listFoodDataBase.setUiFoodList(foodList);
                    saveWorkRoom();
                } else if (command.equals("y")) {
                    checkYesOption(foodList, scanner);
                } else if (command.equals("l")) {
                    loadWorkRoom();
                } else if (command.equals("p")) {
                    printThingies();
                }

            }

        }
    }

    private static void displaymanu3() {
        System.out.println("\nDo you want to add more your favourite food in your food list?");
        System.out.println("\ty -> Yes");
        System.out.println("\tn -> No and Save my Food List");
        System.out.println("\tl -> load work room from file");
        System.out.println("\tp -> print loaded things");
    }

    // EFFECTS: display your food list with name, price, and calories
    private static boolean displayFinalFoodList(List<Food> foodList, boolean validInput) {
        System.out.println("Sure! Here is your final food list:");
        for (Food f : foodList) {
            printFood(f);
            validInput = false;
        }
        System.out.println("Good bye!!");
        return validInput;
    }

    // EFFECTS: display your food list with name, price, and calories
    private static void displayFoodList(List<Food> foodList) {
        for (Food f : foodList) {
            String foodName = f.getName();
            double foodCal = f.getCaloriesOfFood();
            double foodPrice = f.getPrice();
            System.out.println("Recommended Food List:" + "Food Name:" + foodName + "    " + "Food Calories:"
                    + foodCal + "     " + "Food Price:" + foodPrice);
        }
    }

    // EFFECTS: return your food list with name, price, and calories
    private static List<Food> getFoodList() {
        ListFoodDataBase listFoodDataBase1 = new ListFoodDataBase();
        listFoodDataBase1.initializeFood();
        List<Food> foodList = listFoodDataBase1.getlowCaloriesFoood();
        return foodList;
    }

    // EFFECTS: add your favourite food into the food list
    private void checkYesOption(List<Food> foodList, Scanner scanner) {
        enterfoodname(scanner);

        enterfoodCal(scanner);

        enterfoodPrice(scanner);
        Food favouriteFood = new Food(foodName, foodCal, foodPrice);
        foodList.add(favouriteFood);
        System.out.println("Here is your new food list:");
        for (Food f : foodList) {
            printFood(f);
        }
    }

    // EFFECTS: Enter the food price of your input and be recorded
    private void enterfoodPrice(Scanner scanner) {
        while (true) {
            System.out.println("Enter your favourite food's price:");
            String usrInput = scanner.nextLine();
            if (usrInput.matches("\\d+.*\\d*")) {
                this.foodCal = Double.parseDouble(usrInput);
                break;
            }
            System.out.println("invalid input, please enter a valid double for food name");
        }
    }

    // EFFECTS: enter the food calories of your input and be recorded
    private void enterfoodCal(Scanner scanner) {
        while (true) {
            System.out.println("Enter your favourite food's calories per 100g:");
            String usrInput = scanner.nextLine();
            if (usrInput.matches("\\d+.*\\d*")) {
                this.foodCal = Double.parseDouble(usrInput);
                break;
            }
            System.out.println("invalid input, please enter a valid double for food name");
        }
    }

    // EFFECTS: enter the food name of your input and be recorded
    private void enterfoodname(Scanner scanner) {
        while (true) {
            System.out.println("Enter your favourite food's name:");
            foodName = scanner.next();
            foodName.toLowerCase();
            if (foodName.matches("[a-z]+")) {
                break;
            }
            System.out.println("invalid input, please enter a valid string for food name");
        }
    }

    // MODIFIES: this
    // EFFECTS: get a list of Food with calories greater tha 260
    private void getHighCalFoods() {
        List<Food> foodList = getHighFoodList2();
        String command = null;
        Scanner scanner = new Scanner((System.in));
        boolean validInput = true;
        while (true) {
            while (validInput) {
                command = displaymenufinal(scanner);
                if (command.equals("n")) {
                    validInput = displayFinalFoodList(foodList, validInput);
                    listFoodDataBase.setUiFoodList(foodList);
                    saveWorkRoom();
                } else if (command.equals("y")) {
                    foodInfo(foodList, scanner);
                    for (Food f : foodList) {
                        printFood(f);
                    }

                } else if (command.equals("l")) {
                    loadWorkRoom();
                } else if (command.equals("p")) {
                    printThingies();
                }

            }
        }

    }

    private static void printFood(Food f) {
        System.out.println("Recommended Food List:" + "Food Name:" + f.getName() + "   "
                + "Food Calories:"
                + f.getCaloriesOfFood() + "     " + "Food Price:" + f.getPrice());
    }

    private void foodInfo(List<Food> foodList, Scanner scanner) {
        enterfoodnamehigh(scanner);

        enterfoodcalhigh(scanner);

        enterfoodpricehigh(scanner);

        addFoodList(foodList);
        System.out.println("Here is your new food list:");
    }

    // EFFECTS: add food to the food list
    private void addFoodList(List<Food> foodList) {
        Food favouriteFood = new Food(foodName2, foodCal, foodPrice);
        foodList.add(favouriteFood);
    }

    // EFFECTS: display the manu options for user to add food
    private static String displaymenufinal(Scanner scanner) {
        String command;
        System.out.println("\nDo you want to add more your favourite food in your food list?");
        System.out.println("\ty -> Yes");
        System.out.println("\tn -> No and Save my Food List");
        System.out.println("\tl -> load work room from file");
        System.out.println("\tp -> print loaded things");
        command = scanner.next();
        command = command.toLowerCase();
        return command;
    }

    // EFFECTS: return the food list with high calories
    private static List<Food> getHighFoodList2() {
        ListFoodDataBase listFoodDataBase1 = new ListFoodDataBase();
        listFoodDataBase1.initializeFood();
        List<Food> foodList = listFoodDataBase1.gethighCaloriesFoood();
        displayFoodList(foodList);
        return foodList;
    }


    // EFFECTS: enter the food price of your input and be recorded
    private void enterfoodpricehigh(Scanner scanner) {
        while (true) {
            System.out.println("Enter your favourite food's calories per 100g:");
            String usrInput = scanner.nextLine();
            if (usrInput.matches("\\d+.*\\d*")) {
                this.foodPrice = Double.parseDouble(usrInput);
                break;
            }
            System.out.println("invalid input, please enter a valid double for food price");
        }
    }


    // EFFECTS: enter the food calories of your input and be recorded
    private void enterfoodcalhigh(Scanner scanner) {
        while (true) {
            System.out.println("Enter your favourite food's calories per 100g:");
            String usrInput = scanner.nextLine();
            if (usrInput.matches("\\d+.*\\d*")) {
                this.foodCal = Double.parseDouble(usrInput);
                break;
            }
            System.out.println("invalid input, please enter a valid double for food calories");
        }
    }


    // EFFECTS: enter the food name of your input and be recorded
    private void enterfoodnamehigh(Scanner scanner) {
        while (true) {
            System.out.println("Enter your favourite food's name:");
            foodName2 = scanner.next();
            foodName2.toLowerCase();
            if (foodName2.matches("[a-z]+")) {
                break;
            }
            System.out.println("invalid input, please enter a valid string for food name");
        }
    }

    // Effect: Enter the age of the user
    private void enterAge(boolean validInput, Scanner scanner) {
        while (validInput) {
            System.out.println("Enter Your Age Expected an Integer");
            if (scanner.hasNextInt()) {
                this.age = scanner.nextInt();
                break;
            }
            System.out.println("invalid input, please enter a valid integer for age");
        }
    }

    // Effect: Enter the gender of the user
    private void enterGender(boolean validInput, Scanner scanner) {
        while (validInput) {
            System.out.println("Enter Your Sex Identity (male/female)");
            this.sex = scanner.next();
            sex.toLowerCase();
            if (sex.equals("male") || sex.equals("female")) {
                break;
            }
            System.out.println("invalid input, enter male or female");
        }
    }

    // Effect: Enter the height of the user
    private void enterHeight(boolean validInput, Scanner scanner) {
        while (validInput) {
            System.out.println("Enter Your Height(cm) Expected a double");
            this.height = scanner.nextDouble();
            if (height >= 100 && height <= 300) {
                break;
            }
            System.out.println("unreasonable input, careful 1your height should in cm unit");
        }
    }

    // Effect: Enter the height of the user
    private void enterWeight(boolean validInput, Scanner scanner) {
        while (validInput) {
            System.out.print("\nEnter Your Body Weight(Kg) Expected a double \n");
            this.weight = scanner.nextDouble();
            if (weight >= 30 && weight <= 280) {
                break;
            }
            System.out.println("Your Input seems too big or too small, please reenter");

        }
    }

    // Effect: display menu options for user
    private void displaymenu() {
        System.out.println("\nTarget Weight option:");
        System.out.println("\tg -> gain weight");
        System.out.println("\tl -> lose weight");
        System.out.println("\ta -> load work room from file");
    }

    // EFFECTS: saves the workroom to file
    private void saveWorkRoom() {
        try {
            jsonWriter.open();
            jsonWriter.write(listFoodDataBase);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadWorkRoom() {
        try {
            listFoodDataBase = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: prints all the thingies in workroom to the console
    private void printThingies() {
        List<Food> thingies = listFoodDataBase.getThingies();
        for (Food t : thingies) {
            System.out.println("loaded food list:" + "Food Name:" + t.getName() + "   "
                    + "Food Calories:"
                    + t.getCaloriesOfFood() + "     " + "Food Price:" + t.getPrice());
        }
    }


}
