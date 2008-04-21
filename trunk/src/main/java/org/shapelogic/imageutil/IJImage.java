package org.shapelogic.imageutil;

import ij.ImagePlus;
import ij.io.Opener;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;
import ij.process.ShortProcessor;

import java.awt.Rectangle;

/** Abstraction of ImageJ ImageProcessor.<br />
 * 
 * The other main implementation should probably be build on top of BufferedImage.
 * 
 * @author Sami Badawi
 *
 */
public class IJImage implements SLImage {
	protected ImageProcessor _imageProcessor;
	protected ImagePlus _image;
	protected String _filePath;
	
	public IJImage(String filePath) {
		_filePath = filePath;
		Opener opener = new Opener();
		_image = opener.openImage(filePath);
		_imageProcessor = _image.getProcessor();
	}

	/** Constructor with 3 string arguments. <br />
	 * 
	 * @param dir e.g. "./src/test/resources/images/smallThinShapes";
	 * @param fileName e.g. "blobs"
	 * @param fileFormat e.g. ".gif"
	 */
	public IJImage(String dir, String fileName, String fileFormat) {
		this(dir + "/" + fileName + fileFormat);
	}
	
	public IJImage(ImagePlus image) {
		_image = image;
		_imageProcessor = image.getProcessor();
	}
	
	public IJImage(ImageProcessor ip) {
		_imageProcessor = ip;
	}
	
	@Override
	public int get(int x, int y) {
		return _imageProcessor.get(x, y);
	}

	@Override
	public int get(int index) {
		return _imageProcessor.get(index);
	}

	@Override
	public int getHeight() {
		return _imageProcessor.getHeight();
	}

	@Override
	public int getNChannels() {
		return _imageProcessor.getNChannels();
	}

	@Override
	public int getPixelCount() {
		return _imageProcessor.getPixelCount();
	}

	@Override
	public Object getPixels() {
		return _imageProcessor.getPixels();
	}

	@Override
	public Rectangle getRoi() {
		return _imageProcessor.getRoi();
	}

	@Override
	public int getWidth() {
		return _imageProcessor.getWidth();
	}

	@Override
	public boolean isInvertedLut() {
		return _imageProcessor.isInvertedLut();
	}

	@Override
	public void putPixel(int x, int y, int value) {
		_imageProcessor.putPixel(x, y, value);
	}

	@Override
	public void set(int x, int y, int value) {
		_imageProcessor.set(x, y, value);
	}

	@Override
	public void set(int index, int value) {
		_imageProcessor.set(index, value);
	}

	@Override
	public void setPixels(Object pixels) {
		_imageProcessor.setPixels(pixels);
	}

	@Override
	public void setRoi(Rectangle roi) {
		_imageProcessor.setRoi(roi);
	}

	@Override
	public void setRoi(int x, int y, int rwidth, int rheight) {
		_imageProcessor.setRoi(x, y, rwidth, rheight);
	}

	public ImageProcessor getImageProcessor() {
		return _imageProcessor;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	/** This mean that the whole images is available in the pixels array. */
	@Override
	public Rectangle getActiveRectangle() {
		return null;
	}

	@Override
	public int getLineStride() {
		return getWidth();
	}

	@Override
	public boolean isGray() {
		return _imageProcessor instanceof ByteProcessor ||
			_imageProcessor instanceof ShortProcessor ||
			_imageProcessor instanceof FloatProcessor;
	}

	@Override
	public boolean isRgb() {
		return _imageProcessor instanceof ColorProcessor;
	}

}
