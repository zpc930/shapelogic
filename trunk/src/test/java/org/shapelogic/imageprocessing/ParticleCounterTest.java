package org.shapelogic.imageprocessing;

import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

import static org.shapelogic.imageprocessing.ImageUtil.runPluginFilterOnImage;

/** Test SBSegment.
 * <br />
 * 
 * @author Sami Badawi
 *
 */
public class ParticleCounterTest extends AbstractImageProcessingTests {
	ParticleCounter _segmenter = new ParticleCounter();
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		_dirURL = "./src/test/resources/images/particles";
		_fileFormat = ".gif";
	}
	
	public void testShortVerticalGif() {
		String fileName = "oneWhitePixelGray";
		ImageProcessor bp = runPluginFilterOnImage(filePath(fileName), _segmenter);
		assertEquals(1,bp.getWidth());
		assertTrue(bp instanceof ByteProcessor);
		assertTrue(bp.isInvertedLut());
		int pixel = bp.get(0,0);
		assertEquals(0,pixel); //So this is a white background pixel
		PixelAreaFactory factory = _segmenter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
		assertEquals(1,factory.getStore().size());
		assertTrue(_segmenter.isParticleImage());
	}

	/** This shows that when you save and image as PNG it will always be opened 
	 * as a color image.
	 */
	public void testShortVerticalPng() {
		String fileName = "oneWhitePixelGray";
		ImageProcessor bp = runPluginFilterOnImage(filePath(fileName,".png"), _segmenter);
		assertEquals(1,bp.getWidth());
		assertTrue(bp instanceof ColorProcessor);
		int pixel = bp.get(0,0);
		assertEquals(-1,pixel);
		PixelAreaFactory factory = _segmenter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
		assertEquals(1,factory.getStore().size());
		assertTrue(_segmenter.isParticleImage());
	}

	public void testBlobsGif() {
		String fileName = "blobs";
		ImageProcessor bp = runPluginFilterOnImage(filePath(fileName), _segmenter);
		assertEquals(256,bp.getWidth());
		assertEquals(65024,bp.getPixelCount());
		assertTrue(bp instanceof ByteProcessor);
		int pixel = bp.get(0,0);
		assertEquals(40,pixel);
		PixelAreaFactory factory = _segmenter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
		assertEquals(9761,factory.getStore().size()); 
		assertFalse(_segmenter.isParticleImage()); //XXX should be changed to true
		assertTrue(bp.isInvertedLut());
	}

}
