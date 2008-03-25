package org.shapelogic.imageprocessing;

import java.util.Comparator;

/** Compare area by pixel size. <br />
 * 
 * @author Sami Badawi
 *
 */
public class AreaComparator implements Comparator<PixelArea> {

	@Override
	public int compare(PixelArea o1, PixelArea o2) {
		return o1.getArea() - o2.getArea();
	}

}
