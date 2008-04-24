package org.shapelogic.color;

import java.util.List;


/** Factory and storage interface. <br /> 
 * 
 * @author Sami Badawi
 *
 */
public interface ValueAreaFactory {
	ValueArea makePixelArea(int x, int y, int startColor);
	List<ValueArea> getStore();
	int getBackgroundColor();
	void sort();
}
