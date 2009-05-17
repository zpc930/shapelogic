package org.shapelogic.color;

/** Test GrayRange.<br />
 * 
 * @author Sami Badawi
 *
 */
public class GrayRangeTest extends GrayAndVarianceTest {
	
	public void testColorCenter() {
		GrayRange grayRange = new GrayRange();
		grayRange.setColorCenter(100);
		grayRange.setMaxDistance(10);
		assertEquals(0.,grayRange.distanceFromRange(105));
		assertTrue(grayRange.colorInRange(105));
	}
}
