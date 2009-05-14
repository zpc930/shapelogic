package org.shapelogic.color;

import org.shapelogic.imageutil.PixelArea;

/** IColorEdgeArea is a color aggregate with a mean color and a standard deviation, 
 * range and space.<br />
 * 
 * ColorAndVarianceI is an interface for a color / gray implementations.
 * 
 * @author Sami Badawi
 *
 */
public interface IColorEdgeArea extends IColorAndVariance {

//	PixelArea getPixelArea();
//	void setColorCenter(int center);
	
	/** Description of how much high contrast neighbor.
	 * 
	 * What would be a good measure for this.
	 * 
	 * Maybe the rate of borders that are high contrast to the one that are not.
	 * 
	 * So maybe have a 30 and 70 percentile contrast levels. 
	 * 
	 * Or maybe I can just set the levels manually and do the count.
	 * 
	 *  */
	int getHighContrastBorders();
	
	int getMediumContrastBorders();
	
	int getLowContrastBorders();
	
	int getAllContrastBorders();
	
	void addBorder(int level);
    
}
