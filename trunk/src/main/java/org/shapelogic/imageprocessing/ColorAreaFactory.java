package org.shapelogic.imageprocessing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** GrayAreaFactory is a factory and store for ColorArea.
 * <br />
 * @author Sami Badawi
 *
 */
public class ColorAreaFactory implements PixelAreaFactory {
	
	ArrayList<ColorArea> _store = new ArrayList<ColorArea>();
	int _background;
	int _maxArea = 0;
	
	@Override
	public List<? extends PixelArea> getStore() {
		return _store;
	}

	@Override
	public PixelArea makePixelArea(int x, int y, int startColor) {
		ColorArea result = new ColorArea(x, y, startColor);
		_store.add(result);
		return result;
	}

	/** Returns the biggest color. */
	@Override
	public int background() {
		return _background;
	}

	@Override
	public void sort() {
		Collections.sort(_store,new AreaComparator());
	}

}
