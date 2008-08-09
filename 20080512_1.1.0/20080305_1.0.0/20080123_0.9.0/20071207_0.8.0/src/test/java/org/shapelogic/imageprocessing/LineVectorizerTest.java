package org.shapelogic.imageprocessing;

import ij.process.ByteProcessor;

import java.util.Collection;

import org.shapelogic.polygon.CLine;
import org.shapelogic.polygon.IPoint2D;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class LineVectorizerTest extends AbstractImageProcessingTests {
	LineVectorizer lineVectorizer = new LineVectorizer();
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		dirURL = "./src/test/resources/images/smallThinShapes";
		fileFormat = ".gif";
	}

	public void testShortVertical() {
		String fileName = "vertical";
		ByteProcessor bp = runPluginFilterOnImage(filePath(fileName), lineVectorizer);
		assertEquals(20,bp.getWidth());
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Collection<IPoint2D> points = ((LineVectorizer)lineVectorizer).getPoints();
		assertEquals(2, points.size());
		Collection<CLine> lines = ((LineVectorizer)lineVectorizer).getPolygon().getLines();
		assertEquals(1, lines.size());
	}

	public void testShortVerticalAndHorizontal() {
		String fileName = "verticalAndHorizontal";
		ByteProcessor bp = runPluginFilterOnImage(filePath(fileName), lineVectorizer);
		assertEquals(20,bp.getWidth());
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Collection<IPoint2D> points = ((LineVectorizer)lineVectorizer).getPoints();
		assertEquals(3, points.size());
		Collection<CLine> lines = ((LineVectorizer)lineVectorizer).getPolygon().getLines();
		assertEquals(2, lines.size());
	}

	public void testShortRotatedTThin() {
		String fileName = "rotatedT";
		ByteProcessor bp = runPluginFilterOnImage(filePath(fileName), lineVectorizer);
		assertEquals(20,bp.getWidth());
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		assertEquals(2, lineVectorizer.countRegionCrossingsAroundPoint(lineVectorizer.pointToPixelIndex(1,1)));
		assertEquals(4, lineVectorizer.countRegionCrossingsAroundPoint(lineVectorizer.pointToPixelIndex(1,2)));
		assertEquals(4, lineVectorizer.countRegionCrossingsAroundPoint(lineVectorizer.pointToPixelIndex(1,7)));
		//T-junction
		assertEquals(6, lineVectorizer.countRegionCrossingsAroundPoint(lineVectorizer.pointToPixelIndex(1,8))); 
		Collection<IPoint2D> points = ((LineVectorizer)lineVectorizer).getPoints();
		assertEquals(4, points.size());
		Collection<CLine> lines = ((LineVectorizer)lineVectorizer).getPolygon().getLines();
		assertEquals(3, lines.size());
	}
	
	public void testThinProblematicL() {
		String fileName = "problematicL";
		ByteProcessor bp = runPluginFilterOnImage(filePath(fileName), lineVectorizer);
		assertEquals(20,bp.getWidth());
		int pixel = bp.get(0,0);
		assertEquals(PixelType.BACKGROUND_POINT.color,pixel);
		Collection<IPoint2D> points = ((LineVectorizer)lineVectorizer).getPoints();
		assertEquals(3, points.size());
		Collection<CLine> lines = ((LineVectorizer)lineVectorizer).getPolygon().getLines();
		assertEquals(2, lines.size());
//		assertEquals("L",lineVectorizer.getMatchingOH());
	}
	
}
