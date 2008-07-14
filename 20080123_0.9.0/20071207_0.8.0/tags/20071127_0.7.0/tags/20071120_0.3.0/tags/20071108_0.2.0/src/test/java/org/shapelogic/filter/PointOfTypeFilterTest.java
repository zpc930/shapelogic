package org.shapelogic.filter;

import org.shapelogic.imageprocessing.PointType;

import junit.framework.TestCase;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class PointOfTypeFilterTest extends TestCase {

	public void testParsing() {
		PointOfTypeFilter pointOfTypeFilter = new PointOfTypeFilter();
		pointOfTypeFilter.setConstraint(PointType.HARD_CORNER);
		assertEquals(PointType.HARD_CORNER,	pointOfTypeFilter.getConstraint());
		pointOfTypeFilter.setConstraint("SOFT_POINT");
		assertEquals(PointType.SOFT_POINT,	pointOfTypeFilter.getConstraint());
	} 
}
