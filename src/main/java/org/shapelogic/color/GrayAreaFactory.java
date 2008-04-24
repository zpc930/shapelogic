package org.shapelogic.color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.shapelogic.imageprocessing.AreaComparator;

/** GrayAreaFactory is a factory and store for GrayArea.
 * <br />
 * @author Sami Badawi
 *
 */
public class GrayAreaFactory implements ValueAreaFactory {
	
	ArrayList<ValueArea> _store = new ArrayList<ValueArea>();
	int _backgroundColor;
	int _maxArea = 0;
	
	@Override
	public List<ValueArea> getStore() {
		return _store;
	}

	@Override
	public GrayArea makePixelArea(int x, int y, int startColor) {
		GrayArea result = new GrayArea(x, y, startColor);
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
