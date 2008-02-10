package org.shapelogic.calculation;

import junit.framework.TestCase;

/** Test CalcComposition.
 * 
 * @author Sami Badawi
 *
 */
public class CalcCompositionTest extends TestCase {
	
	Calc0<Integer> one = new Calc0<Integer>() {
		@Override
		public Integer invoke() {
			return 1;
		}
	};
	
	Calc1<Integer, Integer> multiplyBy2 = new Calc1<Integer, Integer>() {
		@Override
		public Integer invoke(Integer input) {
			return input * 2;
		}
	};
	
	CalcIndex0<Integer> naturalNumber = new CalcIndex0<Integer>() {

		@Override
		public Integer invoke(int index) {
			return index;
		}
		
	};
	
	CalcIndex1<Integer,Integer> indexMultiply = new CalcIndex1<Integer,Integer>() {
		@Override
		public Integer invoke(Integer input, int index) {
			return input * index;
		}
	};
	
	Calc1<Integer, Integer> multiplyBy3 = new Calc1<Integer, Integer>() {
		@Override
		public Integer invoke(Integer input) {
			return input * 3;
		}
	};
	
	public void testCalc1Calc0Composite() {
		assertEquals(new Integer(2),CalcComposition.compose(multiplyBy2, one).invoke());
	}
	
	public void testCalc1Calc1Calc0Composite() {
		Calc1<Integer,Integer> multiplyBy6 = CalcComposition.compose(multiplyBy2,multiplyBy3);
		assertEquals(new Integer(6),CalcComposition.compose(multiplyBy6, one).invoke());
	}
	
	public void testCalc1CalcIndex0Composite() {
		CalcIndex0<Integer> even = CalcComposition.compose(multiplyBy2,naturalNumber);
		assertEquals(new Integer(0),even.invoke(0));
		assertEquals(new Integer(2),even.invoke(1));
		assertEquals(new Integer(4),even.invoke(2));
	}
	
	public void testCalc1CalcIndex1Composite() {
		CalcIndex1<Integer,Integer> even = CalcComposition.compose(multiplyBy2,indexMultiply);
		assertEquals(new Integer(0),even.invoke(1,0));
		assertEquals(new Integer(2),even.invoke(1,1));
		assertEquals(new Integer(4),even.invoke(1,2));
		assertEquals(new Integer(12),even.invoke(2,3));
	}
	
	public void testCalcIndex1CalcIndex1Composite() {
		CalcIndex1<Integer,Integer> even = CalcComposition.compose(indexMultiply,indexMultiply);
		assertEquals(new Integer(0),even.invoke(1,0));
		assertEquals(new Integer(1),even.invoke(1,1));
		assertEquals(new Integer(4),even.invoke(1,2));
		assertEquals(new Integer(18),even.invoke(2,3));
	}
}
