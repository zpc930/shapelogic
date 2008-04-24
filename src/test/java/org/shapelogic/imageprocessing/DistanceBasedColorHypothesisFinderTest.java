package org.shapelogic.imageprocessing;

import static org.shapelogic.imageutil.ImageUtil.runPluginFilterOnBufferedImage;

import org.shapelogic.imageutil.SLImage;

/** Test DistanceBasedColorHypothesisFinder.<br />
 * 
 * @author Sami Badawi
 *
 */
public class DistanceBasedColorHypothesisFinderTest  extends AbstractImageProcessingTests {
	DistanceBasedColorHypothesisFinder _colorHypothesisFinder = new DistanceBasedColorHypothesisFinder();
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		_dirURL = "./src/test/resources/images/particles";
		_fileFormat = ".gif";
	}
	
	public void testShortVerticalGif() {
		String fileName = "oneWhitePixelGray";
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName), _colorHypothesisFinder);
		assertEquals(1,bp.getWidth());
		assertTrue(bp.isInvertedLut());
		int pixel = bp.get(0,0);
		assertEquals(0,pixel); //So this is a white background pixel
	}


	public void te_stSpot1Clean() {
		String fileName = "spot1Clean";
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName,".png"), _colorHypothesisFinder);
		assertEquals(30,bp.getWidth());
		assertTrue(bp.isRgb());
		int pixel = bp.get(0,0);
		assertEquals(0xffffff,pixel); //So this is a white background pixel
	}

}
