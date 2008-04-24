package org.shapelogic.color;

import org.shapelogic.imageutil.PixelArea;

/** ValueArea is a color aggregate with a mean color and a standard deviation, 
 * range and space.<br />
 * 
 * ColorAndVarianceI is an interface for a color / gray implementations.
 * 
 * @author Sami Badawi
 *
 */
public interface ValueArea extends IColorAndVariance {

	PixelArea getPixelArea();
	
}
