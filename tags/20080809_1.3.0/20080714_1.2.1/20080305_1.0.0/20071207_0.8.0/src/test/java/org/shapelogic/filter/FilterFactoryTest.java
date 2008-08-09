package org.shapelogic.filter;

import junit.framework.TestCase;

/** Test FilterFactory
 * 
 * Currently only test the AST tree structure 
 * TODO Expanded
 * 
 * @author Sami Badawi
 *
 */
public class FilterFactoryTest extends TestCase {
	
	public void testSimpleMakeFilter(){
		IFilter filter = FilterFactory.makeFilter("org.shapelogic.filter.FullLengthVerticalFilter");
		assertTrue(filter instanceof IFilter);
		
		IFilter filter2 = FilterFactory.makeFilter("XXX");
		assertNull(filter2);
		
		IFilter filter3 = FilterFactory.makeFilter("FullLengthVerticalFilter");
		assertTrue(filter3 instanceof IFilter);
	} 

	public void testAndMakeFilter(){
		IFilter filter = FilterFactory.makeTreeFilter("FullLengthVerticalFilter");
		assertTrue(filter instanceof IFilter);
		
		IFilter filter3 = FilterFactory.makeTreeFilter("FullLengthHorizontalFilter");
		assertTrue(filter3 instanceof IFilter);
		
		IFilter andFilter = FilterFactory.makeTreeFilter(
				"FullLengthHorizontalFilter && FullLengthVerticalFilter");
		assertTrue(andFilter instanceof AndFilter);

		IFilter andOrFilter = FilterFactory.makeTreeFilter(
		"(FullLengthHorizontalFilter || FullLengthVerticalFilter) && FullLengthVerticalFilter");
		assertTrue(andOrFilter instanceof AndFilter);
	}
	
	public void testOrMakeFilter(){
		IFilter orFilter = FilterFactory.makeTreeFilter(
		"FullLengthHorizontalFilter || FullLengthVerticalFilter");
		assertTrue(orFilter instanceof OrFilter);

		IFilter andOrFilter = FilterFactory.makeTreeFilter(
		"FullLengthHorizontalFilter || FullLengthVerticalFilter && FullLengthVerticalFilter");
		assertTrue(andOrFilter instanceof OrFilter);
	} 
	
	public void testNotMakeFilter(){
		IFilter notFilter = FilterFactory.makeTreeFilter(
		"!FullLengthHorizontalFilter");
		assertTrue(notFilter instanceof NotFilter);
	} 
	
	public void testConstraintMakeFilter(){
		IFilter constraintFilter = FilterFactory.makeTreeFilter(
		"PointLeftOfFilter(0.5)");
		assertTrue(constraintFilter instanceof IFilter);
		assertEquals(0.5,constraintFilter.getConstraint());//XXX turn into number
	} 
}
