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
package main;

import java.lang.reflect.InvocationTargetException;

import junit.framework.TestCase;

public class IdiomTest extends TestCase {

	private static final String JOHN_LENNON = "John Lennon";
	private static final String PAUL_MC_CARTNEY = "Paul McCartney";
	protected CustomerBook customerBook;

	public void setUp() {
		customerBook = new CustomerBook();
	}

	public void testAddingCustomerShouldNotTakeMoreThan50Milliseconds() {
		runCountingMilis(50, new Runnable() {
			@Override
			public void run() {
				customerBook.addCustomerNamed(JOHN_LENNON);
			}
		});
	}

	public void testRemovingCustomerShouldNotTakeMoreThan100Milliseconds()
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		customerBook.addCustomerNamed(PAUL_MC_CARTNEY);
		runCountingMilis(100, new Runnable() {
			@Override
			public void run() {
				customerBook.removeCustomerNamed(PAUL_MC_CARTNEY);
			}
		});
	}

	public void testCanNotAddACustomerWithEmptyName() {
		testWithTry(new TryTest(new Runnable() {
			@Override
			public void run() {
				customerBook.addCustomerNamed("");
			}
		}, RuntimeException.class) {
			@Override
			protected void assertion(Exception exception) {
				assertEquals(exception.getMessage(),
						CustomerBook.CUSTOMER_NAME_EMPTY);
				assertTrue(customerBook.isEmpty());
			}
		});
	}

	public void testCanNotRemoveNotAddedCustomers() {
		testWithTry(new TryTest(new Runnable() {
			@Override
			public void run() {
				customerBook.removeCustomerNamed(JOHN_LENNON);
			}
		}, IllegalArgumentException.class) {
			@Override
			protected void assertion(Exception exception) {
				assertEquals(exception.getMessage(),
						CustomerBook.INVALID_CUSTOMER_NAME);
				assertTrue(customerBook.isEmpty());
			}
		});
	}
	
	public void runCountingMilis(int milis, Runnable runnable) {
		long millisecondsBeforeRunning = System.currentTimeMillis();
		runnable.run();
		long millisecondsAfterRunning = System.currentTimeMillis();
		assertTrue((millisecondsAfterRunning - millisecondsBeforeRunning) < milis);
	}

	public void testWithTry(TryTest test) {
		try {
			test.run();
			fail();
		} catch (Exception e) {
			test.doAssert(e);
		}
	}

	public abstract class TryTest implements Runnable {

		private Runnable before;

		private Class<? extends Throwable> clazz;

		protected abstract void assertion(Exception exception);

		public final void doAssert(Exception exception) {
			if (clazz.isInstance(exception)) {
				assertion(exception);
			}
		}

		public TryTest(Runnable before, Class clazz) {
			this.before = before;
			this.clazz = clazz;
		}

		public void run() {
			before.run();
		}
	}

}
