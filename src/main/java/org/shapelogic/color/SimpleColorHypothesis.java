package org.shapelogic.color;

import java.util.ArrayList;
import java.util.Collection;

/** SimpleColorHypothesis is a color hypothesis.<br/>
 * 
 * @author Sami Badawi
 *
 */
public class SimpleColorHypothesis implements ColorHypothesis {
	
	protected Collection<IColorAndVariance> _colors = new ArrayList<IColorAndVariance>();
	protected double _maxDistance = 20;
	protected IColorAndVariance _background;
	
	/** If the color is identical merge else add. 
	 * 
	 */
	@Override
	public boolean addColor(IColorAndVariance color) {
//		for (IColorAndVariance colorI: _colors) {
//			if (mergable(color, colorI)) {
//				color.merge(colorI);
//				return true;
//			}
//		}
		_colors.add(color);
		return false;
	}

	@Override
	public Collection<IColorAndVariance> getColors() {
		return _colors;
	}

	@Override
	public IColorAndVariance getBackground() {
		return _background;
	}

	@Override
	public void setBackground(IColorAndVariance color) {
		_background = color;
	}

	@Override
	public double getMaxDistance() {
		return _maxDistance;
	}

	@Override
	public void setMaxDistance(double tolerance) {
		_maxDistance = tolerance;
	}

	@Override
	public boolean mergable(IColorAndVariance color1, IColorAndVariance color2) {
//		boolean result = Arrays.equals(color1.getColorChannels(), color2.getColorChannels());
        boolean result = ColorDistance1.INSTANCE.distance(color1, color2) < _maxDistance;
        return result;
	}

}
