package org.shapelogic.imageprocessing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** GrayAreaFactory is a factory and store for GrayArea.
 * <br />
 * @author Sami Badawi
 *
 */
public class GrayAreaFactory implements PixelAreaFactory {
	
	ArrayList<GrayArea> _store = new ArrayList<GrayArea>();
	int _background;
	int _maxArea = 0;
	
	@Override
	public List<? extends PixelArea> getStore() {
		return _store;
	}

	@Override
	public PixelArea makePixelArea(int x, int y, int startColor) {
		GrayArea result = new GrayArea(x, y, startColor);
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
