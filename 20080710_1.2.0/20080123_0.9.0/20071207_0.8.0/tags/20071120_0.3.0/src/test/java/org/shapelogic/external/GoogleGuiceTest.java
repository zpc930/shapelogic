package org.shapelogic.external;

import junit.framework.TestCase;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class GoogleGuiceTest extends TestCase {

	static String SLOW_POKE = "I could be slow";
	public void testCreate() throws Exception {
	    Injector injector = Guice.createInjector();
	    String string = injector.getInstance(String.class);
		assertTrue(string instanceof String);
	}

	private static class Dummy {
		String name;
		Dummy(String name) {
			this.name = name;
		}
		Dummy() {
			this(SLOW_POKE);
		}
	}
	
	private static class Boss {
		Dummy worker;
		
		@Inject
		Boss(Dummy worker) {
			this.worker = worker;
		}
	}
	
	private class NumberModule implements Module{

	    public void configure(Binder binder) {

	        binder.bind(Number.class).toInstance(new Double(0));
	    }

	}
	
	public void testCreateDependency() throws Exception {
	    Injector injector = Guice.createInjector();
	    Boss boss = injector.getInstance(Boss.class);
		assertTrue(boss instanceof Boss);
		assertEquals(SLOW_POKE, boss.worker.name);
	}

	public void testCreateBinding() throws Exception {
	    Injector injector = Guice.createInjector( new NumberModule());
	    Number number = injector.getInstance(Number.class);
	    assertTrue(number instanceof Double);
	    assertEquals(0.0, number);
	}
}
