package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserProfileTest {
    private UserProfile testUserProfile;
    private UserProfile testUserProfile2;
    private UserProfile testUserProfile3;


    @BeforeEach
    void runbefore() {
        testUserProfile = new UserProfile(185, 75, "male", 19);
        testUserProfile2 = new UserProfile(168, 49, "female", 19);
        testUserProfile3 = new UserProfile(178, 85, "male", 18);

    }

    @Test
    void testConstructor() {
        assertEquals(185, testUserProfile.getHeight());
        assertEquals(75, testUserProfile.getWeight());
        assertEquals("male", testUserProfile.getSexGender());
        assertEquals(19, testUserProfile.getAge());
    }

    @Test
    void testCaloriesCalculate() {
        assertEquals(1889.3, testUserProfile.calculateDailyCaloires
                (185, 75, "male", 19));
        assertEquals(1338.5000000000002, testUserProfile2.calculateDailyCaloires
                (168, 49, "female", 19));
        assertEquals(1991.3, testUserProfile3.calculateDailyCaloires
                (178, 85, "male", 19));
    }
}