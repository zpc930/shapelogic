package org.shapelogic.scripting;

import junit.framework.TestCase;

import org.shapelogic.calculation.NamedCalcFixed;
import org.shapelogic.calculation.QueryCalc;
import org.shapelogic.calculation.RootMap;
import org.shapelogic.mathematics.NaturalNumberStream;
import org.shapelogic.streams.NumberedStream;
import org.shapelogic.streams.Stream;

/** Test NamedCalcFixed. <br />
 * 
 * Note that this is living in a different package then the original.
 * 
 * @author Sami Badawi
 *
 */
public class NamedCalcFixedTest extends TestCase {
	
	/** Tests that need JDK 1.6. */ 
	public boolean shouldRun() {
		return false; //XXX Make better test for presence of JDK 1.6.
	}

	public void testGetValue() {
		NamedCalcFixed<Integer> calc = new NamedCalcFixed<Integer>("number42",42);
		assertEquals(new Integer(42), calc.getValue());
	}
	
	public void testGetValueInRootMap() {
		assertEquals(new Integer(42), RootMap.get("number42"));
	}
	
	public void testGetValueInRootMap42() {
		if (!shouldRun())
			return;
		FunctionCalcInvoke<Integer> calc42 = new FunctionCalcInvoke<Integer>(
				"getValue42", "def getValue42 = {number42};"
			); 
		assertEquals(new Integer(42), calc42.getValue());
	}
	
	/** The value survives in the RootMap */
	public void testGetValueInRootMap42WithQueryCalc() {
		assertEquals(new Integer(42), QueryCalc.getInstance().get("number42", RootMap.getInstance()));
	}

	public void testGetNaturalNumbers() {
		NamedCalcFixed<NumberedStream<Integer>> calc = 
			new NamedCalcFixed<NumberedStream<Integer>>("naturalNumbers",new NaturalNumberStream());
		assertNotNull(RootMap.get("naturalNumbers"));
		NumberedStream<Integer> result = calc.getValue();
		assertEquals(new Integer(0), result.next());
	}
	
	public void testGetValueInNaturalNumbers() {
		if (!shouldRun())
			return;
		FunctionCalcInvoke<Integer> calc42 = new FunctionCalcInvoke<Integer>(
				"naturalNumbersFunction", "def naturalNumbersFunction = {naturalNumbers.next()};"
			); 
		assertEquals(new Integer(1), calc42.getValue());
	}

	public void testGetValueInNaturalNumbers2() {
		if (!shouldRun())
			return;
		FunctionCalcInvoke<Integer> calc42 = new FunctionCalcInvoke<Integer>(
				"naturalNumbersFunction", "def naturalNumbersFunction = {naturalNumbers.next()};"
			); 
		assertEquals(new Integer(2), calc42.getValue());
	}

	/** The value survives in the RootMap 
	 * This shows that there is a problem with storing streams in the RootMap, 
	 * since there are interpreted as a value, and evaluated.
	 * */
	public void testGetValueInNaturalNumbersWithQueryCalc() {
		NamedCalcFixed<NumberedStream<Integer>> calc = 
			new NamedCalcFixed<NumberedStream<Integer>>("naturalNumbers",new NaturalNumberStream());
		assertNotNull(RootMap.get("naturalNumbers"));
		assertTrue(RootMap.get("naturalNumbers") instanceof Stream);
		assertTrue(QueryCalc.getInstance().get("naturalNumbers", RootMap.getInstance() ) instanceof Stream);
	}

}
