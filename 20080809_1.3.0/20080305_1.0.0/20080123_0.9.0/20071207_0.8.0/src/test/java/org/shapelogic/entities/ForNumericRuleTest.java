package org.shapelogic.entities;

import java.util.*;
import javax.persistence.*;

import org.shapelogic.entities.NumericRule;

import junit.framework.TestCase;

/** This test NumericRule in connection with Hibernate.
 * 
 * @author Sami Badawi
 *
 */
public class ForNumericRuleTest extends TestCase 
{

	private boolean runSlowTests = false;

	public void test() {
		if (!runSlowTests ) 
			return;
		
		    // Start EntityManagerFactory
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("helloworld");
		
		// First unit of work
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		String OHName = "Sami";
		NumericRule message = new NumericRule("Sami","number", "person", "number",1d, "SimpleNumericTask");
		em.persist(message);
		
		NumericRule message2 = new NumericRule("Sami","age", "person", "age",45d, "SimpleNumericTask");
		em.persist(message2);
		
		tx.commit();
		em.close();
		
		// Second unit of work
		EntityManager newEm = emf.createEntityManager();
		EntityTransaction newTx = newEm.getTransaction();
		newTx.begin();
		
		displayResult(newEm, "select m from NumericRule m order by m.id asc",2);
		
		displayResult(newEm, "select m from NumericRule m where m.name = 'age' order by m.id asc",1);
		
		newTx.commit();
		newEm.close();
		
		// Shutting down the application
		    emf.close();
	}

	private void displayResult(EntityManager newEm, String query, int expectedCount) {
		List messages =
	        newEm.createQuery(query).getResultList();
	
	    System.out.println( "Query: " + query);
	    System.out.println( messages.size() + " message(s) found:" );
	    assertEquals(expectedCount, messages.size());
	    for (Object m : messages) {
	      NumericRule loadedMsg = (NumericRule) m;
	      System.out.println(loadedMsg);
	    }
	}

}
