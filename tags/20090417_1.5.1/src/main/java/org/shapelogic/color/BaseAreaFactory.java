package org.shapelogic.color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.shapelogic.imageprocessing.AreaComparator;

/** BaseAreaFactory the base for GrayAreaFactory and ColorAreaFactory, it is a 
 * factory and store for IColorAndVariance.
 * <br />
 * @author Sami Badawi
 *
 */
public abstract class BaseAreaFactory implements ValueAreaFactory {
	
	ArrayList<IColorAndVariance> _store = new ArrayList<IColorAndVariance>();
	int _backgroundColor;
	int _maxArea = 0;
	
	@Override
	public List<IColorAndVariance> getStore() {
		return _store;
	}

	@Override
	public abstract IColorAndVariance makePixelArea(int x, int y, int startColor);

	/** Returns the biggest color. */
	@Override
	public int getBackgroundColor() {
		return _backgroundColor;
	}

	@Override
	public void sort() {
		Collections.sort(_store,new AreaComparator());
	}

    @Override
    public int areasGreaterThan(int minSize) {
        sort();
        for (int i = 0; i < _store.size(); i++) {
            if (minSize <= _store.get(i).getArea())
                return _store.size() - i;
        }
        return 0;
    }

}
