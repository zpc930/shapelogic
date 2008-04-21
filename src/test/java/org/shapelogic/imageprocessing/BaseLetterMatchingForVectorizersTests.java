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
public abstract class BaseLetterMatchingForVectorizersTests extends
		AbstractImageProcessingTests {

//	MaxDistanceVectorizer directionBasedVectorizer = new MaxDistanceVectorizer();
	BaseVectorizer vectorizer;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		_dirURL = "./src/test/resources/images/smallThinLetters";
		_fileFormat = ".gif";
	}
	
	public void testA() {
		String fileName = "A";
		SLImage bp =runPluginFilterOnImage(filePath(fileName), vectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Set<IPoint2D> points = (Set<IPoint2D>)vectorizer.getCleanedupPolygon().getPoints();
		assertEquals(5,points.size());
		printPoints(vectorizer.getPolygon());
		System.out.println("cleaned up");
		printPoints(vectorizer.getCleanedupPolygon());
		IPoint2D topPoint1 = new CPointDouble(13.702127659574469,2.4787234042553186); 
		CPointInt middlePoint1 = new CPointInt(7,17); 
		CPointInt middlePoint2 = new CPointInt(22,17); 
		CPointInt bottomPoint1 = new CPointInt(1,28); 
		CPointInt bottomPoint2 = new CPointInt(28,28);
//		assertTrue(points.contains(topPoint1));
		assertTrue(points.contains(middlePoint1));
		assertTrue(points.contains(middlePoint2));
		assertTrue(points.contains(bottomPoint1));
		assertTrue(points.contains(bottomPoint2));
		assertEquals(fileName,vectorizer.getMatchingOH());
	}

	public void testE() {
		String fileName = "E";
		SLImage bp =runPluginFilterOnImage(filePath(fileName), vectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Set<IPoint2D> points = (Set<IPoint2D>)vectorizer.getPoints();
		printPoints(vectorizer.getPolygon());
		System.out.println("cleaned up");
		printPoints(vectorizer.getCleanedupPolygon());
		CPointInt topPoint1 = new CPointInt(1,1); 
		CPointInt topPoint2 = new CPointInt(18,1); 
		CPointInt middlePoint1 = new CPointInt(1,14); 
		CPointInt middlePoint2 = new CPointInt(14,14); 
		CPointInt bottomPoint1 = new CPointInt(1,28); 
		CPointInt bottomPoint2 = new CPointInt(20,28);
		assertTrue(points.contains(topPoint1));
		assertTrue(points.contains(topPoint2));
		assertTrue(points.contains(middlePoint1));
		assertTrue(points.contains(middlePoint2));
		assertTrue(points.contains(bottomPoint1));
		assertTrue(points.contains(bottomPoint2));
		assertEquals(6,points.size());
		assertEquals(fileName,vectorizer.getMatchingOH());
//		System.out.println("fileName: " + fileName);
//		printPoints(directionBasedVectorizer.getPolygon());
//		System.out.println("cleaned up");
//		printPoints(directionBasedVectorizer.getCleanedupPolygon());
//		System.out.println("----------------");
	}

	public void testF() {
		String fileName = "F";
		SLImage bp =runPluginFilterOnImage(filePath(fileName), vectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Set<IPoint2D> points = (Set<IPoint2D>)vectorizer.getPoints();
		CPointInt topPoint1 = new CPointInt(1,1); 
		CPointInt topPoint2 = new CPointInt(18,1); 
		CPointInt middlePoint1 = new CPointInt(1,14); 
		CPointInt middlePoint2 = new CPointInt(14,14); 
		CPointInt bottomPoint1 = new CPointInt(1,28); 
		assertTrue(points.contains(topPoint1));
		assertTrue(points.contains(topPoint2));
		assertTrue(points.contains(middlePoint1));
		assertTrue(points.contains(middlePoint2));
		assertTrue(points.contains(bottomPoint1));

		assertEquals(5,points.size());
		assertEquals(fileName,vectorizer.getMatchingOH());
	}

	public void testH() {
		String fileName = "H";
		SLImage bp =runPluginFilterOnImage(filePath(fileName), vectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Set<IPoint2D> points = (Set<IPoint2D>)vectorizer.getPoints();
		CPointInt topPoint1 = new CPointInt(1,1); 
		CPointInt topPoint2 = new CPointInt(18,1); 
		CPointInt middlePoint1 = new CPointInt(1,14); 
		CPointInt middlePoint2 = new CPointInt(18,14); 
		CPointInt bottomPoint1 = new CPointInt(1,28); 
		CPointInt bottomPoint2 = new CPointInt(18,28);
		assertTrue(points.contains(topPoint1));
		assertTrue(points.contains(topPoint2));
		assertTrue(points.contains(middlePoint1));
		assertTrue(points.contains(middlePoint2));
		assertTrue(points.contains(bottomPoint1));
		assertTrue(points.contains(bottomPoint2));
		assertEquals(6,points.size());
		assertEquals(fileName,vectorizer.getMatchingOH());
	}
	public void testI() {
		String fileName = "I";
		SLImage bp =runPluginFilterOnImage(filePath(fileName), vectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Set<IPoint2D> points = (Set<IPoint2D>)vectorizer.getPoints();
		CPointInt topPoint1 = new CPointInt(14,1); 
		CPointInt bottomPoint1 = new CPointInt(14,28); 
		assertTrue(points.contains(topPoint1));
		assertTrue(points.contains(bottomPoint1));
		assertEquals(2,points.size());
		assertEquals(fileName,vectorizer.getMatchingOH());
	}
	
	public void testK() {
		String fileName = "K";
		SLImage bp =runPluginFilterOnImage(filePath(fileName), vectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Set<IPoint2D> points = (Set<IPoint2D>)vectorizer.getCleanedupPolygon().getPoints();
		CPointInt topPoint1 = new CPointInt(1,1); 
		CPointInt topPoint2 = new CPointInt(16,2); 
		CPointInt middlePoint1 = new CPointInt(2,14); 
		CPointInt bottomPoint1 = new CPointInt(1,28); 
		CPointInt bottomPoint2 = new CPointInt(15,28);
		printPoints(vectorizer.getPolygon());
		assertTrue(points.contains(topPoint1));
		assertTrue(points.contains(topPoint2));
		assertTrue(points.contains(middlePoint1));
		assertTrue(points.contains(bottomPoint1));
		assertTrue(points.contains(bottomPoint2));
		assertEquals(5,points.size());
		assertEquals(fileName,vectorizer.getMatchingOH());
	}

	public void testL() {
		String fileName = "L";
		SLImage bp =runPluginFilterOnImage(filePath(fileName), vectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Set<IPoint2D> points = (Set<IPoint2D>)vectorizer.getPoints();
		CPointInt topPoint1 = new CPointInt(1,1); 
		CPointInt bottomPoint1 = new CPointInt(1,28); 
		CPointInt bottomPoint2 = new CPointInt(20,28);
		assertTrue(points.contains(topPoint1));
		assertTrue(points.contains(bottomPoint1));
		assertTrue(points.contains(bottomPoint2));
		assertEquals(3,points.size());
		assertEquals(fileName,vectorizer.getMatchingOH());
	}
		
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
//		assertTrue(points.contains(topPoint1));
		assertTrue(points.contains(bottomPoint1));
		assertTrue(points.contains(topPoint2)); 
		assertTrue(points.contains(bottomPoint2));
//		assertTrue(points.contains(middlePoint1)); 
		assertEquals(fileName,vectorizer.getMatchingOH());
	}

	public void testN() {
		String fileName = "N";
		SLImage bp =runPluginFilterOnImage(filePath(fileName), vectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Set<IPoint2D> points = (Set<IPoint2D>)vectorizer.getCleanedupPolygon().getPoints();
		CPointInt topPoint1 = new CPointInt(1,1); 
		CPointInt bottomPoint1 = new CPointInt(1,28); 
		CPointInt topPoint2 = new CPointInt(28,1); 
		CPointInt bottomPoint2 = new CPointInt(28,28);
		printPoints(vectorizer.getPolygon());
//		assertTrue(points.contains(topPoint1));
		assertTrue(points.contains(bottomPoint1));
		assertTrue(points.contains(topPoint2));
//		assertTrue(points.contains(bottomPoint2));
		assertEquals(4,points.size()); 
		assertEquals(fileName,vectorizer.getMatchingOH());
	}

	public void testT() {
		String fileName = "T";
		SLImage bp =runPluginFilterOnImage(filePath(fileName), vectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Set<IPoint2D> points = (Set<IPoint2D>)vectorizer.getCleanedupPolygon().getPoints();
		CPointInt topPoint1 = new CPointInt(1,1); 
		CPointInt topPointMiddle = new CPointInt(14,1); 
		CPointInt topPoint2 = new CPointInt(28,1); 
		CPointInt bottomPoint1 = new CPointInt(14,28); 
		printPoints(vectorizer.getPolygon());
		assertTrue(points.contains(topPoint1));
		assertTrue(points.contains(bottomPoint1));
		assertTrue(points.contains(topPoint2));
		assertTrue(points.contains(topPointMiddle));
		assertEquals(4,points.size()); 
		assertEquals(fileName,vectorizer.getMatchingOH());
	}

	public void testV() {
		String fileName = "V";
		SLImage bp =runPluginFilterOnImage(filePath(fileName), vectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Set<IPoint2D> points = (Set<IPoint2D>)vectorizer.getCleanedupPolygon().getPoints();
		CPointInt topPoint1 = new CPointInt(1,1); 
		CPointInt bottomPoint1 = new CPointInt(14,28); 
		CPointInt topPoint2 = new CPointInt(28,1); 
		printPoints(vectorizer.getPolygon());
		assertTrue(points.contains(topPoint1));
//		assertTrue(points.contains(bottomPoint1)); //XXX enable
		assertTrue(points.contains(topPoint2));
		assertEquals(3,points.size()); 
		assertEquals(fileName,vectorizer.getMatchingOH());
	}

	public void testW() {
		String fileName = "W";
		SLImage bp =runPluginFilterOnImage(filePath(fileName), vectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Set<IPoint2D> points = (Set<IPoint2D>)vectorizer.getCleanedupPolygon().getPoints();
		CPointInt topPoint1 = new CPointInt(1,1); 
		CPointInt bottomPoint1 = new CPointInt(14,28); 
		CPointInt topPoint2 = new CPointInt(28,1); 
		printPoints(vectorizer.getPolygon());
		assertTrue(points.contains(topPoint1));
//		assertTrue(points.contains(bottomPoint1)); //XXX enable
//		assertTrue(points.contains(topPoint2));
//		assertEquals(5,points.size()); 
//		assertEquals(fileName,directionBasedVectorizer.getMatchingOH());
	}

	public void testX() {
		String fileName = "X";
		SLImage bp =runPluginFilterOnImage(filePath(fileName), vectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Set<IPoint2D> points = (Set<IPoint2D>)vectorizer.getCleanedupPolygon().getPoints();
		CPointInt topPoint1 = new CPointInt(1,1); 
		CPointInt bottomPoint1 = new CPointInt(1,28); 
		CPointInt topPoint2 = new CPointInt(28,1); 
		CPointInt bottomPoint2 = new CPointInt(28,28);
		CPointInt centerPoint = new CPointInt(14,14); 
		printPoints(vectorizer.getPolygon());
		assertTrue(points.contains(topPoint1));
//		assertTrue(points.contains(bottomPoint1));
//		assertTrue(points.contains(topPoint2));
//		assertTrue(points.contains(bottomPoint2));
//		assertEquals(5,points.size());
//		assertEquals(fileName,directionBasedVectorizer.getMatchingOH());
	}

	public void testY() {
		String fileName = "Y";
		SLImage bp =runPluginFilterOnImage(filePath(fileName), vectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Set<IPoint2D> points = (Set<IPoint2D>)vectorizer.getCleanedupPolygon().getPoints();
		CPointInt topPoint1 = new CPointInt(1,1); 
		CPointInt bottomPoint1 = new CPointInt(14,28); 
		CPointInt topPoint2 = new CPointInt(28,1); 
		CPointInt centerPoint = new CPointInt(14,14); 
		printPoints(vectorizer.getPolygon());
		assertTrue(points.contains(topPoint1));
		assertTrue(points.contains(bottomPoint1));
		assertTrue(points.contains(topPoint2));
		assertTrue(points.contains(centerPoint));
		assertEquals(4,points.size());
		assertEquals(fileName,vectorizer.getMatchingOH());
	}

	public void testZ() {
		String fileName = "Z";
		SLImage bp =runPluginFilterOnImage(filePath(fileName), vectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Set<IPoint2D> points = (Set<IPoint2D>)vectorizer.getCleanedupPolygon().getPoints();
		CPointInt topPoint1 = new CPointInt(1,1); 
		CPointInt bottomPoint1 = new CPointInt(1,28); 
		CPointInt topPoint2 = new CPointInt(28,1); 
		CPointInt bottomPoint2 = new CPointInt(28,28);
		printPolygon(vectorizer.getPolygon());
		System.out.println("Cleaned up lines");
		printPolygon(vectorizer.getCleanedupPolygon());
		printAnnotaions(vectorizer.getCleanedupPolygon());
		assertTrue(points.contains(topPoint1));
//		assertTrue(points.contains(bottomPoint1));
//		assertTrue(points.contains(topPoint2));
		assertTrue(points.contains(bottomPoint2));
		assertEquals(4,points.size());
		assertEquals(fileName,vectorizer.getMatchingOH());
	}

}
