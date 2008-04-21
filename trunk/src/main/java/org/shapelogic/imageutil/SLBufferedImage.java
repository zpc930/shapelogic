package org.shapelogic.imageutil;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferShort;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/** Abstraction of ImageJ ImageProcessor.<br />
 * 
 * This is an attempt to get Java BufferedImage to look like the ImageJ ImageProcessor.<br />
 * 
 * They are not perfectly matched.<br />
 * 
 * I think that I should make the assumption that this is either a<br /> 
 * 24 or 32 bit RGB  <br />
 * 8 bit gray<br />
 * And fail the creation if it is not.<br />
 * 
 * @author Sami Badawi
 *
 */
public class SLBufferedImage implements SLImage {
	public static final int INT_ALL_MASK = -1;
	public static final int INT_BYTE_MASK = 0Xff;
	public static final int UNDEFINED = -2;
	
	protected BufferedImage _bufferedImage;
	protected String _filePath;
	protected Object _pixels;
	protected byte[] _pixelsInBytes;
	protected short[] _pixelsInShort;
	protected int[] _pixelsInInt;
	protected WritableRaster _rasta;
	protected DataBuffer _dataBuffer;
	
	protected int _mask = INT_ALL_MASK;
	protected Rectangle _roi;
	protected int _lineStride;
	
	
	public SLBufferedImage(String filePath) {
		_filePath = filePath;
		File file = new File(filePath);
		if (file.exists())
			try {
				_bufferedImage = ImageIO.read(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		if (_bufferedImage == null)
			System.out.println("Could not open: " + filePath);
		else {
			if (_bufferedImage.getType() == BufferedImage.TYPE_BYTE_GRAY) {
				_mask = INT_BYTE_MASK;
			}
			_lineStride = _bufferedImage.getWidth(); 
			getPixels(); //XXX should maybe only done lazily
		}
	}

	/** Constructor with 3 string arguments. <br />
	 * 
	 * @param dir e.g. "./src/test/resources/images/smallThinShapes";
	 * @param fileName e.g. "blobs"
	 * @param fileFormat e.g. ".gif"
	 */
	public SLBufferedImage(String dir, String fileName, String fileFormat) {
		this(dir + "/" + fileName + fileFormat);
	}
	
	public SLBufferedImage(BufferedImage image) {
		_bufferedImage = image;
	}
	
	/** In ImageJ this will return the byte if it is a byte gray else an 
	 * encoded RGB value.<br />
	 * 
	 * This does not work well with BufferedImage that has a method to always 
	 * return the RGB.<br />
	 * 
	 * I might have to subclass this in order to make an effective implementation.
	 */
	@Override
	public int get(int x, int y) {
//		return _bufferedImage.getRGB(x, y);
		int index = x + y * _lineStride;
		if (_pixelsInBytes != null) return _pixelsInBytes[index];
		if (_pixelsInInt != null) return _pixelsInInt[index];
		if (_pixelsInShort != null) return _pixelsInShort[index];
		return UNDEFINED;
	}

	@Override
	public int get(int index) {
		if (_pixelsInBytes != null) return _pixelsInBytes[index];
		if (_pixelsInInt != null) return _pixelsInInt[index];
		if (_pixelsInShort != null) return _pixelsInShort[index];
		return UNDEFINED;
	}

	@Override
	public int getHeight() {
		return _bufferedImage.getHeight();
	}

	@Override
	public int getNChannels() {
		return _bufferedImage.getColorModel().getPixelSize()/8;
	}

	public int getNChannelsOld() {
		int type = _bufferedImage.getType();
		if (type == BufferedImage.TYPE_BYTE_GRAY) return 1;
		if (type == BufferedImage.TYPE_BYTE_INDEXED) return 1;//inverted LUT has this type 
		if (type == BufferedImage.TYPE_INT_ARGB) return 4;
		if (type == BufferedImage.TYPE_INT_RGB) return 3;
		return UNDEFINED;
	}

	@Override
	public int getPixelCount() {
		return _bufferedImage.getWidth() * _bufferedImage.getHeight();
	}

	@Override
	public Object getPixels() {
		if (_pixels == null) {
			ColorModel colorModel = _bufferedImage.getColorModel();  
			if (colorModel instanceof ComponentColorModel && 2 < getNChannels())
				_bufferedImage = ImageUtil.toBufferedImage(_bufferedImage,BufferedImage.TYPE_INT_RGB);
			_rasta = _bufferedImage.getRaster();
			_dataBuffer = _rasta.getDataBuffer();
			if (_dataBuffer instanceof DataBufferByte) {
				_pixelsInBytes = ((DataBufferByte)_dataBuffer).getData();
				_pixels = _pixelsInBytes;
			}
			else if (_dataBuffer instanceof DataBufferInt) {
				_pixelsInInt = ((DataBufferInt)_dataBuffer).getData();
				_pixels = _pixelsInInt;
			}
			else if (_dataBuffer instanceof DataBufferShort) {
				_pixelsInShort = ((DataBufferShort)_dataBuffer).getData();
				_pixels = _pixelsInShort;
			}
			else {
				//By doing this here instead of in the constructor this becomes lazy
				_bufferedImage = ImageUtil.toBufferedImage(_bufferedImage,BufferedImage.TYPE_INT_RGB);
				_pixelsInInt = ((DataBufferInt)_dataBuffer).getData();
				_pixels = _pixelsInInt;
			}
		}
		return _pixels;
	}
	
	public byte[] getPixelsInBytes() {
		if (_pixels == null) 
			getPixels();
		return _pixelsInBytes;
	}
	
	public int[] getPixelsInInt() {
		if (_pixels == null) 
			getPixels();
		return _pixelsInInt;
	}
	
	/** Not implemented for BufferedImage. */
	@Override
	public Rectangle getRoi() {
		return _roi;
	}

	@Override
	public int getWidth() {
		return _bufferedImage.getWidth();
	}

	/** Always false for a BufferedImage. */
	@Override
	public boolean isInvertedLut() {
		if (BufferedImage.TYPE_BYTE_INDEXED == _bufferedImage.getType())
			return true; //XXX needs to test the index
		return false;
	}

	@Override
	public void putPixel(int x, int y, int value) {
		int index = x + y * _lineStride; 
//		if (BufferedImage.TYPE_BYTE_INDEXED == _bufferedImage.getType())
//			_bufferedImage.setRGB(x, y, value);
//		else 
		{
			if (_pixelsInBytes != null) _pixelsInBytes[index] = (byte)value;
			else if (_pixelsInInt != null) _pixelsInInt[index] = value;
			else if (_pixelsInShort != null) _pixelsInShort[index] = (short)value;
		}
	}

	@Override
	public void set(int x, int y, int value) {
		int index = x + y * _lineStride; 
//		if (BufferedImage.TYPE_BYTE_INDEXED == _bufferedImage.getType())
//			_bufferedImage.setRGB(x, y, value);
//		else 
		{
			if (_pixelsInBytes != null) _pixelsInBytes[index] = (byte)value;
			else if (_pixelsInInt != null) _pixelsInInt[index] = value;
			else if (_pixelsInShort != null) _pixelsInShort[index] = (short)value;
		}
	}

	@Override
	public void set(int index, int value) {
		if (_pixelsInBytes != null) _pixelsInBytes[index] = (byte)value;
		else if (_pixelsInInt != null) _pixelsInInt[index] = value;
		else if (_pixelsInShort != null) _pixelsInShort[index] = (short)value;
	}

	@Override
	public void setPixels(Object pixels) {
//		_bufferedImage.setPixels(pixels);
	}

	@Override
	public void setRoi(Rectangle roi) {
		_roi = roi;
	}

	@Override
	public void setRoi(int x, int y, int rwidth, int rheight) {
		if (_roi == null)
			_roi = new Rectangle(x,y,rwidth,rheight);
		else
			_roi.setBounds(x, y, rwidth, rheight);
	}

	/** Test for failed image read.<br />
	 * 
	 * If the read of an image fails it will still create the object but
	 * _bufferedImage == null.
	 */ 
	@Override
	public boolean isEmpty() {
		return _bufferedImage == null;
	}

	/** The part of the image that is available in the pixels array.<br />
	 * 
	 * A hook to only have part of the image active at the same time.<br />
	 * 
	 * Returning null mean that the whole images is available in the pixels array. */
	@Override
	public Rectangle getActiveRectangle() {
		return null;
	}

	/** Number of index position in the pixels array to jump to get to the next line. */
	@Override
	public int getLineStride() {
		return getWidth();
	}
	
	public BufferedImage getBufferedImage() {
		return _bufferedImage;
	}

	@Override
	public boolean isGray() {
		int type = _bufferedImage.getType();
		return type == BufferedImage.TYPE_BYTE_GRAY ||
			type == BufferedImage.TYPE_BYTE_INDEXED;
	}

	@Override
	public boolean isRgb() {
		int type = _bufferedImage.getType();
		return type == BufferedImage.TYPE_INT_RGB ||
			type == BufferedImage.TYPE_INT_ARGB;
	}
}
