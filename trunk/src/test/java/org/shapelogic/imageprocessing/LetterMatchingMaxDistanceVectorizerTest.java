package org.shapelogic.imageprocessing;

import ij.process.ByteProcessor;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.shapelogic.filter.AndFilter;
import org.shapelogic.filter.FullLengthVerticalFilter;
import org.shapelogic.filter.IFilter;
import org.shapelogic.filter.LineOfTypeFilter;
import org.shapelogic.filter.PointAboveFilter;
import org.shapelogic.filter.PointBelowFilter;
import org.shapelogic.filter.PointLeftOfFilter;
import org.shapelogic.filter.PointOfTypeFilter;
import org.shapelogic.filter.PointRightOfFilter;
import org.shapelogic.polygon.AnnotatedShape;
import org.shapelogic.polygon.CPointInt;
import org.shapelogic.polygon.GeometricShape2D;
import org.shapelogic.polygon.IPoint2D;
import org.shapelogic.polygon.CLine;
import org.shapelogic.polygon.MultiLine;
import org.shapelogic.polygon.Polygon;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class LetterMatchingMaxDistanceVectorizerTest extends BaseLetterMatchingForVectorizersTests {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		vectorizer = new MaxDistanceVectorizer();
	}
	
	@Override
	public void testA() {
		super.testA();
		Polygon polygon = vectorizer.getPolygon();
		Polygon improvedPolygon = polygon.improve(); 
		AnnotatedShape annotations = improvedPolygon.getAnnotatedShape();
		Set<GeometricShape2D> endPoints = annotations.getShapesForAnnotation(PointType.END_POINT);
		System.out.println("End points: " + endPoints);
		assertEquals(2, endPoints.size());
		Set<GeometricShape2D> junctionPoints = annotations.getShapesForAnnotation(PointType.T_JUNCTION);
		assertEquals(2, junctionPoints.size());
		printAnnotaions(polygon);
	}

	/** There is something wrong with the annotations of B, it only found 2 and one is wrong
	 */
	public void testB() {
		String fileName = "B";
		ByteProcessor bp = runPluginFilterOnImage(filePath(fileName), vectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Polygon polygon = vectorizer.getPolygon();
		printPoints(polygon);
		printPoints(polygon);
		System.out.println("cleaned up");
		Polygon cleanedPolygon = vectorizer.getCleanedupPolygon();
		Set<IPoint2D> points = (Set<IPoint2D>)cleanedPolygon.getPoints();
		printPoints(cleanedPolygon);
		printAnnotaions(cleanedPolygon);
		CPointInt topPoint1 = new CPointInt(4,3); 
		CPointInt topPoint2 = new CPointInt(17,6); //XXX could move
		CPointInt middlePoint1 = new CPointInt(3,14); 
		CPointInt middlePoint2 = new CPointInt(15,14); 
		CPointInt bottomPoint1 = new CPointInt(4,26); 
		CPointInt bottomPoint2 = new CPointInt(18,23); //XXX could move
		assertTrue(points.contains(topPoint1));
		assertTrue(points.contains(topPoint2));
		assertTrue(points.contains(middlePoint1));
		assertTrue(points.contains(middlePoint2));
		assertTrue(points.contains(bottomPoint1));
		assertTrue(points.contains(bottomPoint2));
		assertEquals(7,points.size());
		AnnotatedShape annotations = cleanedPolygon.getAnnotatedShape();
		assertTrue(annotations.getAnnotationForShapes(middlePoint1).contains(PointType.U_JUNCTION));
		assertTrue(annotations.getAnnotationForShapes(middlePoint2).contains(PointType.U_JUNCTION)); //XXX wrong
		assertTrue(annotations.getAnnotationForShapes(topPoint2).contains(PointType.SOFT_POINT));
		assertTrue(annotations.getAnnotationForShapes(bottomPoint2).contains(PointType.SOFT_POINT));
		assertTrue(annotations.getAnnotationForShapes(topPoint1).contains(PointType.HARD_CORNER));
		assertTrue(annotations.getAnnotationForShapes(bottomPoint1).contains(PointType.HARD_CORNER));
		assertEquals(fileName,vectorizer.getMatchingOH());
	}
	
	public void testD() {
		String fileName = "D";
		ByteProcessor bp = runPluginFilterOnImage(filePath(fileName), vectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Set<IPoint2D> points = (Set<IPoint2D>)vectorizer.getPoints();
		Polygon polygon = vectorizer.getPolygon();
		printPoints(polygon);
		printAnnotaions(polygon);
		System.out.println("cleaned up");
		Polygon cleanedPolygon = vectorizer.getCleanedupPolygon();
		printPoints(cleanedPolygon);
		printAnnotaions(cleanedPolygon);
		CPointInt topPoint1 = new CPointInt(4,3); 
		CPointInt topPoint2 = new CPointInt(19,6); //XXX could move
		CPointInt bottomPoint1 = new CPointInt(4,26); 
		CPointInt bottomPoint2 = new CPointInt(20,21); //XXX could move
		assertTrue(points.contains(topPoint1));
		assertTrue(points.contains(topPoint2));
		assertTrue(points.contains(bottomPoint1));
		assertTrue(points.contains(bottomPoint2));
		assertEquals(4,points.size());
		AnnotatedShape annotations = cleanedPolygon.getAnnotatedShape();
		assertTrue(annotations.getAnnotationForShapes(topPoint1).contains(PointType.HARD_CORNER));
		assertTrue(annotations.getAnnotationForShapes(bottomPoint1).contains(PointType.HARD_CORNER));
		assertTrue(annotations.getAnnotationForShapes(topPoint2).contains(PointType.SOFT_POINT));
		assertTrue(annotations.getAnnotationForShapes(bottomPoint2).contains(PointType.SOFT_POINT));
		CLine verticalLine = CLine.makeUnordered(topPoint1, bottomPoint1);
		assertTrue(annotations.getAnnotationForShapes(verticalLine).contains(LineType.STRAIGHT));
		CLine bottomArch = CLine.makeUnordered(bottomPoint1, bottomPoint2);
		assertTrue(annotations.getAnnotationForShapes(bottomArch).contains(LineType.CURVE_ARCH));
		assertNull(annotations.getShapesForAnnotation(LineType.INFLECTION_POINT));
		assertEquals(1,cleanedPolygon.filter(new FullLengthVerticalFilter()).size());
		assertEquals(1,cleanedPolygon.filter("org.shapelogic.filter.FullLengthVerticalFilter").size());
		assertEquals(0,cleanedPolygon.filter("org.shapelogic.filter.FullLengthHorizontalFilter").size());
		IFilter<Polygon,CLine> lineTypeFilter = new LineOfTypeFilter();
		lineTypeFilter.setConstraint(LineType.STRAIGHT);
		assertTrue(1 <= cleanedPolygon.filter(lineTypeFilter).size());
		assertTrue(cleanedPolygon.filter(lineTypeFilter).size() < cleanedPolygon.getLines().size());
		
		IFilter<Polygon,IPoint2D> pointTypeHardFilter = new PointOfTypeFilter();
		pointTypeHardFilter.setConstraint(PointType.HARD_CORNER);
		assertTrue(1 <= cleanedPolygon.filter(pointTypeHardFilter).size());
		assertTrue(cleanedPolygon.filter(pointTypeHardFilter).size() < cleanedPolygon.getPoints().size());
		
		String hardPointTextFilterExpression = "PointOfTypeFilter(PointType.HARD_CORNER)";
		Collection<Object> hardPoint = cleanedPolygon.filter(hardPointTextFilterExpression);
		assertTrue(1 <= hardPoint.size());
		assertTrue(hardPoint.size() < cleanedPolygon.getPoints().size());
		
		IFilter<Polygon,IPoint2D> pointAboveFilter = new PointAboveFilter();
		double aboveLimit = 0.3;
		pointAboveFilter.setConstraint(aboveLimit);
		System.out.println("Above " + aboveLimit + ": " +cleanedPolygon.filter(pointAboveFilter));
		assertEquals(2, cleanedPolygon.filter(pointAboveFilter).size());

		IFilter<Polygon,IPoint2D> pointBelowFilter = new PointBelowFilter();
		pointBelowFilter.setConstraint(aboveLimit);
		System.out.println("Below " + aboveLimit + ": " +cleanedPolygon.filter(pointBelowFilter));
		assertEquals(2, cleanedPolygon.filter(pointBelowFilter).size());

		IFilter<Polygon,IPoint2D> pointLeftOfFilter = new PointLeftOfFilter();
		pointLeftOfFilter.setConstraint(aboveLimit);
		System.out.println("Left " + aboveLimit + ": " +cleanedPolygon.filter(pointLeftOfFilter));
		assertEquals(2, cleanedPolygon.filter(pointLeftOfFilter).size());

		IFilter<Polygon,IPoint2D> pointRightOfFilter = new PointRightOfFilter();
		pointRightOfFilter.setConstraint(aboveLimit);
		System.out.println("Right " + aboveLimit + ": " +cleanedPolygon.filter(pointRightOfFilter));
		assertEquals(2, cleanedPolygon.filter(pointRightOfFilter).size());
		
		IFilter<Polygon,IPoint2D> topRight = new AndFilter<Polygon, IPoint2D>(pointRightOfFilter,pointAboveFilter);
		System.out.println("Right top: " + aboveLimit + ": " +cleanedPolygon.filter(topRight));
		assertEquals(1, cleanedPolygon.filter(topRight).size());

		String topRightString = "PointRightOfFilter(0.3) && PointAboveFilter(0.3)"; 
		System.out.println("Right top: " + aboveLimit + ": " +cleanedPolygon.filter(topRightString));
		assertEquals(1, cleanedPolygon.filter(topRight).size());
		
		IFilter<Polygon,IPoint2D> topHard = new AndFilter<Polygon, IPoint2D>(pointTypeHardFilter,pointAboveFilter);
		System.out.println("Right top: " + aboveLimit + ": " +cleanedPolygon.filter(topRight));
		assertEquals(1, cleanedPolygon.filter(topRight).size());
		
//		assertEquals(fileName,vectorizer.getMatchingOH());
	}
	
	public void testO() {
		String fileName = "O";
		ByteProcessor bp = runPluginFilterOnImage(filePath(fileName), vectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Set<IPoint2D> points = (Set<IPoint2D>)vectorizer.getPoints();
		Polygon polygon = vectorizer.getPolygon();
		printPoints(polygon);
		printAnnotaions(polygon);
		System.out.println("cleaned up");
		Polygon cleanedPolygon = vectorizer.getCleanedupPolygon();
		printPoints(cleanedPolygon);
		printAnnotaions(cleanedPolygon);
		CPointInt topPoint1 = new CPointInt(9,2); //XXX could move
		CPointInt topPoint2 = new CPointInt(20,5); //XXX could move
		CPointInt bottomPoint1 = new CPointInt(15,25); 
		CPointInt middlePoint1 = new CPointInt(3,19); //XXX could move
		CPointInt middlePoint2 = new CPointInt(22,18); //XXX could move
		assertTrue(points.contains(topPoint1));
		assertTrue(points.contains(topPoint2));
		assertTrue(points.contains(bottomPoint1));
		assertTrue(points.contains(middlePoint1));
		assertTrue(points.contains(middlePoint2));
		assertEquals(5,points.size());
		AnnotatedShape annotations = cleanedPolygon.getAnnotatedShape();
		assertTrue(annotations.getAnnotationForShapes(topPoint1).contains(PointType.SOFT_POINT));
		assertTrue(annotations.getAnnotationForShapes(bottomPoint1).contains(PointType.SOFT_POINT));
		assertTrue(annotations.getAnnotationForShapes(topPoint2).contains(PointType.SOFT_POINT));
		assertTrue(annotations.getAnnotationForShapes(middlePoint2).contains(PointType.SOFT_POINT));
		CLine verticalLine = CLine.makeUnordered(topPoint1, middlePoint1);
		assertTrue(annotations.getAnnotationForShapes(verticalLine).contains(LineType.CURVE_ARCH));
		CLine bottomArch = CLine.makeUnordered(bottomPoint1, middlePoint1);
		assertTrue(annotations.getAnnotationForShapes(bottomArch).contains(LineType.CURVE_ARCH));
		assertNull(annotations.getShapesForAnnotation(LineType.INFLECTION_POINT));
//		assertEquals(fileName,vectorizer.getMatchingOH());
	}
	
	public void testS() {
		String fileName = "S";
		ByteProcessor bp = runPluginFilterOnImage(filePath(fileName), vectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Polygon polygon = vectorizer.getPolygon();
		printPoints(polygon);
		printPoints(polygon);
		System.out.println("cleaned up");
		Polygon cleanedPolygon = vectorizer.getCleanedupPolygon();
		Set<IPoint2D> points = (Set<IPoint2D>)cleanedPolygon.getPoints();
		printPoints(cleanedPolygon);
		printAnnotaions(cleanedPolygon);
		CPointInt topPoint1 = new CPointInt(10,3); 
		CPointInt topPoint2 = new CPointInt(19,3); 
		CPointInt middlePoint1 = new CPointInt(6,9); 
		CPointInt middlePoint2 = new CPointInt(18,15); 
		CPointInt bottomPoint1 = new CPointInt(5,23); 
		CPointInt bottomPoint2 = new CPointInt(20,23); 
		assertTrue(points.contains(topPoint1));
		assertTrue(points.contains(topPoint2));
		assertTrue(points.contains(middlePoint1));
		assertTrue(points.contains(middlePoint2));
		assertTrue(points.contains(bottomPoint1));
		assertTrue(points.contains(bottomPoint2));
		assertEquals(6,points.size());
		AnnotatedShape annotations = cleanedPolygon.getAnnotatedShape();
		assertTrue(annotations.getAnnotationForShapes(middlePoint1).contains(PointType.HARD_CORNER));
		assertTrue(annotations.getAnnotationForShapes(middlePoint2).contains(PointType.SOFT_POINT)); //XXX wrong
		assertTrue(annotations.getAnnotationForShapes(topPoint2).contains(PointType.END_POINT));
		assertTrue(annotations.getAnnotationForShapes(bottomPoint2).contains(PointType.SOFT_POINT));
		assertTrue(annotations.getAnnotationForShapes(topPoint1).contains(PointType.SOFT_POINT));
		assertEquals(1,cleanedPolygon.getMultiLines().size());
		assertEquals(1,annotations.getShapesForAnnotation(LineType.INFLECTION_POINT).size());
//		assertTrue(annotations.getAnnotationForShapes(bottomPoint1).contains(PointType.HARD_CORNER));
//		assertEquals(fileName,vectorizer.getMatchingOH());
	}
	
	@Override
	public void testW() {
		super.testW();
		Polygon polygon = vectorizer.getPolygon();
		List<MultiLine> multiLInes = polygon.getMultiLines();
		assertEquals(1, multiLInes.size());
		AnnotatedShape annotations = polygon.getAnnotatedShape();
		Set<GeometricShape2D> hardPoints = annotations.getShapesForAnnotation(PointType.HARD_CORNER);
		assertEquals(3, hardPoints.size());
		System.out.println(hardPoints);
		Set<GeometricShape2D> inflectionPoints = annotations.getShapesForAnnotation(LineType.INFLECTION_POINT);
		assertEquals(2, inflectionPoints.size());
	}

	
}
