package org.shapelogic.color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.shapelogic.imageprocessing.AreaComparator;

/** GrayAreaFactory is a factory and store for ColorArea.
 * <br />
 * @author Sami Badawi
 *
 */
public class ColorAreaFactory implements ValueAreaFactory {
	
	ArrayList<ValueArea> _store = new ArrayList<ValueArea>();
	int _backgroundColor;
	int _maxArea = 0;
	
	@Override
	public List<ValueArea> getStore() {
		return _store;
	}

	@Override
	public ValueArea makePixelArea(int x, int y, int startColor) {
		ColorArea result = new ColorArea(x, y, startColor);
		_store.add(result);
		return result;
	}

	/** Returns the biggest color. */
	@Override
	public int getBackgroundColor() {
		return _backgroundColor;
	}

	@Override
	public void sort() {
		Collections.sort(_store,new AreaComparator());
	}

}
