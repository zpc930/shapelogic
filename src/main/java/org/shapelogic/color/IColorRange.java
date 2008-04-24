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
	
	/** If the ColorRange can change. */
	boolean isRangeFrozen();
}
