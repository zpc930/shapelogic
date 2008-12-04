package org.shapelogic.streams;

import java.util.List;

/** AddListStream takes a list of Integer streams and create the add of them.
 * 
 * @author Sami Badawi
 *
 */
public class AddListStream extends BaseListStreamList<Integer,Integer> {

	@Override
	public Integer invoke(List<Integer> input) {
		int sum = 0;
		if (input == null)
			return sum;
		for (Integer element: input)
            if (input != null)
    			sum += element;
		return sum;
	}
}
