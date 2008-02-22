package org.shapelogic.streams;

import org.shapelogic.calculation.Calc1;
import org.shapelogic.mathematics.NaturalNumberStream;

import junit.framework.TestCase;

/** Test AndListStream.
 * 
 * @author Sami Badawi
 *
 */
public class AndListStreamTest extends TestCase {
	ListStream<Boolean> greaterThan1Stream;
	ListStream<Boolean> greaterThan2Stream;
	
	@Override
	public void setUp() {
		greaterThan1Stream = makeGreaterThanNumberStream(1);
		greaterThan2Stream = makeGreaterThanNumberStream(2);
	}
	
	public ListStream<Boolean> makeGreaterThanNumberStream(final int number) {
		Calc1<Integer, Boolean> greaterThanNumberCalc = 
			new Calc1<Integer,Boolean>() {
			@Override
			public Boolean invoke(Integer input) {
				return number < input;
			}
		};
		ListStream<Boolean> result =
			new ListCalcStream1<Integer, Boolean>(greaterThanNumberCalc, new NaturalNumberStream());
		return result;
	}
	
	public void testAddListStream1() {
		AndListStream andListStream = new AndListStream();
		andListStream.getInputStream().add(greaterThan1Stream);
		
		assertEquals(Boolean.FALSE,andListStream.get(0));
		assertEquals(Boolean.FALSE,andListStream.get(1));
		assertEquals(Boolean.TRUE,andListStream.get(2));
	}
	
	public void testAddListStream2() {
		AndListStream andListStream = new AndListStream();
		andListStream.getInputStream().add(greaterThan1Stream);
		andListStream.getInputStream().add(greaterThan2Stream);
		
		assertEquals(Boolean.FALSE,andListStream.get(0));
		assertEquals(Boolean.FALSE,andListStream.get(1));
		assertEquals(Boolean.FALSE,andListStream.get(2));
		assertEquals(Boolean.TRUE,andListStream.get(3));
	}
}
