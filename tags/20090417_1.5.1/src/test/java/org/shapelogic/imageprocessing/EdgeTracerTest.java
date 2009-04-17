package org.shapelogic.imageprocessing;

import org.shapelogic.imageutil.SLImage;


/** Test of EdgeTracer.<br />
 * 
 * @author Sami Badawi
 *
 */
public class EdgeTracerTest extends EdgeTracerTests {
	
	@Override
	public IEdgeTracer getInstance(SLImage image, int referenceColor,
			double maxDistance, boolean traceCloseToColor) {
		return new EdgeTracer(image,referenceColor,maxDistance,traceCloseToColor);
	}
	
	@Override
	public void setUp() {
	}
	
}
