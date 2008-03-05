package org.shapelogic.polygon;

import org.shapelogic.util.Constants;

import junit.framework.TestCase;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class AnnotatedShapeImplementationTest extends TestCase {
	AnnotatedShape annotatedShape = new AnnotatedShapeImplementation();
	
	@Override
	public void setUp() throws Exception
	{
		super.setUp();
		annotatedShape.setup();
	}
	
	public void testBasicSetAndGet() {
		CPointInt p1 = new CPointInt(1,2); 
		CPointInt p2 = new CPointInt(2,2);
		Integer ONE = new Integer(1);
		annotatedShape.putAnnotation(p1, ONE);
		assertNull(annotatedShape.getShapesForAnnotation(Constants.ZERO));
		assertEquals(1,annotatedShape.getShapesForAnnotation(ONE).size());
		annotatedShape.putAnnotation(p2, 1);
		assertEquals(2,annotatedShape.getShapesForAnnotation(1).size());
	}
}
