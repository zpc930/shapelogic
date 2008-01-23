package org.shapelogic.polygon;

import org.shapelogic.polygon.CLine;
import org.shapelogic.polygon.CPointDouble;
import org.shapelogic.polygon.CPointInt;
import org.shapelogic.polygon.Calculator2D;
import org.shapelogic.polygon.IPoint2D;

import junit.framework.TestCase;
import static org.shapelogic.polygon.Calculator2D.*;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class Calculator2DTest extends TestCase {

	CPointInt origin = new CPointInt(0,0);
	CPointInt xAxis1 = new CPointInt(1,0);
	CPointInt xAxis2 = new CPointInt(2,0);
	CPointInt yAxis1 = new CPointInt(0,1);
	CPointInt diagonal1 = new CPointInt(1,1);
	CLine xAxis1Line = new CLine(origin,xAxis1);
	Calculator2D calculator2D = new Calculator2D();  
	CPointDouble xAxis1Double = new CPointDouble(1,0);
	
	public void testHatPoint() {
		assertEquals(yAxis1, hatPoint(xAxis1));
	}
	
	public void testDotProduct() {
		assertEquals(0.,dotProduct(xAxis1, yAxis1));
		assertEquals(1.,dotProduct(xAxis1, xAxis1));
	}
	
	public void testCrossProduct() {
		assertEquals(1.,crossProduct(xAxis1, yAxis1));
		assertEquals(0.,crossProduct(xAxis1, xAxis1));
	}
	
	/** This is signed */
	public void testDistanceOfPointToLine() {
		assertEquals(0.,distanceOfPointToLine(xAxis1, xAxis1Line));
		assertEquals(1.,distanceOfPointToLine(yAxis1, xAxis1Line));
	}
	
	public void testScaleLineFromStartPoint() {
		assertEquals(pointToLine(xAxis2),scaleLineFromStartPoint(xAxis1Line, 2.));
	}
	
	public void testPointToLine() {
		assertEquals(xAxis1Line,pointToLine(xAxis1));
	}
	
	public void testProjectionOfPointOnLine() {
		assertEquals(origin,projectionOfPointOnLine(yAxis1, xAxis1Line));
	}
	
	public void testInverseLine() {
		assertEquals(xAxis1Line, inverseLine(new CLine(xAxis1,origin)));
	}

	public void testAddLines() {
		CLine addedLines = addLines(xAxis1Line, pointToLine(yAxis1));
		assertEquals(pointToLine(diagonal1),addedLines);
	}
	
	public void testIntersectionOfLines(){
		assertEquals(origin,intersectionOfLines(xAxis1Line, new CLine(yAxis1,origin)));
		assertEquals(xAxis1,intersectionOfLines(xAxis1Line, new CLine(diagonal1,xAxis1)));
		
		CLine activeLine = new CLine(new CPointInt(3,2), new CPointInt(2,1));
		CPointInt expectedPoint = new CPointInt(1,0);
		assertEquals(expectedPoint,intersectionOfLines(xAxis1Line, activeLine));
	}

	public void testIntersectionOfLinesDouble(){
		
		CLine activeLine = new CLine(new CPointDouble(3,2), new CPointDouble(2,1));
		CPointDouble expectedPoint = new CPointDouble(1,0);
		assertEquals(expectedPoint,intersectionOfLines(xAxis1Line, activeLine));
	}

	public void testToCPointDouble() {
		assertEquals(xAxis1Double,toCPointDouble(xAxis1));
	}
	
	public void testToCPointInt() {
		assertEquals(xAxis1,toCPointInt(xAxis1Double));
	}
	
	public void testToCPointIntIfPossible() {
		IPoint2D badIntPoint = new CPointDouble(0.0, 0.8); 
		assertTrue(toCPointIntIfPossible(xAxis1) instanceof CPointInt);
		assertTrue(toCPointIntIfPossible(xAxis1Double) instanceof CPointInt);
		assertFalse(toCPointIntIfPossible(badIntPoint) instanceof CPointInt);
	}
	
	public void testLinesParallel(CLine line1, CLine line2) {
		assertTrue(linesParallel(xAxis1Line, pointToLine(xAxis1)));
	}

	public void testIntersectionOfLinesInt(){
		CPointInt topPoint = new CPointInt(1,1); 
		CPointInt bottomPoint1 = new CPointInt(1,27); 
		CPointInt bottomPoint2 = new CPointInt(2,28); 
		CPointInt bottomPoint3 = new CPointInt(28,28);
		CLine activeLine = new CLine(topPoint, bottomPoint1);
		CLine projectionLine = new CLine(bottomPoint2, bottomPoint3);
		CPointDouble expectedPoint = new CPointDouble(1,28);
		assertEquals(expectedPoint,intersectionOfLines(activeLine, projectionLine));
	}
	public void testDirectionBetweenNeighborPoints() {
		assertEquals(0, (int)directionBetweenNeighborPoints(origin, xAxis1));
		assertEquals(1, (int)directionBetweenNeighborPoints(origin, diagonal1));
		assertEquals(2, (int)directionBetweenNeighborPoints(origin, yAxis1)); 
		assertEquals(3, (int)directionBetweenNeighborPoints(origin, new CPointInt(-1,1))); 
		assertEquals(4, (int)directionBetweenNeighborPoints(origin, new CPointInt(-1,0))); 
		assertEquals(5, (int)directionBetweenNeighborPoints(origin, new CPointInt(-1,-1))); 
		assertEquals(6, (int)directionBetweenNeighborPoints(origin, new CPointInt(0,-1))); 
		assertEquals(7, (int)directionBetweenNeighborPoints(origin, new CPointInt(1,-1))); 
	}

}
