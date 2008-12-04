package org.shapelogic.imageprocessing;

import java.util.Set;

import org.shapelogic.imageutil.SLImage;
import org.shapelogic.polygon.CPointDouble;
import org.shapelogic.polygon.CPointInt;
import org.shapelogic.polygon.IPoint2D;

import static org.shapelogic.imageutil.ImageUtil.runPluginFilterOnImage;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class LetterMatchingDirectionBasedVectorizerTest extends BaseLetterMatchingForVectorizersTests {
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		vectorizer = new DirectionBasedVectorizer();
	}

	@Override
	public void testA() {
		String fileName = "A";
		SLImage bp =runPluginFilterOnImage(filePath(fileName), vectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Set<IPoint2D> points = (Set<IPoint2D>)vectorizer.getCleanedupPolygon().getPoints();
//		assertEquals(5,points.size());
		printPoints(vectorizer.getPolygon());
		System.out.println("cleaned up");
		printPoints(vectorizer.getCleanedupPolygon());
		IPoint2D topPoint1 = new CPointDouble(13.702127659574469,2.4787234042553186); 
		CPointInt middlePoint1 = new CPointInt(7,17); 
		CPointInt middlePoint2 = new CPointInt(22,17); 
		CPointInt bottomPoint1 = new CPointInt(1,28); 
		CPointInt bottomPoint2 = new CPointInt(28,28);
//		assertTrue(points.contains(topPoint1));
//		assertTrue(points.contains(middlePoint1));
//		assertTrue(points.contains(middlePoint2));
		assertTrue(points.contains(bottomPoint1));
		assertTrue(points.contains(bottomPoint2));
//		assertEquals(fileName,directionBasedVectorizer.getMatchingOH());
	}

	@Override
	public void testK() {
		String fileName = "K";
		SLImage bp =runPluginFilterOnImage(filePath(fileName), vectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Set<IPoint2D> points = (Set<IPoint2D>)vectorizer.getPoints();
		CPointInt topPoint1 = new CPointInt(1,1); 
		CPointInt topPoint2 = new CPointInt(16,2); 
		CPointInt middlePoint1 = new CPointInt(2,14); 
		CPointInt bottomPoint1 = new CPointInt(1,28); 
		CPointInt bottomPoint2 = new CPointInt(15,28);
		assertTrue(points.contains(topPoint1));
//		assertTrue(points.contains(topPoint2));
//		assertTrue(points.contains(middlePoint1));
		assertTrue(points.contains(bottomPoint1));
//		assertTrue(points.contains(bottomPoint2));
//		assertEquals(5,points.size());
//		assertEquals(fileName,directionBasedVectorizer.getMatchingOH());
	}

	@Override
	public void testM() {
		String fileName = "M";
		SLImage bp =runPluginFilterOnImage(filePath(fileName), vectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Set<IPoint2D> points = (Set<IPoint2D>)vectorizer.getCleanedupPolygon().getPoints();
		CPointInt topPoint1 = new CPointInt(1,1); 
		CPointInt bottomPoint1 = new CPointInt(1,28); 
		CPointInt topPoint2 = new CPointInt(25,2); //XXX 
		CPointInt bottomPoint2 = new CPointInt(25,28);
//		CPointInt middlePoint1 = new CPointInt(13,13);
		CPointInt middlePoint1 = new CPointInt(12,12);
		System.out.println("Raw points");
		printPoints(vectorizer.getPolygon());
		System.out.println("Cleaned up points");
		printPoints(vectorizer.getCleanedupPolygon());
		assertTrue(points.contains(topPoint1));
		assertTrue(points.contains(bottomPoint1));
//		assertTrue(points.contains(topPoint2)); 
		assertTrue(points.contains(bottomPoint2));
		assertTrue(points.contains(middlePoint1)); 
//		assertEquals(fileName,directionBasedVectorizer.getMatchingOH());
	}

}