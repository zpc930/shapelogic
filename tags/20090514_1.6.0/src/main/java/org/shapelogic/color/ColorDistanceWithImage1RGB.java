package org.shapelogic.color;

import org.shapelogic.imageutil.SLImage;

/** ColorDistance with an image so you can ask the distance based on x,y coordinates.<br />
 * 
 * @author Sami Badawi
 *
 */
public class ColorDistanceWithImage1RGB extends ColorDistance1RGB
implements IColorDistanceWithImage {
	protected int[] _pixels;
	protected SLImage _image;
	protected int _lineStride;

	@Override
	public double distanceToReferenceColor(int x, int y) {
		int index = x + y * _lineStride; 
		return distanceToReferenceColor(_pixels[index]);
	}

	@Override
	public SLImage getImage() {
		return _image;
	}

	@Override
	public void setImage(SLImage image) {
		_image = image;
		if (!image.isRgb())
			throw new RuntimeException("Input image is of type RGB.");
		_pixels = (int[])image.getPixels();
		_lineStride = _image.getLineStride();
	}
}
