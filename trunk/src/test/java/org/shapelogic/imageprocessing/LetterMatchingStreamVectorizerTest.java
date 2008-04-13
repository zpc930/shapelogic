package org.shapelogic.imageprocessing;

import ij.process.ImageProcessor;

import java.util.Set;

import org.shapelogic.polygon.AnnotatedShape;
import org.shapelogic.polygon.GeometricShape2D;
import org.shapelogic.polygon.Polygon;
import org.shapelogic.util.LineType;
import org.shapelogic.util.PointType;

import static org.shapelogic.imageprocessing.ImageUtil.runPluginFilterOnImage;

/** LetterMatchingStreamVectorizerTest unit test for letter matching for StreamVectorizer.
 * <br />  
 * The main letter matching and vectorization algorithm, 
 * that works for both straight and curved capital letters
 * Note that this is sub classed from another unit test for more basic tests 
 * that only work for straight letters  
 * 
 * @author Sami Badawi
 *
 */
public class LetterMatchingStreamVectorizerTest extends BaseLetterMatchingMaxDistanceVectorizerTests {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		vectorizer = new StreamVectorizer();
	}
	
	public void testABC() {
		String fileName = "ABC";
		ImageProcessor bp = runPluginFilterOnImage(filePath(fileName), vectorizer);
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Polygon polygon = vectorizer.getPolygon();
		Polygon improvedPolygon = polygon.improve(); 
		AnnotatedShape annotations = improvedPolygon.getAnnotatedShape();
		Set<GeometricShape2D> endPoints = annotations.getShapesForAnnotation(PointType.END_POINT);
		System.out.println("End points: " + endPoints);
//		assertEquals(2, endPoints.size());
		Set<GeometricShape2D> inflectionPoints = annotations.getShapesForAnnotation(LineType.INFLECTION_POINT);
		assertEmptyCollection(inflectionPoints);
		printAnnotaions(polygon);
		Polygon cleanedPolygon = vectorizer.getCleanedupPolygon();
		assertEquals("A; B; C",vectorizer.getMatchingOH());
	}

}
