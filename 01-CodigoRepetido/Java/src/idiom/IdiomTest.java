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

import idiom.CustomerBook;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;

public class IdiomTest extends TestCase {

    protected CustomerBook customerBook;

    public void setUp() {
        customerBook = new CustomerBook();
    }

    public void testAddingCustomerShouldNotTakeMoreThan50Milliseconds() {

        //long millisecondsBeforeRunning = System.currentTimeMillis();
        //customerBook.addCustomerNamed("John Lennon");
        //long millisecondsAfterRunning = System.currentTimeMillis();
        //assertTrue((millisecondsAfterRunning - millisecondsBeforeRunning) < 50);
        
        testTakeMoreThan(50, "John Lennon", "addCustomerNamed");
        

    }

    public void testRemovingCustomerShouldNotTakeMoreThan100Milliseconds() {
        String paulMcCartney = "Paul McCartney";
        customerBook.addCustomerNamed(paulMcCartney);

        //long millisecondsBeforeRunning = System.currentTimeMillis();
        //customerBook.removeCustomerNamed(paulMcCartney);
        //long millisecondsAfterRunning = System.currentTimeMillis();
        //assertTrue((millisecondsAfterRunning - millisecondsBeforeRunning) < 100);
        
        testTakeMoreThan(100, paulMcCartney, "removeCustomerNamed");
        
    }

    private void testTakeMoreThan(long milliseconds, String name, String metodo){
        try {
            Method metodoAEjecutar = customerBook.getClass().getDeclaredMethod(metodo, String.class);

            long millisecondsBeforeRunning = System.currentTimeMillis();
            metodoAEjecutar.invoke(customerBook, name);
            long millisecondsAfterRunning = System.currentTimeMillis();

            assertTrue((millisecondsAfterRunning - millisecondsBeforeRunning) < milliseconds);

        } catch (Exception ex) {
            Logger.getLogger(IdiomTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void testCanNotAddACustomerWithEmptyName() {

        try {
            customerBook.addCustomerNamed("");
            fail();
        } catch (RuntimeException e) {
            //assertEquals(e.getMessage(), CustomerBook.CUSTOMER_NAME_EMPTY);
            assertEqualsObject(e.getMessage(), CustomerBook.CUSTOMER_NAME_EMPTY);
            assertTrue(customerBook.isEmpty());
        }
    }

    public void testCanNotRemoveNotAddedCustomers() {

        try {
            customerBook.removeCustomerNamed("John Lennon");
            fail();
        } catch (IllegalArgumentException e) {
            //assertEquals(e.getMessage(), CustomerBook.INVALID_CUSTOMER_NAME);
            //assertEquals(0, customerBook.numberOfCustomers());
            assertEqualsObject(e.getMessage(), CustomerBook.INVALID_CUSTOMER_NAME);            
            assertEqualsObject(0, customerBook.numberOfCustomers());
        }
    }

    private void assertEqualsObject(Object Object1, Object Object2) {
        assertEquals(Object1, Object2);
    }
     
}
