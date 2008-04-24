package org.shapelogic.imageprocessing;

import org.shapelogic.color.ValueAreaFactory;
import org.shapelogic.imageutil.SLImage;

import static org.shapelogic.imageutil.ImageUtil.runPluginFilterOnBufferedImage;
import static org.shapelogic.imageutil.ImageUtil.runPluginFilterOnImage;

/** Test SBSegment.
 * <br />
 * 
 * @author Sami Badawi
 *
 */
public class SegmentTest extends AbstractImageProcessingTests {
	SegmentCounter _segmenter = new SegmentCounter(false);
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		_dirURL = "./src/test/resources/images/particles";
		_fileFormat = ".gif";
	}
	
	public void testShortVerticalGif() {
		String fileName = "oneWhitePixelGray";
		SLImage  bp = runPluginFilterOnBufferedImage(filePath(fileName), _segmenter);
		assertEquals(1,bp.getWidth());
//		assertTrue(bp instanceof ByteProcessor);
		int pixel = bp.get(0,0);
		assertEquals(0,pixel);
		ValueAreaFactory factory = _segmenter.getSegmentation().getSegmentAreaFactory();
		assertNull(factory);
//		assertEquals(1,factory.getStore().size());
	}

	/** This shows that when you save and image as PNG it will always be opened 
	 * as a color image.
	 */
	public void testShortVerticalPng() {
		String fileName = "oneWhitePixelGray";
		SLImage  bp = runPluginFilterOnBufferedImage(filePath(fileName), _segmenter);
		assertEquals(1,bp.getWidth());
//		assertTrue(bp instanceof ByteProcessor);
		int pixel = bp.get(0,0);
		assertEquals(0,pixel);
		ValueAreaFactory factory = _segmenter.getSegmentation().getSegmentAreaFactory();
		assertNull(factory);
//		assertEquals(1,factory.getStore().size());
	}

	public void testBlobsGif() {
		String fileName = "blobs";
		SLImage  bp = runPluginFilterOnBufferedImage(filePath(fileName), _segmenter);
		assertEquals(256,bp.getWidth());
		assertEquals(65024,bp.getPixelCount());
//		assertTrue(bp instanceof ByteProcessor);
		int pixel = bp.get(0,0);
		assertEquals(40,pixel);
		ValueAreaFactory factory = _segmenter.getSegmentation().getSegmentAreaFactory();
		assertNull(factory);
//		assertEquals(9761,factory.getStore().size()); 
	}

	/** This gets opened as a byte interleaved and not as an int RGB
	 */
	public void testCleanSpotPng() {
		String fileName = "spot1Clean";
		SLImage  bp = runPluginFilterOnBufferedImage(filePath(fileName,".png"), _segmenter);
		assertEquals(30,bp.getWidth());
		assertEquals(900,bp.getPixelCount());
		int pixel = bp.get(0,0);
		assertEquals(0xffffff,pixel);
		ValueAreaFactory factory = _segmenter.getSegmentation().getSegmentAreaFactory();
		assertNull(factory);
//		assertEquals(2,factory.getStore().size()); 
	}

}
