package org.shapelogic.imageprocessing;

import java.util.List;

/** Factory and storage interface. <br /> 
 * 
 * @author Sami Badawi
 *
 */
public interface PixelAreaFactory {
	PixelArea makePixelArea(int x, int y, int startColor);
	List<? extends PixelArea> getStore();
	int background();
	void sort();
}
