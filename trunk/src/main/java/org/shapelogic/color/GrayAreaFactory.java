package org.shapelogic.color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.shapelogic.imageprocessing.AreaComparator;
import org.shapelogic.imageutil.PixelArea;

/** GrayAreaFactory is a factory and store for GrayEdgeArea.
 * <br />
 * @author Sami Badawi
 *
 */
public class GrayAreaFactory implements ValueAreaFactory {
	
	ArrayList<IColorAndVariance> _store = new ArrayList<IColorAndVariance>();
	int _backgroundColor;
	int _maxArea = 0;
	
	@Override
	public List<IColorAndVariance> getStore() {
		return _store;
	}

	@Override
	public IColorAndVariance makePixelArea(int x, int y, int startColor) {
		IColorAndVariance result = new GrayAndVariance();
        result.setPixelArea(new PixelArea(x, y));
        //XXX not sure if this could result in double counting
        result.putPixel(x, y, startColor); 
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
