package org.shapelogic.imageprocessing;

import ij.process.ByteProcessor;

import java.util.Collection;
import java.util.Set;

import org.shapelogic.polygon.CLine;
import org.shapelogic.polygon.CPointInt;
import org.shapelogic.polygon.IPoint2D;
import org.shapelogic.polygon.MultiLinePolygon;
import org.shapelogic.polygon.PolygonEndPointAdjuster;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class DirectionBasedVectorizerTest extends AbstractImageProcessingTests {
	DirectionBasedVectorizer directionBasedVectorizer = new DirectionBasedVectorizer();
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		dirURL = "./src/test/resources/images/smallThinShapes";
		fileFormat = ".gif";
	}
	
	public void testShortVertical() {
		String fileName = "vertical";
		ByteProcessor bp = runPluginFilterOnImage(filePath(fileName), directionBasedVectorizer);
		assertEquals(20,bp.getWidth());
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Collection<IPoint2D> points = directionBasedVectorizer.getPoints();
		assertEquals(2, points.size());
		Collection<CLine> lines = directionBasedVectorizer.getPolygon().getLines();
		assertEquals(1, lines.size());
		MultiLinePolygon polygon = ((MultiLinePolygon)directionBasedVectorizer.getPolygon());
		assertEquals(0,polygon.getMultiLines().size());
	}

	public void testShortVerticalAndHorizontal() {
		String fileName = "verticalAndHorizontal";
		ByteProcessor bp = runPluginFilterOnImage(filePath(fileName), directionBasedVectorizer);
		assertEquals(20,bp.getWidth());
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Collection<IPoint2D> points = directionBasedVectorizer.getPoints();
		CPointInt topPoint = new CPointInt(1,1); 
		CPointInt bottomPoint1 = new CPointInt(1,17); 
		CPointInt bottomPoint2 = new CPointInt(15,17); 
		assertTrue(points.contains(topPoint));
		assertTrue(points.contains(bottomPoint1));
		assertTrue(points.contains(bottomPoint2));
		printPoints(directionBasedVectorizer.getPolygon());
		assertEquals(3, points.size());
		Collection<CLine> lines = directionBasedVectorizer.getPolygon().getLines();
		assertEquals(2, lines.size());
		assertEquals(3, directionBasedVectorizer.getPolygon().getEndPointsClusters().size());
		MultiLinePolygon polygon = ((MultiLinePolygon)directionBasedVectorizer.getPolygon());
		assertEquals(1,polygon.getMultiLines().size());
		assertFalse(polygon.getMultiLines().get(0).isClosed());
	}

	public void testShortRotatedTThin() {
		String fileName = "rotatedT";
		ByteProcessor bp = runPluginFilterOnImage(filePath(fileName), directionBasedVectorizer);
		assertEquals(20,bp.getWidth());
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		assertEquals(2, directionBasedVectorizer.countRegionCrossingsAroundPoint(directionBasedVectorizer.pointToPixelIndex(1,1)));
		assertEquals(4, directionBasedVectorizer.countRegionCrossingsAroundPoint(directionBasedVectorizer.pointToPixelIndex(1,2)));
		assertEquals(4, directionBasedVectorizer.countRegionCrossingsAroundPoint(directionBasedVectorizer.pointToPixelIndex(1,7)));
		//T-junction
		assertEquals(6, directionBasedVectorizer.countRegionCrossingsAroundPoint(directionBasedVectorizer.pointToPixelIndex(1,8))); 
		Collection<IPoint2D> points = directionBasedVectorizer.getPoints();
		printPolygon(directionBasedVectorizer.getPolygon());
		assertEquals(4, points.size());
		Collection<CLine> lines = directionBasedVectorizer.getPolygon().getLines();
		assertEquals(3, lines.size());
	}
	
	public void testThinProblematicL() {
		String fileName = "problematicL";
		ByteProcessor bp = runPluginFilterOnImage(filePath(fileName), directionBasedVectorizer);
		assertEquals(20,bp.getWidth());
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Collection<IPoint2D> points = directionBasedVectorizer.getPoints();
		assertEquals(3, points.size());
		Collection<CLine> lines = directionBasedVectorizer.getPolygon().getLines();
		assertEquals(2, lines.size());
		assertEquals("L",directionBasedVectorizer.getMatchingOH());
		MultiLinePolygon polygon = ((MultiLinePolygon)directionBasedVectorizer.getPolygon());
		assertEquals(1,polygon.getMultiLines().size());
		assertFalse(polygon.getMultiLines().get(0).isClosed());
	}
	
	public void testThinDiagonal() {
		String fileName = "diagonal";
		ByteProcessor bp = runPluginFilterOnImage(filePath(fileName), directionBasedVectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Collection<IPoint2D> points = directionBasedVectorizer.getPoints();
		assertNotNull(points);
		Collection<CLine> lines = directionBasedVectorizer.getPolygon().getLines();
		assertEquals(1, lines.size());
	}

	public void testSmallThinTriangle() {
		String fileName = "triangle";
		ByteProcessor bp = runPluginFilterOnImage(filePath(fileName), directionBasedVectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Collection<IPoint2D> points = directionBasedVectorizer.getPoints();
		assertNotNull(points);
		CPointInt topPoint1 = new CPointInt(1,3); 
		CPointInt topPoint2 = new CPointInt(2,2); 
		CPointInt bottomPoint1 = new CPointInt(1,27);  
		CPointInt bottomPoint2 = new CPointInt(2,28); 
		CPointInt bottomPoint3 = new CPointInt(26,28); 
		CPointInt bottomPoint4 = new CPointInt(27,27);
		int crossingsForbottomPoint2 = directionBasedVectorizer.countRegionCrossingsAroundPoint( 
				directionBasedVectorizer.pointToPixelIndex(bottomPoint2.x, bottomPoint2.y));
		assertEquals(4,	crossingsForbottomPoint2);
		assertTrue(points.contains(topPoint1));
		assertTrue(points.contains(topPoint2));
		assertTrue(points.contains(bottomPoint1));
		assertTrue(points.contains(bottomPoint2));
		assertTrue(points.contains(bottomPoint3));
		assertTrue(points.contains(bottomPoint4));
		assertEquals(6,points.size());
		Collection<CLine> lines = directionBasedVectorizer.getPolygon().getLines();
		assertEquals(6, lines.size());
		assertEquals(6, directionBasedVectorizer.getPolygon().getEndPointsClusters().size());
		assertEquals(3, directionBasedVectorizer.getPolygon().getEndPointsMultiClusters().size());
		MultiLinePolygon polygon = ((MultiLinePolygon)directionBasedVectorizer.getPolygon());
		assertEquals(1,polygon.getMultiLines().size());
		assertTrue(polygon.getMultiLines().get(0).isClosed());
		PolygonEndPointAdjuster clusterAdjuster = new PolygonEndPointAdjuster(polygon);
		MultiLinePolygon clusteredPolygon = (MultiLinePolygon) clusterAdjuster.getCalcValue();
		assertNotSame(polygon, clusteredPolygon);
		assertEquals(3,clusteredPolygon.getPoints().size());
		assertEquals(3,clusteredPolygon.getLines().size());
		CPointInt adjustedTopPoint = new CPointInt(1,1); 
		CPointInt adjustedBottomPoint1 = new CPointInt(1,28);  
		CPointInt adjustedBottomPoint2 = new CPointInt(28,28);
		Collection<IPoint2D> adjustedPoints = clusteredPolygon.getPoints();
		assertTrue(adjustedPoints.contains(adjustedTopPoint));
		assertTrue(adjustedPoints.contains(adjustedBottomPoint1));
		assertTrue(adjustedPoints.contains(adjustedBottomPoint2));
	}

	public void testThinLBracket() {
		String fileName = "LBracket";
		ByteProcessor bp = runPluginFilterOnImage(filePath(fileName), directionBasedVectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Set<IPoint2D> points = (Set<IPoint2D>)directionBasedVectorizer.getPoints();
		assertNotNull(points);
		CPointInt topPoint = new CPointInt(1,1); 
		CPointInt bottomPoint1 = new CPointInt(1,27); 
		CPointInt bottomPoint2 = new CPointInt(2,28); 
		CPointInt bottomPoint3 = new CPointInt(28,28); 
		assertTrue(points.contains(topPoint));
		assertTrue(points.contains(bottomPoint1));
		assertTrue(points.contains(bottomPoint2));
		assertTrue(points.contains(bottomPoint3));
		assertEquals(4,points.size());
		Collection<CLine> lines = directionBasedVectorizer.getPolygon().getLines();
		assertEquals(3, lines.size());
		MultiLinePolygon polygon = ((MultiLinePolygon)directionBasedVectorizer.getPolygon());
		assertEquals(1,polygon.getMultiLines().size());
		PolygonEndPointAdjuster clusterAdjuster = new PolygonEndPointAdjuster(polygon);
		MultiLinePolygon clusteredPolygon = (MultiLinePolygon) clusterAdjuster.getCalcValue();
		assertNotSame(polygon, clusteredPolygon);
		assertEquals(3,clusteredPolygon.getPoints().size());
		assertEquals(2,clusteredPolygon.getLines().size());
}

	/** This test did not work for the DirectionBasedVectorizer
	 * That is the reason that I created a new vectorizer: MaxDistanceVectorizer
	 */
	public void testElongatedX() {
		String fileName = "elongatedX";
		ByteProcessor bp = runPluginFilterOnImage(filePath(fileName), directionBasedVectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Set<IPoint2D> points = (Set<IPoint2D>)directionBasedVectorizer.getPoints();
		assertNotNull(points);
		printPoints(directionBasedVectorizer.getPolygon());
		CPointInt topPoint1 = new CPointInt(1,1); 
		CPointInt bottomPoint1 = new CPointInt(1,8); 
		CPointInt topPoint2 = new CPointInt(7,1); 
		CPointInt bottomPoint2 = new CPointInt(7,8); 
		CPointInt midtTopPoint = new CPointInt(4,4); 
		CPointInt midtBottomPoint = new CPointInt(4,5); 
		assertTrue(points.contains(topPoint1));
//		assertTrue(points.contains(midtTopPoint));
		assertTrue(points.contains(bottomPoint1));
//		assertTrue(points.contains(topPoint2));
		assertTrue(points.contains(bottomPoint2));
		assertTrue(points.contains(midtBottomPoint));
//		assertEquals(6,points.size());
//		Collection<CLine> lines = directionBasedVectorizer.getPolygon().getLines();
//		assertEquals(5, lines.size());
	}

	public void testThinPlus() {
		String fileName = "plus";
		ByteProcessor bp = runPluginFilterOnImage(filePath(fileName), directionBasedVectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Set<IPoint2D> points = (Set<IPoint2D>)directionBasedVectorizer.getPoints();
		assertNotNull(points);
		CPointInt topPoint = new CPointInt(10,1); 
		CPointInt middlePoint = new CPointInt(10,10); 
		CPointInt leftPoint = new CPointInt(1,10); 
		CPointInt rightPoint = new CPointInt(18,10); 
		CPointInt bottomPoint = new CPointInt(10,18); 
		assertTrue(points.contains(topPoint));
		assertTrue(points.contains(bottomPoint));
		assertTrue(points.contains(middlePoint));
		assertTrue(points.contains(leftPoint));
		assertTrue(points.contains(rightPoint));
		assertEquals(5,points.size());
		Collection<CLine> lines = directionBasedVectorizer.getPolygon().getLines();
//		assertEquals(3, lines.size());
	}

	/** This is an test for new images with problems, should only be used when there are problems*/
	public void testBigCircle() {
		String fileName = "bigCircle";
		ByteProcessor bp = runPluginFilterOnImage(filePath(fileName), directionBasedVectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Collection<IPoint2D> points = directionBasedVectorizer.getPoints();
		assertNotNull(points);
		MultiLinePolygon polygon = ((MultiLinePolygon)directionBasedVectorizer.getPolygon());
		assertEquals(1,polygon.getMultiLines().size());
		assertTrue(polygon.getMultiLines().get(0).isClosed());
		assertNotNull(polygon.getMultiLines().get(0).getCenterForCircle());
	}

	/** This is an test for new images with problems, should only be used when there are problems*/
	public void te_stFailImage() {
		String fileName = "fail";
		ByteProcessor bp = runPluginFilterOnImage(filePath(fileName), directionBasedVectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Collection<IPoint2D> points = directionBasedVectorizer.getPoints();
		assertNotNull(points);
	}

}
