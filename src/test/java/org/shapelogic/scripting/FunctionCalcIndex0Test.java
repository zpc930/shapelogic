package org.shapelogic.scripting;

import org.shapelogic.calculation.RootMap;
import org.shapelogic.mathematics.NaturalNumberStream;
import org.shapelogic.streams.ListStream;

import junit.framework.TestCase;

/** Test FunctionCalcIndex0.
 * 
 * @author Sami Badawi
 *
 */
public class FunctionCalcIndex0Test extends TestCase {
	
	public void testCreationOfCalcIndex0() {
		FunctionCalcIndex0<Integer> functionTransform = 
			new FunctionCalcIndex0<Integer>("times2_FUNCTION_","def times2_FUNCTION_ = {it*2};");
		assertNotNull(functionTransform);
		assertEquals(new Integer(2), functionTransform.invoke(1));
	}

	/** You can access static method, but you cannot do imports inside the methods. 
	 * Therefore classes need full path name. This is still a lot better than JEXL.
	 */
	public void testAccessToNamedStream() {
		RootMap.put("naturalNumbers", new NaturalNumberStream(5));
		final ListStream<Integer> naturalNumbers = (ListStream<Integer>) RootMap.get("naturalNumbers");
		
		FunctionCalcIndex0<Integer> functionTransform = 
			new FunctionCalcIndex0<Integer>("naturalNumbers_FUNCTION_",
					"def naturalNumbers_FUNCTION_ = {org.shapelogic.calculation.RootMap.get('naturalNumbers').get(it)};");
		assertNotNull(functionTransform);
		assertEquals(new Integer(2), functionTransform.invoke(2));
	}

	public void testAccessToNamedStream2() {
		RootMap.put("naturalNumbers", new NaturalNumberStream(5));
		final ListStream<Integer> naturalNumbers = (ListStream<Integer>) RootMap.get("naturalNumbers");
		
		FunctionCalcIndex0<Integer> functionTransform = 
			new FunctionCalcIndex0<Integer>("evenNumbers_FUNCTION_",
					"def evenNumbers_FUNCTION_ = {org.shapelogic.calculation.RootMap.get('naturalNumbers').get(it) * 2};");
		assertNotNull(functionTransform);
		assertEquals(new Integer(4), functionTransform.invoke(2));
	}

	/** You can do imports before inside the methods. */
	public void testAccessToNamedStream3() {
		RootMap.put("naturalNumbers", new NaturalNumberStream(5));
		final ListStream<Integer> naturalNumbers = (ListStream<Integer>) RootMap.get("naturalNumbers");
		
		FunctionCalcIndex0<Integer> functionTransform = 
			new FunctionCalcIndex0<Integer>("naturalNumbers_FUNCTION_",
					"import org.shapelogic.calculation.RootMap;\n" +
					"def naturalNumbers_FUNCTION_ = {" +
					"RootMap.get('naturalNumbers').get(it)};");
		assertNotNull(functionTransform);
		assertEquals(new Integer(2), functionTransform.invoke(2));
	}
}
