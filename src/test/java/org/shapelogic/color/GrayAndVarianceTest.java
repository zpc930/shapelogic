package org.shapelogic.color;

import org.shapelogic.imageutil.IJImage;
import org.shapelogic.imageutil.PixelAreaHandler;

import junit.framework.TestCase;

/** Test GrayAndVariance.<br />
 * 
 * @author Sami Badawi
 *
 */
public class GrayAndVarianceTest extends TestCase {
	protected static final int dummyX = 0;
	protected static final int dummyY = 0;
	String _dir = "./src/test/resources/images/particles";
	String _fileFormat = ".png";
	
	
	public void testGrayAndVariance() {
		GrayAndVariance grayAndVariance = new GrayAndVariance();
		grayAndVariance.putPixel(dummyX, dummyY, 0);
		grayAndVariance.putPixel(dummyX, dummyY, 100);
		assertEquals(50,grayAndVariance.getMeanColor());
	}
	
	public void testAreaOneWhitePixelGray() {
		GrayAndVariance grayAndVariance = new GrayAndVariance();
		IJImage image = new IJImage(_dir,"oneWhitePixelGray",".gif");
		PixelAreaHandler aggregator = new PixelAreaHandler(image);
		aggregator.handlePixelArea(grayAndVariance, 0, 0, 1, 1);
		assertEquals(1,grayAndVariance.getArea());
		assertEquals(0,grayAndVariance.getMeanColor());
		assertEquals(0.,grayAndVariance.getStandardDeviation());
	}
	
	public void testWholeOneWhitePixelGray() {
		GrayAndVariance grayAndVariance = new GrayAndVariance();
		PixelAreaHandler aggregator = new PixelAreaHandler(_dir,"oneWhitePixelGray",".gif");
		aggregator.handleAllPixels(grayAndVariance);
		assertEquals(1,grayAndVariance.getArea());
		assertEquals(0,grayAndVariance.getMeanColor());
		assertEquals(0.,grayAndVariance.getStandardDeviation());
	}
	
	public void testAreaSpot1Clean() {
		GrayAndVariance grayAndVariance = new GrayAndVariance();
		PixelAreaHandler aggregator = new PixelAreaHandler(_dir,"spot1Clean",".jpg");
		aggregator.handlePixelArea(grayAndVariance, 0, 0, 30, 30);
		assertEquals(900,grayAndVariance.getArea());
		assertEquals(205,grayAndVariance.getMeanColor());
		assertEquals(99,(int)grayAndVariance.getStandardDeviation());
	}
	
	public void testAllSpot1Clean() {
		GrayAndVariance grayAndVariance = new GrayAndVariance();
		PixelAreaHandler aggregator = new PixelAreaHandler(_dir,"spot1Clean",".jpg");
		aggregator.handleAllPixels(grayAndVariance);
		assertEquals(900,grayAndVariance.getArea());
		assertEquals(205,grayAndVariance.getMeanColor());
		assertEquals(99,(int)grayAndVariance.getStandardDeviation());
	}
	
	public void testFirstLineOneWhitePixelGray() {
		GrayAndVariance grayAndVariance = new GrayAndVariance();
		PixelAreaHandler aggregator = new PixelAreaHandler(_dir,"spot1Clean",".jpg");
		aggregator.handlePixelArea(grayAndVariance, 0, 0, 30, 1);
		assertEquals(30,grayAndVariance.getArea());
		assertEquals(255,grayAndVariance.getMeanColor());
		assertEquals(0.,grayAndVariance.getStandardDeviation());
	}
	
}
