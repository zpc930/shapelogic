package org.shapelogic.color;

/** ColorNorm is the scaled 1 norm in the color space.<br />
 * 
 * The 1 norm is called the Taxicab norm or Manhattan norm.<br />
 * It is then scaled down by the number of dimensions, so that the distance of 
 * 2 colors will be the same in gray and RGB space.<br />
 * <br />
 * This could be changed to use scaling for the different component.<br />
 * <br />
 * This should work for both color and gray.<br />
 * 
 * @author Sami Badawi
 *
 */
public class ColorDistance1 implements IColorDistance {
	
	@Override
	public double distance(ColorChannels colorV1, ColorChannels colorV2) {
		int[] color1 = colorV1.getColorChannels();
		int[] color2 = colorV2.getColorChannels();
		int minLength = Math.min(color1.length, color2.length);
		if (minLength == 0)
			return 0.;
		double distanceResult = 0.;
		for (int i = 0; i < minLength; i++) {
			distanceResult += Math.abs(color1[i] - color2[i]); 
		}
		return distanceResult / minLength;
	}

	@Override
	public double distance(int[] color1, int[] color2) {
		int minLength = Math.min(color1.length, color2.length);
		if (minLength == 0)
			return 0.;
		double distanceResult = 0.;
		for (int i = 0; i < minLength; i++) {
			distanceResult += Math.abs(color1[i] - color2[i]); 
		}
		return distanceResult / minLength;
	}

}
