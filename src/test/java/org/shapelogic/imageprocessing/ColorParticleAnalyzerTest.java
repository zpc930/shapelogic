package org.shapelogic.imageprocessing;

import org.shapelogic.color.ValueAreaFactory;
import org.shapelogic.imageutil.SLImage;

import static org.shapelogic.imageutil.ImageUtil.runPluginFilterOnBufferedImage;

/** Test ParticleCounter. <br />
 * 
 * The difference of this from BaseParticleCounterTest is that this should test
 * more customized ParticleCounters, while BaseParticleCounterTest should test
 * the basic cases.
 * 
 * @author Sami Badawi
 *
 */
public class ColorParticleAnalyzerTest extends AbstractImageProcessingTests {
	BaseParticleCounter _particleCounter;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		_dirURL = "./src/test/resources/images/particles";
		_fileFormat = ".gif";
        _particleCounter = new ColorParticleAnalyzer();
	}
	
	public void testWhitePixelGray() {
		String fileName = "oneWhitePixelGray";
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName), _particleCounter);
		assertEquals(1,bp.getWidth());
		assertTrue(bp.isInvertedLut());
		int pixel = bp.get(0,0);
		assertEquals(0,pixel); //So this is a white background pixel
		ValueAreaFactory factory = _particleCounter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
		assertEquals(1,factory.getStore().size());
		assertTrue(_particleCounter.isParticleImage());
	}

	/** This shows that when you save and image as PNG it will always be opened 
	 * as a color image.
	 */
	public void testOneWhitePixelGrayPng() {
		String fileName = "oneWhitePixelGray";
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName,".png"), _particleCounter);
		assertTrue(bp.isEmpty());
	}

	public void testBlobsGif() {
		String fileName = "blobs";
        _particleCounter.setMaxDistance(100);
        _particleCounter.setMinPixelsInArea(7);
        _particleCounter.setIterations(3);
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName), _particleCounter);
		assertEquals(256,bp.getWidth());
		assertEquals(65024,bp.getPixelCount());
		int pixel = bp.get(0,0);
		assertEquals(40,pixel);
		ValueAreaFactory factory = _particleCounter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
		assertEquals(65,factory.getStore().size()); 
//		assertTrue(_particleCounter.isParticleImage()); 
//		assertEquals(30,_particleCounter.getParticleCount()); 
//		assertTrue(bp.isInvertedLut());
	}

	public void testEmbryos() {
		String fileName = "embryos6";
        _particleCounter.setMaxDistance(100);
        _particleCounter.setMinPixelsInArea(20);
        _particleCounter.setIterations(3);
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName,".jpg"), _particleCounter);
		assertEquals(256,bp.getWidth());
		assertEquals(52480,bp.getPixelCount());
		int pixel = bp.get(0,0);
		assertEquals(12561501,pixel);
		ValueAreaFactory factory = _particleCounter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
		assertEquals(61,factory.getStore().size()); //XXX should be 2
		assertTrue(_particleCounter.isParticleImage()); 
		assertEquals(5,_particleCounter.getParticleCount()); 
	}

	public void testEmbryosWithParameters() {
		String fileName = "embryos6";
        _particleCounter.setMaxDistance(100);
        _particleCounter.setMinPixelsInArea(20);
        _particleCounter.setIterations(3);
        String parameters = "iterations=4 maxDistance=90 minPixelsInArea=70";
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName,".jpg"), _particleCounter,parameters);
		assertEquals(256,bp.getWidth());
		assertEquals(52480,bp.getPixelCount());
		int pixel = bp.get(0,0);
		assertEquals(12561501,pixel);
		ValueAreaFactory factory = _particleCounter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
//		assertEquals(383,factory.getStore().size()); //XXX should be 2
		assertTrue(_particleCounter.isParticleImage()); 
		assertEquals(5,_particleCounter.getParticleCount()); 
	}

	public void testEmbryosMoreIterations() {
		String fileName = "embryos6";
        _particleCounter.setMaxDistance(100);
        _particleCounter.setMinPixelsInArea(20);
        _particleCounter.setIterations(4);
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName,".jpg"), _particleCounter);
		assertEquals(256,bp.getWidth());
		assertEquals(52480,bp.getPixelCount());
		int pixel = bp.get(0,0);
		assertEquals(12561501,pixel);
		ValueAreaFactory factory = _particleCounter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
		assertEquals(66,factory.getStore().size()); 
		assertTrue(_particleCounter.isParticleImage()); 
		assertEquals(7,_particleCounter.getParticleCount()); 
	}

	/** This gets opened as a byte interleaved and not as an int RGB
	 */
	public void testCleanSpotPng() {
		String fileName = "spot1Clean";
		SLImage  bp = runPluginFilterOnBufferedImage(filePath(fileName,".png"), _particleCounter);
		assertEquals(30,bp.getWidth());
		assertEquals(900,bp.getPixelCount());
		int pixel = bp.get(0,0);
		assertEquals(0xffffff,pixel);
		ValueAreaFactory factory = _particleCounter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
		assertEquals(2,factory.getStore().size()); 
	}
}
