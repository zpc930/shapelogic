package org.shapelogic.imageutil;

/** Run a PixelHanler in a given area using ImageJ.<br />
 * 
 * This class was the beginning of SLImage, the image abstraction from ImageJ.<br />
 * 
 * @author Sami Badawi
 *
 */
public class PixelAreaHandler  
{
	protected SLImage _image;
	
	public PixelAreaHandler(SLImage image) {
		_image = image;
	}
	
	/** Currently this work with ImageJ images. */
	public PixelAreaHandler(String dir, String fileName, String fileFormat) {
		_image = new IJImage(dir,fileName,fileFormat);
	}

	/** Handle a pixel with a color and a coordinate. */
	public void handlePixelArea(PixelHandler ph, int x, int y, int width, int height) {
		PixelHandlerEnds phe = null;
		if (ph instanceof PixelHandlerEnds)
			phe = (PixelHandlerEnds)ph;
		int xEnd = x + width;
		int yEnd = y + height;
		for (int j=y;j<yEnd;j++) {
			if (phe != null)
				phe.handlePixelStart(x, j, _image.get(x, j));
			for (int i=x;i<xEnd;i++) {
				ph.putPixel(i, j, _image.get(i, j));
			}
			if (phe != null)
				phe.handlePixelEnd(x+width, j, _image.get(x+width, j));
		}
	}

	/** Handle all the pixels in an image.<br />
	 * 
	 * This should maybe be changed to work with ROI.<br />
	 */
	public void handleAllPixels(PixelHandler ph) {
		handlePixelArea(ph, 0, 0, _image.getWidth(), _image.getHeight());
	}
}
