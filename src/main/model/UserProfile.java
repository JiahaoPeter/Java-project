package model;

// A Class the represent the profile information of the user including height, weight, gender, age.
public class UserProfile {
    private double height;
    private double weight;
    private String sexGender;
    private int age;

    // Required Clause: height,weight,age >0
    // Effect Clause: create a user profile with height, weight, sex, age
    public UserProfile(double height, double weight, String sexGender, int age) {
        this.height = height;
        this.weight = weight;
        this.sexGender = sexGender;
        this.age = age;

    }

    // Required Clause: calculated calories should be grater than 0
    // Effect Clause: calculate the daily calories for a user
    public double calculateDailyCaloires(double height, double weight, String sexGender, int age) {
        double dailyCalories = 0;
        if (sexGender.equals("male")) {
            dailyCalories = 66 + 13.7 * weight + 5 * height - 6.8 * age;
        } else {
            dailyCalories = 655 + 9.6 * weight + 1.8 * height - 4.7 * age;
        }
        return dailyCalories;

    }

    // Effect Clause: return the height of the user
    public double getHeight() {
        return height;
    }

    // Effect Clause: return the weight of the user
    public double getWeight() {
        return weight;
    }


    // Effect Clause: return the sex of the user
    public String getSexGender() {
        return sexGender;
    }

    // // Effect Clause: return the age of the user
    public int getAge() {
        return age;
    }


}

