package org.shapelogic.imageprocessing;

import org.shapelogic.color.ValueAreaFactory;
import org.shapelogic.imageutil.SLImage;

import static org.shapelogic.imageutil.ImageUtil.runPluginFilterOnBufferedImage;

/** Test SBSegment.
 * <br />
 * 
 * @author Sami Badawi
 *
 */
public class BaseParticleCounterTest extends AbstractImageProcessingTests {
	BaseParticleCounter _baseParticleCounter;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
        _baseParticleCounter = new BaseParticleCounter();
		_dirURL = "./src/test/resources/images/particles";
		_fileFormat = ".gif";
	}
	
	public void testSpot1Noise5Jpg() {
		String fileName = "spot1Noise5";
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName,".jpg"), _baseParticleCounter);
		assertEquals(30,bp.getWidth());
//		assertTrue(bp.isInvertedLut());
		int pixel = bp.get(0,0);
		assertEquals(16382457,pixel); //So this is a white background pixel
		ValueAreaFactory factory = _baseParticleCounter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
		assertEquals(6,factory.getStore().size()); //XXX change to 2
		assertEquals(1,_baseParticleCounter.getParticleCount());
		assertTrue(_baseParticleCounter.isParticleImage());
	}

	public void testShortVerticalGif() {
		String fileName = "oneWhitePixelGray";
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName), _baseParticleCounter);
		assertEquals(1,bp.getWidth());
		assertTrue(bp.isInvertedLut());
		int pixel = bp.get(0,0);
		assertEquals(0,pixel); //So this is a white background pixel
		ValueAreaFactory factory = _baseParticleCounter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
		assertEquals(1,factory.getStore().size());
		assertTrue(_baseParticleCounter.isParticleImage());
	}

	/** This shows that when you save and image as PNG it will always be opened 
	 * as a color image.
	 */
	public void testShortVerticalPng() {
		String fileName = "oneWhitePixelGray";
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName,".png"), _baseParticleCounter);
		assertTrue(bp.isEmpty());
		if (true) return;
		assertEquals(1,bp.getWidth());
		int pixel = bp.get(0,0);
		assertEquals(0,pixel);
		ValueAreaFactory factory = _baseParticleCounter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
		assertEquals(1,factory.getStore().size());
		assertTrue(_baseParticleCounter.isParticleImage());
	}

	public void testBlobsGif() {
		String fileName = "blobs";
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName), _baseParticleCounter);
		assertEquals(256,bp.getWidth());
		assertEquals(65024,bp.getPixelCount());
		int pixel = bp.get(0,0);
		assertEquals(40,pixel);
		ValueAreaFactory factory = _baseParticleCounter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
		assertEquals(199,factory.getStore().size()); 
		assertFalse(_baseParticleCounter.isParticleImage()); //XXX should be changed to true
		assertTrue(bp.isInvertedLut());
	}

	/** This gets opened as a byte interleaved and not as an int RGB
	 */
	public void testCleanSpotPng() {
		String fileName = "spot1Clean";
		SLImage  bp = runPluginFilterOnBufferedImage(filePath(fileName,".png"), _baseParticleCounter);
		assertEquals(30,bp.getWidth());
		assertEquals(900,bp.getPixelCount());
		int pixel = bp.get(0,0);
		assertEquals(0xffffff,pixel);
		ValueAreaFactory factory = _baseParticleCounter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
		assertEquals(1,_baseParticleCounter.getParticleCount());
		assertEquals(2,factory.getStore().size()); 
	}

	public void testSpot1Noise5() {
		String fileName = "spot1Noise5";
		SLImage  bp = runPluginFilterOnBufferedImage(filePath(fileName,".jpg"), _baseParticleCounter);
		assertEquals(30,bp.getWidth());
		assertEquals(900,bp.getPixelCount());
		int pixel = bp.get(0,0);
		assertEquals(16382457,pixel);
		ValueAreaFactory factory = _baseParticleCounter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
		assertEquals(1,_baseParticleCounter.getParticleCount());
		assertEquals(6,factory.getStore().size()); 
	}

	public void testSpot1Noise10() {
		String fileName = "spot1Noise10";
		SLImage  bp = runPluginFilterOnBufferedImage(filePath(fileName,".jpg"), _baseParticleCounter);
		assertEquals(30,bp.getWidth());
		assertEquals(900,bp.getPixelCount());
		int pixel = bp.get(0,0);
		assertEquals(16645629,pixel);
		ValueAreaFactory factory = _baseParticleCounter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
		assertEquals(1,_baseParticleCounter.getParticleCount());
		assertEquals(19,factory.getStore().size()); 
	}
}
