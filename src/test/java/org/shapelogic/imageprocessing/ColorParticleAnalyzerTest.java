package org.shapelogic.imageprocessing;

import java.util.NoSuchElementException;

import org.shapelogic.color.IColorAndVariance;
import org.shapelogic.color.ValueAreaFactory;
import org.shapelogic.imageutil.SLImage;
import org.shapelogic.logic.CommonLogicExpressions;
import org.shapelogic.polygon.BBox;
import org.shapelogic.streamlogic.StreamNames;
import org.shapelogic.streams.NumberedStream;
import org.shapelogic.streams.StreamFactory;

import org.shapelogic.util.Headings;
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
	ColorParticleAnalyzer _particleCounter;
    String _dataFileDir;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		_dirURL = "./src/test/resources/images/particles";
		_fileFormat = ".gif";
        _particleCounter = new ColorParticleAnalyzerIJ();
        _particleCounter.setDisplayTable(false);
        _dataFileDir = "./src/test/resources/data/neuralnetwork";
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
		assertEquals(0,_particleCounter.getParticleFiltered().size());
		IColorAndVariance particle = factory.getStore().get(0);
		BBox bBox = particle.getPixelArea().getBoundingBox();
		assertEquals(0.,bBox.minVal.getX());
		assertEquals(0.,bBox.minVal.getY());
		assertEquals(0.,bBox.maxVal.getX());
		assertEquals(0.,bBox.maxVal.getY());
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
		assertTrue(_particleCounter.isParticleImage()); 
		assertEquals(62,_particleCounter.getParticleCount()); 
		assertTrue(bp.isInvertedLut());
		StreamFactory streamFactory = new StreamFactory(_particleCounter);
		NumberedStream<Number> ns = streamFactory.findNumberedStream(CommonLogicExpressions.ASPECT_RATIO);
		assertClose(0.9, ns.get(0).doubleValue(), 0.1);
		assertClose(2, ns.get(1).doubleValue(), 0.1);
		NumberedStream<String> letterStream = streamFactory.findNumberedStream(StreamNames.CATEGORY);
		assertEquals("Tall", letterStream.get(0));
		assertEquals("Flat", letterStream.get(1));
	}

	public void testBlobsGifToMask() {
		String fileName = "blobs";
        _particleCounter.setMaxDistance(100);
        _particleCounter.setMinPixelsInArea(7);
        _particleCounter.setIterations(3);
        _particleCounter.setToMask(true);
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName), _particleCounter);
		assertEquals(256,bp.getWidth());
		assertEquals(65024,bp.getPixelCount());
		int pixel = bp.get(0,0);
		assertEquals(0,pixel);
		ValueAreaFactory factory = _particleCounter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
		assertEquals(65,factory.getStore().size()); 
		assertTrue(_particleCounter.isParticleImage()); 
		assertEquals(62,_particleCounter.getParticleCount()); 
		assertTrue(bp.isInvertedLut());
		StreamFactory streamFactory = new StreamFactory(_particleCounter);
		NumberedStream<Number> ns = streamFactory.findNumberedStream(CommonLogicExpressions.ASPECT_RATIO);
		assertClose(0.9, ns.get(0).doubleValue(), 0.1);
		assertClose(2, ns.get(1).doubleValue(), 0.1);
		NumberedStream<String> letterStream = streamFactory.findNumberedStream(StreamNames.CATEGORY);
		assertEquals("Tall", letterStream.get(0));
		assertEquals("Flat", letterStream.get(1));
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
		assertEquals("Should have 5 particles for this setting.", 5,_particleCounter.getParticleCount());
	}
	
	public void testEmbryosDefaultSettings() {
		String fileName = "embryos6";
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName,".jpg"), _particleCounter);
		assertEquals(256,bp.getWidth());
		assertEquals(52480,bp.getPixelCount());
		int pixel = bp.get(0,0);
		assertEquals(12561501,pixel);
		ValueAreaFactory factory = _particleCounter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
		assertEquals(30,factory.getStore().size()); //XXX should be 2
		assertTrue(_particleCounter.isParticleImage());
		assertEquals("Should have 6 particles for this setting.", 6,_particleCounter.getParticleCount());
		StreamFactory streamFactory = new StreamFactory(_particleCounter);
		NumberedStream<Number> ns = streamFactory.findNumberedStream(CommonLogicExpressions.ASPECT_RATIO);
		assertClose(0.9, ns.get(0).doubleValue(), 0.1);
		assertClose(1., ns.get(1).doubleValue(), 0.1);
		NumberedStream<String> letterStream = streamFactory.findNumberedStream(StreamNames.CATEGORY);
		assertEquals("Dark round", letterStream.get(0));
		assertEquals("Dark round", letterStream.get(1));
		StringBuffer internalInfo = _particleCounter.getInternalInfo();
		assertTrue(500 < internalInfo.length());
        //Test color red for 2 first particles
		NumberedStream<Integer> redStream = streamFactory.findNumberedStream(StreamNames.COLOR_R);
		assertEquals(new Integer(105), redStream.get(0));
		assertEquals(new Integer(102), redStream.get(1));

        //Test min x bounding box for 2 first particles
		NumberedStream<Double> xMinStream = streamFactory.findNumberedStream(Headings.BOUNDING_BOX_X_MIN);
		assertEquals(new Double(155), xMinStream.get(0));
		assertEquals(new Double(171), xMinStream.get(1));
    }

	public void testEmbryosToMask() {
		String fileName = "embryos6";
        _particleCounter.setToMask(true);
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName,".jpg"), _particleCounter);
		assertEquals(256,bp.getWidth());
		assertEquals(52480,bp.getPixelCount());
		int pixel = bp.get(0,0);
		assertEquals(0xffffff,pixel); //Background
		assertEquals(0,bp.get(128, 128)); //Foreground
		ValueAreaFactory factory = _particleCounter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
		assertEquals(30,factory.getStore().size()); //XXX should be 2
		assertTrue(_particleCounter.isParticleImage());
		assertEquals("Should have 6 particles for this setting.", 6,_particleCounter.getParticleCount());
		StreamFactory streamFactory = new StreamFactory(_particleCounter);
		NumberedStream<Number> ns = streamFactory.findNumberedStream(CommonLogicExpressions.ASPECT_RATIO);
		assertClose(0.9, ns.get(0).doubleValue(), 0.1);
		assertClose(1., ns.get(1).doubleValue(), 0.1);
		NumberedStream<String> letterStream = streamFactory.findNumberedStream(StreamNames.CATEGORY);
		assertEquals("Dark round", letterStream.get(0));
		assertEquals("Dark round", letterStream.get(1));
	}

	public void assertClose(double expected, double found, double precision) {
		assertTrue(Math.abs(expected-found) < precision);
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
		assertEquals(1,_particleCounter.getParticleFiltered().size());
		IColorAndVariance particle = _particleCounter.getParticleFiltered().get(0);
		BBox bBox = particle.getPixelArea().getBoundingBox();
		assertEquals(8.,bBox.minVal.getX());
		assertEquals(8.,bBox.minVal.getY());
		assertEquals(22.,bBox.maxVal.getX());
		assertEquals(22.,bBox.maxVal.getY());
		assertEquals(15.,bBox.getCenter().getX());
		assertEquals(15.,bBox.getCenter().getY());
		assertEquals(1.,bBox.getAspectRatio());
	}

	/** This gets opened as a byte interleaved and not as an int RGB
	 */
	public void testOvalCleanPng() {
		String fileName = "oval1Clean";
		SLImage  bp = runPluginFilterOnBufferedImage(filePath(fileName,".png"), _particleCounter);
		assertEquals(30,bp.getWidth());
		assertEquals(1800,bp.getPixelCount());
		int pixel = bp.get(0,0);
		assertEquals(0xffffff,pixel);
		ValueAreaFactory factory = _particleCounter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
		assertEquals(2,factory.getStore().size()); 
		assertEquals(1,_particleCounter.getParticleFiltered().size());
		IColorAndVariance particle = _particleCounter.getParticleFiltered().get(0);
		BBox bBox = particle.getPixelArea().getBoundingBox();
		assertEquals(8.,bBox.minVal.getX());
		assertEquals(16.,bBox.minVal.getY());
		assertEquals(22.,bBox.maxVal.getX());
		assertEquals(45.,bBox.maxVal.getY());
		assertEquals(15.,bBox.getCenter().getX());
		assertEquals(30.,bBox.getCenter().getY());
		assertEquals(0.4827586206896552,bBox.getAspectRatio());
	}

	public void testEmbryosReadRulesFromFile() {
		String fileName = "embryos6";
        String neuralNetworkFile = "particle_nn_with_rules.txt";
        _particleCounter.setNeuralNetworkFile(_dataFileDir +"/" + neuralNetworkFile);
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName,".jpg"), _particleCounter);
		assertEquals(256,bp.getWidth());
		assertEquals(52480,bp.getPixelCount());
		int pixel = bp.get(0,0);
		assertEquals(12561501,pixel);
		ValueAreaFactory factory = _particleCounter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
		assertEquals(30,factory.getStore().size()); //XXX should be 2
		assertTrue(_particleCounter.isParticleImage());
		assertEquals("Should have 6 particles for this setting.", 6,_particleCounter.getParticleCount());
		StreamFactory streamFactory = new StreamFactory(_particleCounter);
		NumberedStream<Number> ns = streamFactory.findNumberedStream(CommonLogicExpressions.ASPECT_RATIO);
		assertClose(0.9, ns.get(0).doubleValue(), 0.1);
		assertClose(1., ns.get(1).doubleValue(), 0.1);
		NumberedStream<String> letterStream = streamFactory.findNumberedStream(StreamNames.CATEGORY);
		assertEquals("TALL", letterStream.get(0));
		assertEquals("", letterStream.get(1));
		StringBuffer internalInfo = _particleCounter.getInternalInfo();
		assertTrue(500 < internalInfo.length());
        //Test color red for 2 first particles
		NumberedStream<Integer> redStream = streamFactory.findNumberedStream(StreamNames.COLOR_R);
		assertEquals(new Integer(105), redStream.get(0));
		assertEquals(new Integer(102), redStream.get(1));

        //Test min x bounding box for 2 first particles
		NumberedStream<Double> xMinStream = streamFactory.findNumberedStream(Headings.BOUNDING_BOX_X_MIN);
		assertEquals(new Double(155), xMinStream.get(0));
		assertEquals(new Double(171), xMinStream.get(1));
        assertEquals("Category", _particleCounter.getTableBuilderOutputList().get(0));
        assertEquals("Area", _particleCounter.getTableBuilderOutputList().get(1));

    }

	public void testEmbryosReadRulesAndPrintFromFile() {
		String fileName = "embryos6";
        String neuralNetworkFile = "particle_nn_with_rules.txt";
        _particleCounter.setNeuralNetworkFile(_dataFileDir +"/" + neuralNetworkFile);
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName,".jpg"), _particleCounter);
		assertEquals(256,bp.getWidth());
		assertEquals(52480,bp.getPixelCount());
		int pixel = bp.get(0,0);
		assertEquals(12561501,pixel);
		ValueAreaFactory factory = _particleCounter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
		assertEquals(30,factory.getStore().size()); //XXX should be 2
		assertTrue(_particleCounter.isParticleImage());
		assertEquals("Should have 6 particles for this setting.", 6,_particleCounter.getParticleCount());
		StreamFactory streamFactory = new StreamFactory(_particleCounter);
		NumberedStream<Number> ns = streamFactory.findNumberedStream(CommonLogicExpressions.ASPECT_RATIO);
		assertClose(0.9, ns.get(0).doubleValue(), 0.1);
		assertClose(1., ns.get(1).doubleValue(), 0.1);
		NumberedStream<String> letterStream = streamFactory.findNumberedStream(StreamNames.CATEGORY);
		assertEquals("TALL", letterStream.get(0));
		assertEquals("", letterStream.get(1));
		StringBuffer internalInfo = _particleCounter.getInternalInfo();
		assertTrue(500 < internalInfo.length());
        //Test color red for 2 first particles
		NumberedStream<Integer> redStream = streamFactory.findNumberedStream(StreamNames.COLOR_R);
		assertEquals(new Integer(105), redStream.get(0));
		assertEquals(new Integer(102), redStream.get(1));

        //Test min x bounding box for 2 first particles
		NumberedStream<Double> xMinStream = streamFactory.findNumberedStream(Headings.BOUNDING_BOX_X_MIN);
		assertEquals(new Double(155), xMinStream.get(0));
		assertEquals(new Double(171), xMinStream.get(1));
        assertEquals("Category", _particleCounter.getTableBuilderOutputList().get(0));
