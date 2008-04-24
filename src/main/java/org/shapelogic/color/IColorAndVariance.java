package org.shapelogic.color;

import org.shapelogic.imageutil.PixelHandler;

/** ColorAndVarianceI interface for a color / gray and a standard deviation.
 * 
 * @author Sami Badawi
 *
 */
public interface IColorAndVariance extends PixelHandler, ColorChannels {
	
	/** */
	void merge(IColorAndVariance colorAndVariance);
	
	double getStandardDeviation();
	
	int getMeanColor();
	
	/** Area of this color range. */
	int getArea();
	
}
