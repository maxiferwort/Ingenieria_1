/*
 * Developed by 10Pines SRL
 * License: 
 * This work is licensed under the 
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License. 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ 
 * or send a letter to Creative Commons, 444 Castro Street, Suite 900, Mountain View, 
 * California, 94041, USA.
 *  
 */
package idiom;

import junit.framework.TestCase;

public class IdiomTest extends TestCase {

    protected CustomerBook customerBook;
    long millisecondsBeforeRunning, millisecondsAfterRunning;

    public void setUp() {
        customerBook = new CustomerBook();
    }

    public void testAddingCustomerShouldNotTakeMoreThan50Milliseconds() {

        countMillisecondsStart();
        customerBook.addCustomerNamed("John Lennon");
        countMillisecondsEnd();

        assertTrue(elapsedTimeLessThan(50));
    }

    public void testRemovingCustomerShouldNotTakeMoreThan100Milliseconds() {
        String paulMcCartney = "Paul McCartney";

        customerBook.addCustomerNamed(paulMcCartney);

        countMillisecondsStart();
        customerBook.removeCustomerNamed(paulMcCartney);
        countMillisecondsEnd();

        assertTrue(elapsedTimeLessThan(100));
    }

    public void testCanNotAddACustomerWithEmptyName() {

        try {
            customerBook.addCustomerNamed("");
            fail();
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), CustomerBook.CUSTOMER_NAME_EMPTY);
            assertTrue(customerBook.isEmpty());
        }
    }

    public void testCanNotRemoveNotAddedCustomers() {

        try {
            customerBook.removeCustomerNamed("John Lennon");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), CustomerBook.INVALID_CUSTOMER_NAME);
            assertEquals(0, customerBook.numberOfCustomers());
        }
    }

    private void resetTimers() {
        millisecondsBeforeRunning = 0;
        millisecondsAfterRunning = 0;
    }

    private void countMillisecondsStart() {
        resetTimers();
        millisecondsBeforeRunning = System.currentTimeMillis();
    }

    private void countMillisecondsEnd() {
        millisecondsAfterRunning = System.currentTimeMillis();;
    }

    private long elapsedTime() {
        return (millisecondsAfterRunning - millisecondsBeforeRunning);
    }

    private boolean elapsedTimeLessThan(long milliseconds) {
        return elapsedTime() < milliseconds;
    }

}