//        assertEquals("AspectRatio", _particleCounter.getTableBuilderOutputList().get(1));

    }

	public void testEmbryosReadRulesFromFileNeuralNetwork() {
		String fileName = "embryos6";
        String neuralNetworkFile = "particle_nn_with_rules_print.txt";
        _particleCounter.setNeuralNetworkFile(_dataFileDir +"/" + neuralNetworkFile);
        _particleCounter.setUseNeuralNetwork(false);
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName,".jpg"), _particleCounter);
		assertEquals(256,bp.getWidth());
		assertEquals(52480,bp.getPixelCount());
		int pixel = bp.get(0,0);
		assertEquals(12561501,pixel);
		ValueAreaFactory factory = _particleCounter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
		assertEquals(30,factory.getStore().size()); 
		assertTrue(_particleCounter.isParticleImage());
		assertEquals("Should have 6 particles for this setting.", 6,_particleCounter.getParticleCount());
		StreamFactory streamFactory = new StreamFactory(_particleCounter);
		NumberedStream<Number> ns = streamFactory.findNumberedStream(CommonLogicExpressions.ASPECT_RATIO);
		assertClose(0.9, ns.get(0).doubleValue(), 0.1);
		assertClose(1., ns.get(1).doubleValue(), 0.1);
		NumberedStream<String> letterStream = streamFactory.findNumberedStream(StreamNames.CATEGORY);
		assertEquals("TALL", letterStream.get(0));
		assertEquals("", letterStream.get(1));
		StringBuffer internalInfo = _particleCounter.getInternalInfo();
		assertTrue(500 < internalInfo.length());
        //Test color red for 2 first particles
		NumberedStream<Integer> redStream = streamFactory.findNumberedStream(StreamNames.COLOR_R);
		assertEquals(new Integer(105), redStream.get(0));
		assertEquals(new Integer(102), redStream.get(1));

        //Test min x bounding box for 2 first particles
		NumberedStream<Double> xMinStream = streamFactory.findNumberedStream(Headings.BOUNDING_BOX_X_MIN);
		assertEquals(new Double(155), xMinStream.get(0));
		assertEquals(new Double(171), xMinStream.get(1));
    }

	public void testEmbryosReadPrintFromFileNeuralNetwork() {
		String fileName = "embryos6";
        String neuralNetworkFile = "particle_nn_with_print.txt";
        _particleCounter.setNeuralNetworkFile(_dataFileDir +"/" + neuralNetworkFile);
        _particleCounter.setUseNeuralNetwork(false);
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName,".jpg"), _particleCounter);
		assertEquals(256,bp.getWidth());
		assertEquals(52480,bp.getPixelCount());
		int pixel = bp.get(0,0);
		assertEquals(12561501,pixel);
		ValueAreaFactory factory = _particleCounter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
		assertEquals(30,factory.getStore().size()); 
		assertTrue(_particleCounter.isParticleImage());
		assertEquals("Should have 6 particles for this setting.", 6,_particleCounter.getParticleCount());
		StreamFactory streamFactory = new StreamFactory(_particleCounter);
		NumberedStream<Number> ns = streamFactory.findNumberedStream(CommonLogicExpressions.ASPECT_RATIO);
		assertClose(0.9, ns.get(0).doubleValue(), 0.1);
		assertClose(1., ns.get(1).doubleValue(), 0.1);
		NumberedStream<String> letterStream = streamFactory.findNumberedStream(StreamNames.CATEGORY);
		assertEquals("Dark round", letterStream.get(0));
		assertEquals("Dark round", letterStream.get(1));
		StringBuffer internalInfo = _particleCounter.getInternalInfo();
		assertTrue(500 < internalInfo.length());
        //Test color red for 2 first particles
		NumberedStream<Integer> redStream = streamFactory.findNumberedStream(StreamNames.COLOR_R);
		assertEquals(new Integer(105), redStream.get(0));
		assertEquals(new Integer(102), redStream.get(1));

        //Test min x bounding box for 2 first particles
		NumberedStream<Double> xMinStream = streamFactory.findNumberedStream(Headings.BOUNDING_BOX_X_MIN);
		assertEquals(new Double(155), xMinStream.get(0));
		assertEquals(new Double(171), xMinStream.get(1));
    }

	public void testEmbryosReadNeuralNetworkFromFile() {
		String fileName = "embryos6";
        String neuralNetworkFile = "particle_nn_with_print.txt";
        _particleCounter.setNeuralNetworkFile(_dataFileDir +"/" + neuralNetworkFile);
        _particleCounter.setUseNeuralNetwork(true);
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName,".jpg"), _particleCounter);
		assertEquals(256,bp.getWidth());
		assertEquals(52480,bp.getPixelCount());
		int pixel = bp.get(0,0);
		assertEquals(12561501,pixel);
		ValueAreaFactory factory = _particleCounter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
		assertEquals(30,factory.getStore().size()); 
		assertTrue(_particleCounter.isParticleImage());
		assertEquals("Should have 6 particles for this setting.", 6,_particleCounter.getParticleCount());
		StreamFactory streamFactory = new StreamFactory(_particleCounter);
		NumberedStream<Number> ns = streamFactory.findNumberedStream(CommonLogicExpressions.ASPECT_RATIO);
		assertClose(0.9, ns.get(0).doubleValue(), 0.1);
		assertClose(1., ns.get(1).doubleValue(), 0.1);
		NumberedStream<String> letterStream = streamFactory.findNumberedStream(StreamNames.CATEGORY);
		assertEquals("tall", letterStream.get(0));
		assertEquals("", letterStream.get(1));
		StringBuffer internalInfo = _particleCounter.getInternalInfo();
		assertTrue(500 < internalInfo.length());
        //Test color red for 2 first particles
		NumberedStream<Integer> redStream = streamFactory.findNumberedStream(StreamNames.COLOR_R);
		assertEquals(new Integer(105), redStream.get(0));
		assertEquals(new Integer(102), redStream.get(1));

        //Test min x bounding box for 2 first particles
		NumberedStream<Double> xMinStream = streamFactory.findNumberedStream(Headings.BOUNDING_BOX_X_MIN);
		assertEquals(new Double(155), xMinStream.get(0));
		assertEquals(new Double(171), xMinStream.get(1));
    }

	public void testEmbryosReadPrintFromFileNeuralNetworkMultiLayer() {
		String fileName = "embryos6";
        String neuralNetworkFile = "particle_nn_multi_layer.txt";
        _particleCounter.setNeuralNetworkFile(_dataFileDir +"/" + neuralNetworkFile);
        _particleCounter.setUseNeuralNetwork(true);
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName,".jpg"), _particleCounter);
		assertEquals(256,bp.getWidth());
		assertEquals(52480,bp.getPixelCount());
		int pixel = bp.get(0,0);
		assertEquals(12561501,pixel);
		ValueAreaFactory factory = _particleCounter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
		assertEquals(30,factory.getStore().size()); 
		assertTrue(_particleCounter.isParticleImage());
		assertEquals("Should have 6 particles for this setting.", 6,_particleCounter.getParticleCount());
		StreamFactory streamFactory = new StreamFactory(_particleCounter);
		NumberedStream<Number> ns = streamFactory.findNumberedStream(CommonLogicExpressions.ASPECT_RATIO);
		assertClose(0.9, ns.get(0).doubleValue(), 0.1);
		assertClose(1., ns.get(1).doubleValue(), 0.1);
		NumberedStream<String> letterStream = streamFactory.findNumberedStream(StreamNames.CATEGORY);
		assertEquals("tall", letterStream.get(0));
		assertEquals("", letterStream.get(1));
		StringBuffer internalInfo = _particleCounter.getInternalInfo();
		assertTrue(500 < internalInfo.length());
        //Test color red for 2 first particles
		NumberedStream<Integer> redStream = streamFactory.findNumberedStream(StreamNames.COLOR_R);
		assertEquals(new Integer(105), redStream.get(0));
		assertEquals(new Integer(102), redStream.get(1));

        //Test min x bounding box for 2 first particles
		NumberedStream<Double> xMinStream = streamFactory.findNumberedStream(Headings.BOUNDING_BOX_X_MIN);
		assertEquals(new Double(155), xMinStream.get(0));
		assertEquals(new Double(171), xMinStream.get(1));
    }

	/** Called with bad file with missing streams.<br />
	 * Should not throw an exception when running.<br />
	 * But the category stream is not getting populated so is should throw exception here.
	 */
	public void testEmbryosReadRulesFileMissingStream() {
		String fileName = "embryos6";
        String neuralNetworkFile = "polygon_digit_recognizer_with_rules_print.txt";
        _particleCounter.setNeuralNetworkFile(_dataFileDir +"/" + neuralNetworkFile);
        _particleCounter.setUseNeuralNetwork(false);
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName,".jpg"), _particleCounter);
		assertEquals(256,bp.getWidth());
		assertEquals(52480,bp.getPixelCount());
		int pixel = bp.get(0,0);
		assertEquals(12561501,pixel);
		ValueAreaFactory factory = _particleCounter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
		assertEquals(30,factory.getStore().size()); 
		assertTrue(_particleCounter.isParticleImage());
		assertEquals("Should have 6 particles for this setting.", 6,_particleCounter.getParticleCount());
		StreamFactory streamFactory = new StreamFactory(_particleCounter);
		NumberedStream<Number> ns = streamFactory.findNumberedStream(CommonLogicExpressions.ASPECT_RATIO);
		assertClose(0.9, ns.get(0).doubleValue(), 0.1);
		assertClose(1., ns.get(1).doubleValue(), 0.1);
		NumberedStream<String> letterStream = streamFactory.findNumberedStream(StreamNames.CATEGORY);
		assertEquals("", letterStream.get(0));
		assertEquals("", letterStream.get(1));
		StringBuffer internalInfo = _particleCounter.getInternalInfo();
		assertTrue(500 < internalInfo.length());
        //Test color red for 2 first particles
		NumberedStream<Integer> redStream = streamFactory.findNumberedStream(StreamNames.COLOR_R);
		assertEquals(new Integer(105), redStream.get(0));
		assertEquals(new Integer(102), redStream.get(1));

        //Test min x bounding box for 2 first particles
		NumberedStream<Double> xMinStream = streamFactory.findNumberedStream(Headings.BOUNDING_BOX_X_MIN);
		assertEquals(new Double(155), xMinStream.get(0));
		assertEquals(new Double(171), xMinStream.get(1));
    }

	/** Called with bad file with missing streams.<br />
	 * Should not throw an exception when running.<br />
	 * But the category stream is not getting populated so is should throw exception here.
	 */
	public void testEmbryosReadNeuralNetworkFromFileMissingStream() {
		String fileName = "embryos6";
        String neuralNetworkFile = "missing_stream_nn.txt";
        _particleCounter.setNeuralNetworkFile(_dataFileDir +"/" + neuralNetworkFile);
        _particleCounter.setUseNeuralNetwork(true);
		SLImage bp = runPluginFilterOnBufferedImage(filePath(fileName,".jpg"), _particleCounter);
		assertEquals("Missing stream for stream name: Points",_particleCounter.getErrorMessage());
		assertEquals(256,bp.getWidth());
		assertEquals(52480,bp.getPixelCount());
		int pixel = bp.get(0,0);
		assertEquals(12561501,pixel);
		ValueAreaFactory factory = _particleCounter.getSegmentation().getSegmentAreaFactory();
		assertNotNull(factory);
		assertEquals(30,factory.getStore().size()); 
		assertTrue(_particleCounter.isParticleImage());
		assertEquals("Should have 6 particles for this setting.", 6,_particleCounter.getParticleCount());
		StreamFactory streamFactory = new StreamFactory(_particleCounter);
		NumberedStream<Number> ns = streamFactory.findNumberedStream(CommonLogicExpressions.ASPECT_RATIO);
		assertClose(0.9, ns.get(0).doubleValue(), 0.1);
		assertClose(1., ns.get(1).doubleValue(), 0.1);
		NumberedStream<String> letterStream = streamFactory.findNumberedStream(StreamNames.CATEGORY);
		try {
			assertEquals("tall", letterStream.get(0));
			fail("NoSuchElementException should have been thrown.");
		}
		catch (NoSuchElementException ex) {
			assertTrue(true);
		}
    }

}
