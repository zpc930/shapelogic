package org.shapelogic.color;

import org.shapelogic.imageutil.PixelHandler;

/** ColorAndVarianceI is a color aggregate with a mean color and a standard deviation.<br />
 *  
 * ColorAndVarianceI is an interface for a color / gray implementations.
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
