package org.shapelogic.streams;

import org.shapelogic.calculation.Calc1;
import org.shapelogic.calculation.RootMap;
import org.shapelogic.mathematics.NaturalNumberStream;
import org.shapelogic.predicate.BinaryEqualPredicate;
import org.shapelogic.predicate.BinaryPredicate;
import org.shapelogic.util.Constants;

/** Add tests that are specific for Scripting implementations of streams.
 * 
 * @author Sami Badawi
 *
 */
abstract public class AbstractScriptingListStreamTests extends AbstractListStreamTests {

	protected String _language;
	protected String _filterFunctionExpressionEven;
	protected String _filterFunctionExpressionThird;

	/** Take a named input stream and make a filter based on a scripting function.
	 * <br />
	 * So this should only be run for the Scripting function based tests.
	 */
	public void testNamedFilterStreamWithBinaryPredicateInput() {
		if (_disableTests) return;
		if (_filterFunctionExpressionEven==null)
			return;
		NaturalNumberStream naturalNumbersTo3 = new NaturalNumberStream(3);
		String inputStreamName = "naturalNumbersTo3";
		RootMap.put(inputStreamName, naturalNumbersTo3);
		String ruleName = "EvenNumbers";
		BinaryPredicate<Integer, Integer> binaryPredicate = new BinaryEqualPredicate();
		Integer compareObject = 2;
		ListStream<Boolean> stream = StreamFactory.createListStream0(ruleName, 
				inputStreamName, _filterFunctionExpressionEven, 
				binaryPredicate, compareObject, _language);
		assertEquals(Boolean.FALSE,stream.next());
		assertEquals(Boolean.TRUE,stream.next());
		assertEquals(Boolean.FALSE,stream.next());
		assertEquals(Boolean.FALSE,stream.next());
//		assertFalse(stream.hasNext()); //XXX should work
//		assertNull(stream.next()); //XXX should work
		assertEquals(Boolean.FALSE,stream.next());
		assertEquals(Boolean.FALSE,stream.next());
	}
	
	/** Take a named input stream and make a filter based on a scripting function.
	 * <br />
	 * So this should only be run for the Scripting function based tests.
	 */
	public void testNamedFilterStream() {
		if (_disableTests) return;
		if (_filterFunctionExpressionEven==null)
			return;
		NaturalNumberStream naturalNumbersTo3 = new NaturalNumberStream(3);
		String inputStreamName = "naturalNumbersTo3";
		RootMap.put(inputStreamName, naturalNumbersTo3);
		String ruleName = "EvenNumbers";
		Integer compareObject = 2;
		ListStream<Boolean> stream = StreamFactory.createListStream0(ruleName, 
				inputStreamName, _filterFunctionExpressionEven, "==", compareObject, _language);
		assertEquals(Boolean.FALSE,stream.next());
		assertEquals(Boolean.TRUE,stream.next());
		assertEquals(Boolean.FALSE,stream.next());
		assertEquals(Boolean.FALSE,stream.next());
//		assertFalse(stream.hasNext()); //XXX should work
//		assertNull(stream.next()); //XXX should work
		assertEquals(Boolean.FALSE,stream.next());
		assertEquals(Boolean.FALSE,stream.next());
	}

	public void testAddListStream0() {
		if (_disableTests) return;
		if (_filterFunctionExpressionEven==null)
			return;
		NaturalNumberStream naturalNumbersTo3 = new NaturalNumberStream(3);
		String inputStreamName = "naturalNumbersTo3";
		RootMap.put(inputStreamName, naturalNumbersTo3);
		String ruleName = "XOrRule";
		String partEvenName = "EvenNumbers";
		String partThirdName = "ThirdNumbers";
		Integer compareObject = 2;
		RootMap.getMap().remove(ruleName);
		ListStream<Boolean> stream = StreamFactory.addListStream0(ruleName,partEvenName, 
				inputStreamName, _filterFunctionExpressionEven, "==", compareObject, _language);
		StreamFactory.addListStream0(ruleName, partThirdName,
				inputStreamName, _filterFunctionExpressionThird, "==", 8, _language);
		assertEquals(Boolean.FALSE,stream.next());
		assertEquals(Boolean.TRUE,stream.next());
		assertEquals(Boolean.FALSE,stream.next());
		assertEquals(Boolean.FALSE,stream.next());
//		assertFalse(stream.hasNext()); //XXX should work
//		assertNull(stream.next()); //XXX should work
		assertEquals(Boolean.FALSE,stream.next());
		assertEquals(Boolean.FALSE,stream.next());
	}

	
	
	/** Take a named input stream and make a filter based on a scripting function.
	 * <br />
	 * So this should only be run for the Scripting function based tests.
	 */
	public void testMakeFunctionBooleanCalc1() {
		if (_disableTests) return;
		if (_filterFunctionExpressionEven==null)
			return;
		String ruleName = "EvenNumbers";
		Integer compareObject = 2;
		Calc1<Integer, Boolean> stream = StreamFactory.makeFunctionBooleanCalc1(
				_filterFunctionExpressionEven, "==", compareObject, _language, ruleName + Constants.FUNCTION_NAME_SUFFIX);
		assertEquals(Boolean.FALSE,stream.invoke(0));
		assertEquals(Boolean.TRUE,stream.invoke(1));
		assertEquals(Boolean.FALSE,stream.invoke(2));
	}
	
	/** Take a named input stream and make a filter based on a scripting function.
	 * <br />
	 * So this should only be run for the Scripting function based tests.
	 */
	public void testMakeFunctionBooleanCalc1BinaryPredicate() {
		if (_disableTests) return;
		if (_filterFunctionExpressionEven==null)
			return;
		String ruleName = "EvenNumbers";
		Integer compareObject = 2;
		Calc1<Integer, Boolean> stream = StreamFactory.makeFunctionBooleanCalc1(
				_filterFunctionExpressionEven, BinaryEqualPredicate.EQUALS, compareObject, _language, ruleName + Constants.FUNCTION_NAME_SUFFIX);
		assertEquals(Boolean.FALSE,stream.invoke(0));
		assertEquals(Boolean.TRUE,stream.invoke(1));
		assertEquals(Boolean.FALSE,stream.invoke(2));
	}
	
}
