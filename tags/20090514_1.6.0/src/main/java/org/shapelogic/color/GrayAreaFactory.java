package org.shapelogic.color;

import org.shapelogic.imageutil.PixelArea;

/** GrayAreaFactory is a factory and store for GrayEdgeArea.
 * <br />
 * @author Sami Badawi
 *
 */
public class GrayAreaFactory  extends BaseAreaFactory 
        implements ValueAreaFactory {
	
	@Override
	public IColorAndVariance makePixelArea(int x, int y, int startColor) {
		IColorAndVariance result = new GrayAndVariance();
        result.setPixelArea(new PixelArea(x, y));
        //XXX not sure if this could result in double counting
        result.putPixel(x, y, startColor); 
		_store.add(result);
		return result;
	}
}
