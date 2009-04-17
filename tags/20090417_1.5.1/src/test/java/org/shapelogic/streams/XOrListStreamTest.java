package org.shapelogic.streams;

import java.util.ArrayList;
import java.util.List;

import org.shapelogic.calculation.Calc1;
import org.shapelogic.calculation.RootMap;
import org.shapelogic.mathematics.NaturalNumberStream;

import junit.framework.TestCase;

/** Test AndListStream.
 * 
 * @author Sami Badawi
 *
 */
public class XOrListStreamTest extends TestCase {
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
	
	public void testAddListStreamGet1() {
		List<NumberedStream<Boolean> > listOfStreams = new ArrayList<NumberedStream<Boolean>>();
		listOfStreams.add(greaterThan1Stream);
		List<String > listOfNames = new ArrayList<String>();
		String greaterThan1Name = "greaterThan1Stream";
		listOfNames.add(greaterThan1Name);
		ListStream<String> xorListStream = new XOrListStream(listOfNames, RootMap.getInstance(), listOfStreams);
		
		assertEquals("",xorListStream.get(0));
		assertEquals("",xorListStream.get(1));
		assertEquals(greaterThan1Name,xorListStream.get(2));
		assertEquals(greaterThan1Name,xorListStream.get(3));
	}
	
	public void testAddListStreamNext1() {
		List<NumberedStream<Boolean> > listOfStreams = new ArrayList<NumberedStream<Boolean>>();
		listOfStreams.add(greaterThan1Stream);
		List<String > listOfNames = new ArrayList<String>();
		String greaterThan1Name = "greaterThan1Stream";
		listOfNames.add(greaterThan1Name);
		XOrListStream xorListStream = new XOrListStream(listOfNames,RootMap.getInstance(),listOfStreams);
		
		assertEquals("",xorListStream.next());
		assertEquals("",xorListStream.next());
		assertEquals(greaterThan1Name,xorListStream.next());
		assertEquals(greaterThan1Name,xorListStream.next());
	}
	
	public void testAddListStreamGet2() {
		List<NumberedStream<Boolean> > listOfStreams = new ArrayList<NumberedStream<Boolean>>();
		listOfStreams.add(greaterThan1Stream);
		listOfStreams.add(greaterThan2Stream);
		List<String > listOfNames = new ArrayList<String>();
		String greaterThan1Name = "greaterThan1Stream";
		String greaterThan2Name = "greaterThan2Stream";
		listOfNames.add(greaterThan1Name);
		listOfNames.add(greaterThan2Name);
		XOrListStream xorListStream = new XOrListStream(listOfNames,RootMap.getInstance(),listOfStreams);
		
		assertEquals("",xorListStream.get(0));
		assertEquals("",xorListStream.get(1));
		assertEquals(greaterThan1Name,xorListStream.get(2));
		assertEquals("",xorListStream.get(3));
	}
	
}
