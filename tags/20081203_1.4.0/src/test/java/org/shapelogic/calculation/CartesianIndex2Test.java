package org.shapelogic.calculation;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class CartesianIndex2Test extends TestCase {
	CartesianIndex2 cartesianIndex2;
	List<Integer> list1;
	List<Integer> list2;
//	int[] exprected = new int[2];
	
	/**
	 * 
	 */
	public void setUp() { 
		list1 = new ArrayList<Integer>();
		list1.add(0);
		list1.add(1);
		list2 = new ArrayList<Integer>();
		list2.add(0);
		list2.add(10);
		cartesianIndex2 = new CartesianIndex2(list1,list2);
	}
	
	public void testInitForList() {
		assertEquals(0, cartesianIndex2.getValue()[0]);
		assertEquals(0, cartesianIndex2.getValue()[1]);
		cartesianIndex2.next();
		assertEquals(1, cartesianIndex2.getValue()[0]);
		assertEquals(0, cartesianIndex2.getValue()[1]);
		cartesianIndex2.next();
		assertEquals(0, cartesianIndex2.getValue()[0]);
		assertEquals(1, cartesianIndex2.getValue()[1]);
		assertTrue(cartesianIndex2.hasNext());
		cartesianIndex2.next();
		assertEquals(1, cartesianIndex2.getValue()[0]);
		assertEquals(1, cartesianIndex2.getValue()[1]);
		assertFalse(cartesianIndex2.hasNext());
	}
	
}
