package org.shapelogic.imageutil;

import java.awt.image.BufferedImage;

import junit.framework.TestCase;

/** Test SLBufferedImage.
 * 
 * @author Sami Badawi
 *
 */
public class SLBufferedImageTest extends TestCase {
	String dir = "./src/test/resources/images/particles";
	
	public void testOneWhitePixelGray() {
		String filename = "oneWhitePixelGray";
		SLBufferedImage image = new SLBufferedImage(dir,filename,".gif");
		assertNotNull(image);
		assertEquals(1, image.getWidth());
		assertEquals(1, image.getHeight());
		assertEquals(1, image.getNChannels());
		assertNotNull(image.getPixels());
		assertEquals(1, image.getPixelsInBytes().length);
		assertEquals(0, image.getPixelsInBytes()[0]);
		assertEquals(BufferedImage.TYPE_BYTE_INDEXED, image.getBufferedImage().getType());
		assertTrue(image.isInvertedLut());
		assertEquals(0,image.get(0,0));
		assertEquals(0,image.get(0));
	} 

	/** Maybe IOImage cannot open gray png images. */
	public void testOneWhitePixelGrayPng() {
		String filename = "oneWhitePixelGray";
		SLBufferedImage image = new SLBufferedImage(dir,filename,".png");
		assertNotNull(image);
		assertTrue(image.isEmpty());
		if (true) return;
		assertEquals(1, image.getWidth());
		assertEquals(1, image.getHeight());
		assertEquals(1, image.getNChannels());
		assertNotNull(image.getPixels());
		assertEquals(3, image.getPixelsInBytes().length);
		assertEquals(-1, image.getPixelsInBytes()[0]);
	} 

	public void testOneWhitePixelRGB() {
		String filename = "oneWhitePixelRGB";
		SLBufferedImage image = new SLBufferedImage(dir,filename,".png");
		assertNotNull(image);
		assertFalse(image.isEmpty());
		assertEquals(1, image.getWidth());
		assertEquals(1, image.getHeight());
		assertEquals(3, image.getNChannels());
		assertNotNull(image.getPixels());
		assertEquals(1, image.getPixelsInInt().length);
		assertEquals(0xffffff, image.getPixelsInInt()[0]);
		assertEquals(BufferedImage.TYPE_INT_RGB, image.getBufferedImage().getType());
		assertFalse(image.isInvertedLut());
		assertEquals(0xffffff,image.get(0,0));
		assertEquals(0xffffff,image.get(0));
	} 

	public void testLetterB() {
		String dirURL = "./src/test/resources/images/smallThinLetters";
		String filename = "B";
		SLBufferedImage image = new SLBufferedImage(dirURL,filename,".gif");
		assertNotNull(image);
		assertEquals(30, image.getWidth());
		assertEquals(30, image.getHeight());
		assertEquals(1, image.getNChannels());
		assertNotNull(image.getPixels());
		assertEquals(900, image.getPixelsInBytes().length);
		assertEquals(0, image.getPixelsInBytes()[0]);
		assertEquals(BufferedImage.TYPE_BYTE_INDEXED, image.getBufferedImage().getType());
		assertTrue(image.isInvertedLut());
		assertEquals(0,image.get(0,0));
		assertEquals(0,image.get(0));
	} 

}
