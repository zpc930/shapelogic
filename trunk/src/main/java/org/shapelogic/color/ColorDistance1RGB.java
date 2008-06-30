package org.shapelogic.color;

/** Color distance for RGB.<br />
 * 
 * @author Sami Badawi
 *
 */
public class ColorDistance1RGB extends ColorDistance1 {
	protected int[] _referenceColorChannels = new int[3];
	
	@Override
	public double distance(int color1, int color2) {
		int [] rgb1 = ColorUtil.splitColor(color1);
		int [] rgb2 = ColorUtil.splitColor(color2);
		int dist = 0;
		for (int i = 0; i < rgb1.length; i++ ) {
			dist += Math.abs(rgb1[i] - rgb2[i]);
		}
		dist = dist / 3; // to make it fit with grayscale
		return dist;
	}
	
	@Override
	public void setReferenceColor(int color) {
		_referenceColor = color;
		ColorUtil.splitColor(color, _referenceColorChannels);
	}

	@Override
	public double distanceToReferenceColor(int color) {
		return distance(_referenceColorChannels,ColorUtil.splitColor(color));
	}
}
