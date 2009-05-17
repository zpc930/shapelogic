package org.shapelogic.color;

import java.util.List;


/** Factory and storage interface. <br /> 
 * 
 * @author Sami Badawi
 *
 */
public interface ValueAreaFactory {
	IColorAndVariance makePixelArea(int x, int y, int startColor);
	List<IColorAndVariance> getStore();
	int getBackgroundColor();
	void sort();
    int areasGreaterThan(int minSize);
}
