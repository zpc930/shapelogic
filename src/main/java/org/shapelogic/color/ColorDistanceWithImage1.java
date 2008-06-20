package org.shapelogic.color;

import org.shapelogic.imageutil.SLImage;

/** ColorDistance with an image so you can ask the distance based on x,y coordinates.<br />
 * 
 * @author Sami Badawi
 *
 */
public class ColorDistanceWithImage1 extends ColorDistance1
implements IColorDistanceWithImage {
	public static int MASK = 255;
	protected byte[] _pixels;
	protected SLImage _image;
	protected int _lineStride;

	@Override
	public double distanceToReferenceColor(int x, int y) {
		int index = x + y * _lineStride; 
		return distanceToReferenceColor(_pixels[index] & MASK);
	}

	@Override
	public SLImage getImage() {
		return _image;
	}

	@Override
	public void setImage(SLImage image) {
		_image = image;
		if (!image.isGray())
			throw new RuntimeException("Input image is of type gray.");
		_pixels = (byte[])image.getPixels();
		_lineStride = _image.getLineStride();
	}
}
