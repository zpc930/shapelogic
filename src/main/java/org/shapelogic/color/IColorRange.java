package org.shapelogic.color;

/** ColorRangeI is a color aggregate that exist in an defined range.<br />
 * 
 * ColorAndVarianceI is an interface for a color / gray implementations.
 * 
 * @author Sami Badawi
 *
 */
public interface IColorRange extends IColorAndVariance {
	
	/** Whether a color belong to the ColorRange. */
	boolean colorInRange(int color);
	
	/** Distance of input color from this ColorRange. */
	double distanceFromRange(int color);
	
	/** Distance of input color from this ColorRange. */
	double distanceFromRangeCenter(int color);
	
	double getMaxDistance();
	
	void setMaxDistance(double distance);
	
	int getColorCenter();
    
    void setColorCenter(int center);
    
	/** If the ColorRange can change. */
	boolean isRangeFrozen();
	
	IColorDistance getDistance();

	void setDistance(IColorDistance distance);
}
