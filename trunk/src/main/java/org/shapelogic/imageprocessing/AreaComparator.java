package org.shapelogic.imageprocessing;

import java.util.Comparator;

import org.shapelogic.color.ValueArea;

/** Compare area by pixel size. <br />
 * 
 * @author Sami Badawi
 *
 */
public class AreaComparator implements Comparator<ValueArea> {

	@Override
	public int compare(ValueArea o1, ValueArea o2) {
		return o1.getArea() - o2.getArea();
	}

}
