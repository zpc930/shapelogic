package org.shapelogic.imageprocessing;

import java.util.Comparator;

//import org.shapelogic.color.ValueArea;
import org.shapelogic.imageutil.HasArea;

/** Compare area by pixel size. <br />
 * 
 * @author Sami Badawi
 *
 */
public class AreaComparator implements Comparator<HasArea> {

	@Override
	public int compare(HasArea o1, HasArea o2) {
		return o1.getArea() - o2.getArea();
	}

}
