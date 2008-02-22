package org.shapelogic.streams;

import java.util.List;

/** AndListStream takes a list of Boolean streams and create the and of them.
 * 
 * @author Sami Badawi
 *
 */
public class AndListStream extends BaseListStreamList<Boolean,Boolean> {

	@Override
	public Boolean invoke(List<Boolean> input) {
		for (Boolean bool : input)
			if (!bool)
				return false;
		return true;
	}

}
