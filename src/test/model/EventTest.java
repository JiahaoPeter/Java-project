package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Event class
 */
public class EventTest {
	private Event e;
	private Date d;
    private Event e1;
    private Event e2;
	
	//NOTE: these tests might fail if time at which line (2) below is executed
	//is different from time that line (1) is executed.  Lines (1) and (2) must
	//run in same millisecond for this test to make sense and pass.
	
	@BeforeEach
	public void runBefore() {
		e = new Event("Sensor open at door");   // (1)
		d = Calendar.getInstance().getTime();   // (2)
        e1 = new Event("Sensor open at door");
        e2 = new Event("Sensor open at door");
    }
	
	@Test
	public void testEvent() {
		assertEquals("Sensor open at door", e.getDescription());
		assertEquals(d, e.getDate());
	}

	@Test
	public void testToString() {
		assertEquals(d.toString() + "\n" + "Sensor open at door", e.toString());
	}

    @Test
    public void testHashCode() {
        assertEquals(e1.hashCode(), e2.hashCode()); // Since they are equal, hash codes should match
    }

    @Test
    public void testEquals() {
        assertTrue(e1.equals(e2));
    }

    @Test
    public void testNotEquals() {
        Event differentEvent = new Event("Different Event");
        assertFalse(e1.equals(differentEvent));
        assertFalse(e1.equals(null));
        assertFalse(e1.equals(d));
    }



}