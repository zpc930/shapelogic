package org.shapelogic.imageprocessing;

import java.util.List;
import org.shapelogic.color.ColorHypothesis;
import org.shapelogic.color.IColorRange;
import static org.shapelogic.imageutil.ImageUtil.runPluginFilterOnBufferedImage;

import org.shapelogic.imageutil.SLImage;

/** Test DistanceBasedColorHypothesisFinder.<br />
 * 
 * @author Sami Badawi
 *
 */
public class DistanceBasedColorHypothesisFinderTest  extends AbstractImageProcessingTests {
	DistanceBasedColorHypothesisFinder _colorHypothesisFinder;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		_dirURL = "./src/test/resources/images/particles";
		_fileFormat = ".gif";
        _colorHypothesisFinder = new DistanceBasedColorHypothesisFinder();
	}
	
	public void testOneWhitePixelGrayGif() {
		String fileName = "oneWhitePixelGray";
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName), _colorHypothesisFinder);
		assertEquals(1,bp.getWidth());
		assertTrue(bp.isInvertedLut());
		int pixel = bp.get(0,0);
		assertEquals(0,pixel); //So this is a white background pixel
		assertEquals(1,_colorHypothesisFinder.getColorHypothesis().getColors().size()); 
	}

	public void testSpot1Clean() {
		String fileName = "spot1Clean";
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName,".png"), _colorHypothesisFinder);
		assertEquals(30,bp.getWidth());
		assertTrue(bp.isRgb());
		int pixel = bp.get(0,0);
		assertEquals(0xffffff,pixel); //So this is a white background pixel
        //So this is a white background pixel
        ColorHypothesis colorHypothesis = _colorHypothesisFinder.getColorHypothesis();
		assertEquals(2,colorHypothesis.getColors().size()); 
        assertNotNull(colorHypothesis.getBackground());
	}

	public void testSpot1CleanJpg() {
		String fileName = "spot1Clean";
//		_colorHypothesisFinder.setMaxDistance(50);
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName,".jpg"), _colorHypothesisFinder);
        assertEquals(30,bp.getWidth());
		assertTrue(bp.isRgb());
		int pixel = bp.get(0,0);
		assertEquals(0xffffff,pixel); //So this is a white background pixel
		assertEquals(2,_colorHypothesisFinder.getColorHypothesis().getColors().size()); 
	}

	public void testSpot1Noise5Jpg() {
		String fileName = "spot1Noise5";
//		_colorHypothesisFinder.setMaxDistance(50);
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName,".jpg"), _colorHypothesisFinder);
        assertEquals(30,bp.getWidth());
		assertTrue(bp.isRgb());
		int pixel = bp.get(0,0);
		assertEquals(16382457,pixel); //So this is a white background pixel
		assertEquals(2,_colorHypothesisFinder.getColorHypothesis().getColors().size());
        List<IColorRange> colors = (List)_colorHypothesisFinder.getColorHypothesis().getColors();
		assertEquals(5,colors.get(0).getColorChannels()[0]); 
		assertEquals(249,colors.get(1).getColorChannels()[0]); 
	}

	public void testSpot1Noise5Jpg2Iterations() {
		String fileName = "spot1Noise5";
        _colorHypothesisFinder.setIterations(2);
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName,".jpg"), _colorHypothesisFinder);
        assertEquals(30,bp.getWidth());
		assertTrue(bp.isRgb());
		int pixel = bp.get(0,0);
		assertEquals(16382457,pixel); //So this is a white background pixel
		assertEquals(2,_colorHypothesisFinder.getColorHypothesis().getColors().size());
        List<IColorRange> colors = (List)_colorHypothesisFinder.getColorHypothesis().getColors();
		assertEquals(6,colors.get(0).getColorChannels()[0]); 
		assertEquals(250,colors.get(1).getColorChannels()[0]); 
	}

	public void testSpot1Noise5Jpg3Iterations() {
		String fileName = "spot1Noise5";
        _colorHypothesisFinder.setIterations(3);
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName,".jpg"), _colorHypothesisFinder);
        assertEquals(30,bp.getWidth());
		assertTrue(bp.isRgb());
		int pixel = bp.get(0,0);
		assertEquals(16382457,pixel); //So this is a white background pixel
		assertEquals(2,_colorHypothesisFinder.getColorHypothesis().getColors().size());
        List<IColorRange> colors = (List)_colorHypothesisFinder.getColorHypothesis().getColors();
		assertEquals(6,colors.get(0).getColorChannels()[0]); 
		assertEquals(250,colors.get(1).getColorChannels()[0]); 
	}

	public void testSpot1Noise10Jpg() {
		String fileName = "spot1Noise10";
		_colorHypothesisFinder.setMaxDistance(50);
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName,".jpg"), _colorHypothesisFinder);
        assertEquals(30,bp.getWidth());
		assertTrue(bp.isRgb());
		int pixel = bp.get(0,0);
		assertEquals(16645629,pixel); //So this is a white background pixel
		assertEquals(2,_colorHypothesisFinder.getColorHypothesis().getColors().size()); 
	}

	public void testSpot1Noise10MoreIterationsJpg() {
		String fileName = "spot1Noise10";
		_colorHypothesisFinder.setMaxDistance(35);
        _colorHypothesisFinder.setIterations(3);
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName,".jpg"), _colorHypothesisFinder);
        assertEquals(30,bp.getWidth());
		assertTrue(bp.isRgb());
		int pixel = bp.get(0,0);
		assertEquals(16645629,pixel); //So this is a white background pixel
		assertEquals(3,_colorHypothesisFinder.getColorHypothesis().getColors().size()); 
	}

}
