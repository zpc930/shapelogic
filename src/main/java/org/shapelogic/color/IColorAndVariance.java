package org.shapelogic.color;

import org.shapelogic.imageutil.HasArea;
import org.shapelogic.imageutil.HasPixelArea;
import org.shapelogic.imageutil.PixelHandler;

/** ColorAndVarianceI is a color aggregate with a mean color and a standard deviation.<br />
 *  
 * ColorAndVarianceI is an interface for a color / gray implementations.
 * 
 * @author Sami Badawi
 *
 */
public interface IColorAndVariance extends PixelHandler, ColorChannels, HasArea,
        HasPixelArea
{
	
	/** */
	void merge(IColorAndVariance colorAndVariance);
	
	double getStandardDeviation();
	
	int getMeanColor();
	
    int getMeanRed();

    int getMeanGreen();

    int getMeanBlue();
}
